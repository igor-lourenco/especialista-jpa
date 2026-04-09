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
import javax.persistence.Persistence;

public class _2_Resolvendo_problema_de_concorrencia_com_Lock_Otmista {
    protected static final Logger logger = LoggerFactory.getLogger(_2_Resolvendo_problema_de_concorrencia_com_Lock_Otmista.class.getSimpleName());

    protected static EntityManagerFactory entityManagerFactory;

/*  — > Lock otimista: É um mecanismo de controle de concorrência usado para evitar perda de dados quando múltiplas
    transações atualizam o mesmo registro, sem bloquear o banco durante a leitura

    - Lock otimista evita sobrescrita silenciosa de dados
    - Usa @Version
    - Não bloqueia o banco
    - Detecta conflito no commit
    - Lança exceção em caso de conflito
    - É o padrão recomendado no JPA
 */

    @Test
    public void usando_LockOtmista() {

        Runnable run0 = () -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            log("RUN 0: vai carregar o Produto 1...");
            Produto produto = entityManager.find(Produto.class, 1);


            log("RUN 0: vai esperar por 5 segundos...");
            esperar(5);

            log("RUN 0: vai alterar o Produto 1...");
            produto.setDescricao("Descrição detalhada.");

            log("RUN 0: vai confirmar a transação");
            entityManager.getTransaction().commit();
            entityManager.close();

            log("RUN 0: concluído...");
        };

        Runnable run1 = () -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            log("RUN 1: vai carregar o Produto 1...");
            Produto produto = entityManager.find(Produto.class, 1);


            log("RUN 1: vai esperar por 2 segundos...");
            esperar(2);

            log("RUN 1: vai alterar o Produto 1...");
            produto.setDescricao("Descrição massa.");

            log("RUN 1: vai confirmar a transação");
            entityManager.getTransaction().commit();
            entityManager.close();

            log("RUN 1: concluído...");
        };

        Thread t0 = new Thread(run0);
        Thread t1 = new Thread(run1);

        t0.start();
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

        Assert.assertEquals("Descrição massa.", produto.getDescricao());

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
