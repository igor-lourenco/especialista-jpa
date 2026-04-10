package com.especialista.junit._15_Concorrencia_e_Locking;

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

public class _4_Lock_Pessimista_pessimistic_READ {
    protected static final Logger logger = LoggerFactory.getLogger(_4_Lock_Pessimista_pessimistic_READ.class.getSimpleName());

    protected static EntityManagerFactory entityManagerFactory;

/*  — > LockModeType.PESSIMISTIC_READ: usado para garantir que um registro não seja modificado enquanto
      sua transação está usando os dados para leitura.
        - Vai ler esse dado garantindo que ninguém vai alterá‑lo enquanto estiver trabalhando nesse dado

        Ou seja:
          - Você pode ler o registro com segurança
          - Outras transações *NÃO* podem modificá‑lo
          - Outras transações podem ler (dependendo do banco)
          - O lock dura até o commit ou rollback

        Observação:
          - Quando mais de uma transação adquire LockModeType.PESSIMISTIC_READ sobre o mesmo registro e depois tenta ALTERAR o dado,
          o que acontece é um conflito na hora de promover o lock.
            - Só uma transação conseguirá alterar o dado.
            - As outras ficarão bloqueadas, esperarão, ou falharão por timeout/deadlock, dependendo do banco e da configuração
*/

    @Test
    public void usando_LockPessimista_LockModeType_Pessimistic_READ() {

        Runnable run0 = () -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            log("RUN 0: Iniciando...");

            String novaDescricao = "Descrição detalhada. CTM: " + System.currentTimeMillis();

            log("RUN 0: vai carregar o Produto...");
            Produto produto = entityManager.find(
                Produto.class,
                1,
                LockModeType.PESSIMISTIC_READ
            );

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

            log("RUN 1: Iniciando...");

            String novaDescricao = "Descrição massa. CTM: " + System.currentTimeMillis();

            log("RUN 1: vai carregar o Produto...");
            Produto produto = entityManager.find(
                Produto.class,
                1,
                LockModeType.PESSIMISTIC_READ
            );

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
        System.out.println(">>> Iniciando o EntityManagerFactory...");
        entityManagerFactory = Persistence.createEntityManagerFactory("EspecialistaJPADB-PU");

    }

    @AfterClass
    public static void tearDownAfterClass() { // executa por ultimo
        System.out.println(">>> Finalizando o EntityManagerFactory...");
        entityManagerFactory.close();
    }
}
