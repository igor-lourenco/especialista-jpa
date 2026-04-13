package com.especialista.junit.utils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryTest {
    protected static final Logger logger = LoggerFactory.getLogger(EntityManagerFactoryTest.class);

    protected static EntityManagerFactory entityManagerFactory;


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