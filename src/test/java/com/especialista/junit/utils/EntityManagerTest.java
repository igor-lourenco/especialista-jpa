package com.especialista.junit.utils;

import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerTest {

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
}