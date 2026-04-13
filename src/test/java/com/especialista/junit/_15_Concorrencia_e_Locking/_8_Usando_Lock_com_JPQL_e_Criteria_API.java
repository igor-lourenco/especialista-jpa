package com.especialista.junit._15_Concorrencia_e_Locking;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido_;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class _8_Usando_Lock_com_JPQL_e_Criteria_API {
    protected static final Logger logger = LoggerFactory.getLogger(_8_Usando_Lock_com_JPQL_e_Criteria_API.class);

    protected static EntityManagerFactory entityManagerFactory;

    @Test
    public void usando_LockPessimista_LockModeType_Pessimistic_WRITE_e_Pessimistic_READ() {

        Runnable run0 = () -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            log("RUN 0: Iniciando...");

            String novaDescricao = "Descrição detalhada. CTM: " + System.currentTimeMillis();

            log("RUN 0: vai carregar o Produto...");
            Produto produto = entityManager
                .createQuery("SELECT p FROM Produto p WHERE p.id = 1", Produto.class)
                .setLockMode(LockModeType.PESSIMISTIC_READ)
                .getSingleResult();

            log("RUN 0: vai alterar o Produto...");
            produto.setDescricao(novaDescricao);

            log("RUN 0: vai esperar por 5 segundos...");
            esperar(5);

            try {
                log("RUN 0: vai confirmar a transação");
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                log("ERRO: " + e.getMessage());
                throw e;
            } finally {
                entityManager.close();
                log("RUN 0: concluído...");
            }
        };

        Runnable run1 = () -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Produto> query = criteriaBuilder.createQuery(Produto.class);
            Root<Produto> root = query.from(Produto.class);
            query.where(criteriaBuilder.equal(root.get(Pedido_.id),1));

            log("RUN 1: Iniciando...");

            String novaDescricao = "Descrição massa. CTM: " + System.currentTimeMillis();

            log("RUN 1: vai carregar o Produto...(vai travar aqui até a RUN 0 comitar e liberar o lock)");
            Produto produto =entityManager
                .createQuery(query)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .getSingleResult();

            log("RUN 1: vai alterar o Produto...");
            produto.setDescricao(novaDescricao);

            log("RUN 1: vai esperar por 2 segundos...");
            esperar(2);

            try {
                log("RUN 1: vai confirmar a transação");
                entityManager.getTransaction().commit();

            } catch (Exception e) {
                log("ERRO: " + e.getMessage());
                throw e;
            } finally {
                entityManager.close();
                log("RUN 1: concluído...");
            }

        };

        Thread t0 = new Thread(run0);
        Thread t1 = new Thread(run1);

        t0.start();

        log("esperando 1 segundo...");
        esperar(1);

        t1.start();

        try {
            log("Juntando com a Thread-0");
            t0.join();   // faz a thread atual[main] esperar outra thread[Thread-0] terminar
            log("Continuando a execução");


            log("Juntando com a Thread-1");
            t1.join();   // faz a thread atual[main] esperar outra thread[Thread-1] terminar
            log("Continuando a execução");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Produto produto = entityManager.find(Produto.class, 1);

        Assert.assertTrue(produto.getDescricao().startsWith("Descrição massa"));

        entityManager.close();
        log("Encerrando método...");
    }


    private static void log(Object obj, Object... args) {
        logger.info("LOG [" + Thread.currentThread().getName() + "]: " + obj);
    }

    private static void esperar(int segundos) {
        try {
            Thread.sleep(segundos * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeClass
    public static void setUpBeforeClass() { // executa primeiro
        logger.debug(">>> Iniciando o EntityManagerFactory...");
        entityManagerFactory = Persistence.createEntityManagerFactory("EspecialistaJPADB-PU");

    }

    @AfterClass
    public static void tearDownAfterClass() { // executa por ultimo
        logger.debug(">>> Finalizando o EntityManagerFactory...");
        entityManagerFactory.close();
    }
}
