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

public class _6_Entendendo_o_que_acontece_se_misturarmos_mais_de_um_tipo_de_Lock {
    protected static final Logger logger = LoggerFactory.getLogger(_6_Entendendo_o_que_acontece_se_misturarmos_mais_de_um_tipo_de_Lock.class.getSimpleName());

    protected static EntityManagerFactory entityManagerFactory;

/*  — > Se a transação for primeiro usando LockModeType.PESSIMISTIC_WRITE:

        - A transação usando PESSIMISTIC_READ fica:
           - Bloqueado(aguardando) enquanto o PESSIMISTIC_WRITE estiver ativo.
           - NÃO consegue nem ler com lock enquanto LockModeType.PESSIMISTIC_WRITE estiver ativa.
           - Só prossegue quando:
             - LockModeType.PESSIMISTIC_WRITE faz commit ou rollback
             - Ou ocorre timeout
             - Ou ocorre deadlock (em cenários mais complexos)


    — > Se a transação for primeiro usando LockModeType.PESSIMISTIC_READ:

       - A transação usando PESSIMISTIC_WRITE fica:
          - Bloqueado(aguardando) enquanto o PESSIMISTIC_READ estiver ativo.
          - O banco não concede lock exclusivo (WRITE) enquanto existir qualquer lock de leitura ativo.
          - Só prossegue quando:
            - PESSIMISTIC_WRITE espera TODOS os PESSIMISTIC_READ saírem
*/

    @Test
    public void usando_LockPessimista_LockModeType_Pessimistic_WRITE_e_Pessimistic_READ() {

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

            log("RUN 1: vai carregar o Produto...(vai travar aqui até a RUN 0 comitar e liberar o lock)");
            Produto produto = entityManager.find(
                Produto.class,
                1,
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
