package com.especialista.junit._14_Second_Level_Cache_cache_compartilhado;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class _1_Entendendo_cache_de_segundo_nivel {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());


    protected static EntityManagerFactory entityManagerFactory;

/*  Fluxo do cache:
      - Primeiro tenta encontrar a entidade no cache de 1° nível (Cache por contexto, no mesmo EntityManager)
      - Se não encontrar, tenta encontrar a entidade no cache de 2° nível (Cache da aplicação, entre EntityManagers)
      - Se não encontrar, faz a consulta no banco de dados
*/

    @Test
    public void buscarDoCache(){

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println();

        logger.info(">>>>> Buscando a partir da instância 1");
        entityManager1.find(Pedido.class, 1);

        logger.info(">>>>> Buscando a partir da instância 2"); // a entidade já foi carregada e cacheada pelo entityManager1
        entityManager2.find(Pedido.class, 1);

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
