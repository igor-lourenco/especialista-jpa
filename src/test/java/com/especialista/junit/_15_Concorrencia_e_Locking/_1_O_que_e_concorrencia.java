package com.especialista.junit._15_Concorrencia_e_Locking;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class _1_O_que_e_concorrencia {
    protected static final Logger logger = LoggerFactory.getLogger(_1_O_que_e_concorrencia.class.getSimpleName());

    @Test
    public void entenderThreads() {

        Runnable run0 = () -> {
            log("RUNNABLE 00: vai esperar 5 segundos...");
            esperar(5);
            log("RUNNABLE 00: concluído...");

        };

        Runnable run1 = () -> {
            log("RUNNABLE 01: vai esperar 2 segundos...");
            esperar(2);
            log("RUNNABLE 01: concluído...");

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

}
