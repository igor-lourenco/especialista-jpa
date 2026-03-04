package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Categoria;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class _48_Abordagem_hibrida_para_dynamic_e_named_queries extends EntityManagerTest {


    @BeforeClass
    public static void configurarNamedQuery(){
        System.out.println(">>> Iniciando o EntityManagerFactory [configurarNamedQuery]...");
        entityManagerFactory = Persistence.createEntityManagerFactory("EspecialistaJPADB-PU");

        System.out.println(">>> Iniciando o EntityManager [configurarNamedQuery]...");
        EntityManager em = entityManagerFactory.createEntityManager();


        String jpql = "SELECT c FROM Categoria c";
        TypedQuery<Categoria> typedQuery = em.createQuery(jpql, Categoria.class);


        System.out.println(">>> Adicionando uma NamedQuery [configurarNamedQuery]...");
        entityManagerFactory.addNamedQuery(
            "Categoria.listar" // Nome da Named Query para ser referenciada
            , typedQuery
        );
    }


    @Test
    public void usandoAbordagemHibrida() {

        TypedQuery<Categoria> typedQuery = entityManager.createNamedQuery("Categoria.listar", Categoria.class);

        List<Categoria> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));

    }
}
