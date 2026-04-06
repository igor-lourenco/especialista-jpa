package com.especialista.junit._14_Second_Level_Cache_cache_compartilhado;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class _7_Configurando_o_ehcache_como_provedor {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    protected static EntityManagerFactory entityManagerFactory;

    @Test
    public void ehcache(){

//      Documentação do ehcache
//        - https://www.ehcache.org/documentation/

        Cache cache = entityManagerFactory.getCache();

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        System.out.println();

        logger.info(">>>>> Buscando e incluindo no cache");
        entityManager1
            .createQuery("SELECT p FROM Pedido p",Pedido.class)
            .getResultList();

        logger.info("Esperando...");
        esperar(1);

        logger.info(">>>>> Verificando se o Pedido está no cache: "
            + cache.contains(Pedido.class, 1));

        logger.info("Buscando Pedido 1...");
        entityManager2.find(Pedido.class, 1);
        logger.info("Está buscando Pagamento porque está usando a configuração que somente a entidade que estiver com a anotação @Cacheable podem ser cacheadas");

        logger.info("Esperando...");
        esperar(3);

        logger.info(">>>>> Verificando (de novo) se o Pedido está no cache : "
            + cache.contains(Pedido.class, 1));

        logger.info("Obs: Em ehcache.xml foi configurado para depois de 2 seg se o Pedido não for usado ele é removido");

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


    private static void esperar(int segundos){
        try {
            Thread.sleep(segundos * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
