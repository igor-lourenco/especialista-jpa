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

public class _5_Lock_Pessimista_pessimistic_WRITE {
    protected static final Logger logger = LoggerFactory.getLogger(_5_Lock_Pessimista_pessimistic_WRITE.class.getSimpleName());

    protected static EntityManagerFactory entityManagerFactory;

/*  — > LockModeType.PESSIMISTIC_WRITE: usado quando precisa de exclusividade total sobre um registro, garantindo que
      ninguém mais leia ou altere aquele dado enquanto sua transação estiver ativa
        - Vai trabalhar nesse dado e ninguém mais pode nem ler, nem modificar até terminar
        - É um lock exclusivo no banco de dados

        Ou seja:
          - Executa um SELECT com lock exclusivo
          - O banco:
            - bloqueia leitura com lock
            - bloqueia UPDATE
            - bloqueia DELETE
          - O lock só é liberado no:
             - commit
             - rollback
          - Só uma transação controla o registro

        Observação:
          - Quando mais de uma transação usa LockModeType.PESSIMISTIC_WRITE sobre o mesmo registro e todas tentam
          alterar, o comportamento é:
            - Somente UMA transação por vez pode obter o lock e alterar o dado.
            - As outras ficam bloqueadas na tentativa de obter o lock (já na leitura)
            - Se o lock não for liberado a tempo, falham por timeout ou deadlock
*/

    @Test
    public void usando_LockPessimista_LockModeType_Pessimistic_WRITE() {

        Runnable run0 = () -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            log("RUN 0: Iniciando...");

            String novaDescricao = "Descrição detalhada. CTM: " + System.currentTimeMillis();

            log("RUN 0: vai carregar o Produto...");
            Produto produto = entityManager.find(
                Produto.class,
                1,
                LockModeType.PESSIMISTIC_WRITE
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

            log("RUN 1: vai carregar o Produto...(vai travar aqui até a RUN 0 comitar e liberar o lock)");
            Produto produto = entityManager.find(
                Produto.class,
                1
                ,
                LockModeType.PESSIMISTIC_WRITE
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
        System.out.println(">>> Iniciando o EntityManagerFactory...");
        entityManagerFactory = Persistence.createEntityManagerFactory("EspecialistaJPADB-PU");

    }

    @AfterClass
    public static void tearDownAfterClass() { // executa por ultimo
        System.out.println(">>> Finalizando o EntityManagerFactory...");
        entityManagerFactory.close();
    }
}
