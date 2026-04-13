package com.especialista.jpa.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Essa classe serve para o funcionamento do multitenancy por SCHEMA no Hibernate, ou seja, é essa classe que especifica
// qual é o tenant (schema) que deve ser usado AGORA, ela faz isso em tempo de execução, usando o ThreadLocal
public class EcmCurrentTenantIdentifierResolver implements
    CurrentTenantIdentifierResolver //  interface do Hibernate usada exclusivamente para multitenancy
{
    protected static final Logger logger = LoggerFactory.getLogger(EcmCurrentTenantIdentifierResolver.class);

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>(); // é um contêiner de dados que isola informações para cada Thread


//  Define o tenant/schema atual
    public static void setTenantIdentifier(String tenantIdentifier){
        logger.warn("Armazena no ThreadLocal Tenant (schema): " + tenantIdentifier);
        threadLocal.set(tenantIdentifier);
    }

    @Override // retorna o Tenant (schema) que está sendo usado agora
    public String resolveCurrentTenantIdentifier() {
        logger.warn("Tenant (schema) que está sendo utilizado agora: " + threadLocal.get());
        return threadLocal.get();
    }

    @Override // define se o Hibernate pode reutilizar uma sessão já aberta quando o tenant muda
    public boolean validateExistingCurrentSessions() {
//        true -> Hibernate tenta reutilizar a sessão
//        false -> Hibernate força criar nova sessão

        logger.warn("Não é pra reutilizar a sessão á aberta, forçando Hibernate a criar uma nova sessão...");
        return false;
    }
}
