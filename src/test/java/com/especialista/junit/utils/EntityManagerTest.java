package com.especialista.junit.utils;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.ZoneId;
import java.util.TimeZone;

public class EntityManagerTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    protected static EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;


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


    @Before
    public void setUp() { // executa antes de cada teste
        System.out.println(">>> Iniciando o EntityManager...");
        entityManager = entityManagerFactory.createEntityManager();
    }


    public void tearDown() { // executa depois de cada teste
        System.out.println(">>> Finalizando o EntityManager...");
        entityManager.close();
    }

    protected void fusoHorario() {
        logger.info("======================================================================================");
        ZoneId zoneId = ZoneId.systemDefault();
        logger.info("Fuso horário(time-zone) padrão do sistema: " + zoneId);

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        logger.info("Alterando o fuso padrão do sistema para UTC...");

        zoneId = ZoneId.systemDefault();
        logger.info("Fuso horário(time-zone) padrão do sistema atualizado: " + zoneId);

        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        zoneId = ZoneId.systemDefault();
        logger.info("Alterando o fuso padrão do sistema de volta para America/Sao_Paulo: " + zoneId);
        logger.info("======================================================================================");
    }
}