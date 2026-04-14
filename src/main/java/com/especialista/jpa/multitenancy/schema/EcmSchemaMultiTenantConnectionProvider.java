package com.especialista.jpa.multitenancy.schema;


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
import java.sql.SQLException;
import java.util.Map;

// Essa classe serve para controlar como a conexão JDBC é criada e ajustada para esse tenant (schema), ou seja, entrega ao Hibernate
// uma Connection já apontando para o schema correto do tenant, funciona como uma ponte entre o Hibernate e
// o pool de conexões (HikariCP), ajustando o schema dinamicamente.
// Obs: Essa classe não cria conexões diretamente, ela delega isso ao Hikari
public class EcmSchemaMultiTenantConnectionProvider implements
    MultiTenantConnectionProvider   // usado para: Abrir uma conexão, Trocar o tenant, Liberar a conexão
    , ServiceRegistryAwareService   // usado para: recebe o ServiceRegistry do Hibernate, ler as propriedades do persistence.xml/application.properties
    , Startable                     // usado para: executar algo no startup do Hibernate, nesse caso inicializa o HikariCP
{
    protected static final Logger logger = LoggerFactory.getLogger(EcmSchemaMultiTenantConnectionProvider.class);

    private Map<String, String> properties = null; //configurações do Hibernate/JPA

    private ConnectionProvider connectionProvider = null; // provider real de conexões (HikariCP)


    @Override // usado pelo Hibernate sempre precisar de uma conexão para um tenant específico.
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        logger.warn("[getConnection]: Schema recebido como parâmetro: " + tenantIdentifier);
        Connection connection = getAnyConnection();

        try {
        logger.warn("[getConnection]: Trocando o schema da conexão para o tenant recebido.: " + tenantIdentifier);
            connection.createStatement().execute("use " + tenantIdentifier);
        } catch (SQLException e) {
        logger.error("[getConnection]: Não foi possível alterar para o schema: " + tenantIdentifier);
            throw new HibernateException("Não foi possível alterar " +
                "para o schema " + tenantIdentifier + ".", e);
        }

        return connection;
    }

    @Override // usaod para pegar uma conexão do pool sem se preocupar com tenant
    public Connection getAnyConnection() throws SQLException {
        logger.warn("[getAnyConnection] : pegando uma conexão do poll de conexões...");
        return connectionProvider.getConnection();
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
        connectionProvider.closeConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
//        Esse método é usado pelo Hibernate para liberar a conexão o mais cedo possível, mesmo que:
//        - A Session ainda esteja aberta
//        - O método ainda não terminou
//        - A transação pode continuar em seguida
        logger.warn("[supportsAggressiveRelease] Delegando o comportamento de liberação para o provider real (HikariCP)");
        return connectionProvider.supportsAggressiveRelease();
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
//        Esse método é usado pelo Hibernate para tratar esse objeto vindo parâmetro como outro tipo mais específico,
//        parecido com o instanceof mas feito de forma padronizada pelo Hibernate / JPA.

        logger.warn("[isUnwrappableAs] Delegando se a classe pode ser desembrulhada para o provider real(HikariCP)");
        return connectionProvider.isUnwrappableAs(unwrapType);
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
//       Esse método é usado pelo Hibernate junto com método isUnwrappableAs, um depende d outro logicamente

        logger.warn("[unwrap] Delegando o retorno do objeto para o provider real(HikariCP)");
        return connectionProvider.unwrap(unwrapType);
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

        // Cria o provider oficial do Hibernate que encapsula o HikariCP. para ser usado para criar e gerenciar conexões JDBC
        HikariCPConnectionProvider cp = new HikariCPConnectionProvider();

        // Configura o provider com as configurações o properties da classe que foi carregada e injetada pelo método injectServices
        cp.configure(this.properties);

        connectionProvider = cp; // especifica que esse vai ser o provider oficial que vai ser usado daqui pra frente.
    }
}