package com.especialista.jpa.multitenancy.maquina;


import org.hibernate.HibernateException;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.service.spi.Startable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

// Essa classe serve para controlar como a conexão JDBC é criada e ajustada para esse tenant (schema), ou seja, entrega ao Hibernate
// uma Connection já apontando para o schema correto do tenant, funciona como uma ponte entre o Hibernate e
// o pool de conexões (HikariCP), ajustando o schema dinamicamente.
// Obs: Essa classe não cria conexões diretamente, ela delega isso ao Hikari
public class EcmMaquineMultiTenantConnectionProvider implements
    MultiTenantConnectionProvider   // usado para: Abrir uma conexão, Trocar o tenant, Liberar a conexão
    , ServiceRegistryAwareService   // usado para: recebe o ServiceRegistry do Hibernate, ler as propriedades do persistence.xml/application.properties
    , Startable                     // usado para: executar algo no startup do Hibernate, nesse caso inicializa o HikariCP
{
    protected static final Logger logger = LoggerFactory.getLogger(EcmMaquineMultiTenantConnectionProvider.class);

    private Map<String, String> properties = null; //configurações do Hibernate/JPA

    private Map<String, ConnectionProvider> connectionProviders = null; // provider real de conexões (HikariCP)


    @Override // usado pelo Hibernate sempre precisar de uma conexão para um tenant específico.
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        logger.warn("[getConnection]: Schema recebido como parâmetro: " + tenantIdentifier);

        ConnectionProvider cp = connectionProviders.get(tenantIdentifier);
        if (cp == null) {
            logger.error("[getConnection] Tenant não configurado: " + tenantIdentifier);
            throw new HibernateException("Tenant não configurado: " + tenantIdentifier);
        }

        DatabaseMetaData metaData = cp.getConnection().getMetaData();

        logger.debug("[getConnection] Retornando conexão DB: {} | URL: {} | User: {}", // apenas para debug, depois remover
            metaData.getDatabaseProductName(),
            metaData.getURL(),
            metaData.getUserName()
        );

//      retorna uma conexão(Connection) de um pool específico do tenant, e cada pool está conectado a um banco de dados específico do tenant
        return cp.getConnection();

    }

    @Override // usaod para pegar uma conexão do pool sem se preocupar com tenant
    public Connection getAnyConnection() throws SQLException {
        logger.warn("[getAnyConnection] : pegando uma conexão do poll de conexões...");
        return getAnyConnectionProvider().getConnection();
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
//      Esse método é usado pelo Hibernate toda vez que:
//        - Uma Session termina
//        - Uma transação acaba
//        - O Hibernate decide liberar a conexão associada a um tenant específico

        logger.warn("[releaseConnection] Transação do tenant: " + tenantIdentifier + " finalizada, devolvendo a conexão...");
        releaseAnyConnection(connection);
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
//        Esse método é usado pelo Hibernate para:
//        - Quando ele só quer devolver uma conexão genérica
//        - Sem se importar com tenant

        logger.warn("[releaseAnyConnection] Finalizado com essa Connection, devolvendo para o pool...");
        getAnyConnectionProvider().closeConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
//        Esse método é usado pelo Hibernate para liberar a conexão o mais cedo possível, mesmo que:
//        - A Session ainda esteja aberta
//        - O método ainda não terminou
//        - A transação pode continuar em seguida
        logger.warn("[supportsAggressiveRelease] Delegando o comportamento de liberação para o provider real (HikariCP)");
        return getAnyConnectionProvider().supportsAggressiveRelease();
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
//        Esse método é usado pelo Hibernate para tratar esse objeto vindo parâmetro como outro tipo mais específico,
//        parecido com o instanceof mas feito de forma padronizada pelo Hibernate / JPA.

        logger.warn("[isUnwrappableAs] Delegando se a classe pode ser desembrulhada para o provider real(HikariCP)");
        return getAnyConnectionProvider().isUnwrappableAs(unwrapType);
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
//       Esse método é usado pelo Hibernate junto com método isUnwrappableAs, um depende d outro logicamente

        logger.warn("[unwrap] Delegando o retorno do objeto para o provider real(HikariCP)");
        return getAnyConnectionProvider().unwrap(unwrapType);
    }

    @Override // Esse método é chamado automaticamente pelo Hibernate, antes do método start().
    public void injectServices(ServiceRegistryImplementor serviceRegistry) {

        logger.warn("[injectServices] Montando ServiceRegistry e injetando ele no nosso properties da classe.");

        this.properties = serviceRegistry // O ServiceRegistry conhece TODA a configuração do Hibernate.
            .getService(ConfigurationService.class) // ConfigurationService que carrega: persistence.xml, hibernate.cfg.xml, application.properties, application.yml
            .getSettings(); // retorna Map<String, Object> com todas as propriedades finais já resolvidas e processadas
    }

    @Override // Esse método é chamado automaticamente pelo Hibernate durante a subida da aplicação
    public void start() {
        logger.warn("[start] Iniciando... ");
        connectionProviders = new HashMap<>();

        logger.warn("[start] Inicializar todos os tenants conhecidos no startup...");

        logger.warn("[start] Criando um ConnectionProvider (HikariCP) para tenant: especialistajpadb ");
        configurarTenant("especialistajpadb",
            "jdbc:mysql://192.168.0.54:3307/especialistajpadb?"
                + "createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=UTC",
            "appuser",
            "L@ur&nco@Mysql135"); // poderia pegar os dados de conexão de um arquivo properties, ou de um outro banco que poderia estar em uma 3º maquina


        logger.warn("[start] Criando um ConnectionProvider (HikariCP) para tenant: especialistajpadb_multitenancy ");
        configurarTenant("especialistajpadb_multitenancy",
            "jdbc:mysql://192.168.0.54:3307/especialistajpadb_multitenancy?"
                + "createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=UTC",
            "appuser",
            "L@ur&nco@Mysql135");  // poderia pegar os dados de conexão de um arquivo properties, ou de um outro banco que poderia estar em uma 3º maquina


//      Isso quebra a dependência com o ConfigurationService, agora cada tenant tem sua própria cópia de config
//      Depois disso, essa classe não pode mais usar this.properties
        this.properties = null;
    }


    private void configurarTenant(String tenant, String url, String usuario, String senha) {

        Map<String, String> props = new HashMap<>(this.properties); // Clonando todas as propriedades globais que está lá no persistence.xml

        logger.warn("[configurarTenant] Configurando tenant: '" + tenant + "', sobrescrevendo url, user, password para apontar para o banco diferente");

        props.put("javax.persistence.jdbc.url", url);
        props.put("hibernate.connection.url", url);

        props.put("javax.persistence.jdbc.user", usuario);
        props.put("hibernate.connection.username", usuario);

        props.put("javax.persistence.jdbc.password", senha);
        props.put("hibernate.connection.password", senha);

        HikariCPConnectionProvider cp = new HikariCPConnectionProvider(); // Criando um HikariCP por tenant
        cp.configure(props); //Configurando para cada tenant ter seu próprio pool, com conexões independentes, com controle de tamanho, timeout, etc.

        this.connectionProviders.put(tenant, cp); // setando no map dessa classe, agora o Hibernate pode pegar conexões diretamente por tenant específico

        logger.warn("[configurarTenant] Agora o Hibernate pode pegar conexões diretamente por tenant específico");
    }

    private ConnectionProvider getAnyConnectionProvider() { // retorna qualquer provider disponível configurado
        return connectionProviders
            .values().iterator().next();
    }
}