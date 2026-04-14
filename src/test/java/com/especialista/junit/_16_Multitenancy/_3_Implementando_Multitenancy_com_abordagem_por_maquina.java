package com.especialista.junit._16_Multitenancy;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.jpa.multitenancy.maquina.EcmCurrentTenantIdentifierResolver;
import com.especialista.junit.utils.EntityManagerFactoryTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

public class _3_Implementando_Multitenancy_com_abordagem_por_maquina extends EntityManagerFactoryTest {
    protected static final Logger logger = LoggerFactory.getLogger(_3_Implementando_Multitenancy_com_abordagem_por_maquina.class);

    @Test
    public void usandoAbordagemPorMaquina(){

        logger.debug("Usando o schema: especialistajpadb");
        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("especialistajpadb");

        logger.debug("Criando EntityManager...");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();


        logger.debug("Buscando produto...");
        Produto produto1 = entityManager1.find(Produto.class, 1);

        Assert.assertEquals("Kindle", produto1.getNome());
        entityManager1.close();

        logger.error("============================================================================================");
        logger.debug("Usando o schema: especialistajpadb_multitenancy");
        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("especialistajpadb_multitenancy");

        logger.debug("Criando EntityManager...");
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        logger.debug("Buscando produto...");
        Produto produto2 = entityManager2.find(Produto.class, 1);
        Assert.assertEquals("Kindle Multitency", produto2.getNome());
        entityManager2.close();

    }
}
