package com.especialista.junit._14_Second_Level_Cache_cache_compartilhado;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class _5_Modos_de_cache_e_anotacao_Cacheable {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());


    protected static EntityManagerFactory entityManagerFactory;

/*  Fluxo do cache:
      - Primeiro tenta encontrar a entidade no cache de 1° nível (Cache por contexto, no mesmo EntityManager)
      - Se não encontrar, tenta encontrar a entidade no cache de 2° nível (Cache da aplicação, entre EntityManagers)
      - Se não encontrar, faz a consulta no banco de dados
 */

    @Test
    public void analisarOpcoesDeCache(){
        Cache cache = entityManagerFactory.getCache();

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        System.out.println();

        logger.info(">>>>> Buscando a partir da instância 1");
        entityManager1.createQuery("SELECT p FROM Pedido p",Pedido.class).getResultList();

        Assert.assertTrue(cache.contains(Pedido.class, 1));

        logger.info(">>>>> Verificando se o Pedido 1 da instância 1 está no cache: "
            + cache.contains(Pedido.class, 1));

        entityManager1.close();
        entityManager2.close();
    }


    @BeforeClass
    public static void setUpBeforeClass() { // executa primeiro
        System.out.println(">>> Iniciando o EntityManagerFactory...");
        entityManagerFactory = Persistence.createEntityManagerFactory("EspecialistaJPADB-PU");

    }

    @AfterClass
    public static void tearDownAfterClass() { // executa por ultimo
        System.out.println(">>> Finalizando o EntityManagerFactory...");
        entityManagerFactory.close();
    }

}
