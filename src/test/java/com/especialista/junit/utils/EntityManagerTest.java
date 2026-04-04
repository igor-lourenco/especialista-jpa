package com.especialista.junit.utils;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    @After
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

    protected CriteriaQuery<Pedido> getCriteriaQuery() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido
        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p
        criteriaQuery.select(root); // SELECT p
        return criteriaQuery;
    }
}