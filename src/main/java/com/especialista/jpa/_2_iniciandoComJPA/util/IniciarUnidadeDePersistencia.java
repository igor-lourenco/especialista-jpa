package com.especialista.jpa._2_iniciandoComJPA.util;

import com.especialista.jpa._2_iniciandoComJPA.modelo.ProdutoIniciandoComJPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class IniciarUnidadeDePersistencia {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory(("EspecialistaJPADB-PU"));

        EntityManager entityManager = entityManagerFactory.createEntityManager();

//      Faça seus testes aqui.

        ProdutoIniciandoComJPA produto = entityManager.find(ProdutoIniciandoComJPA.class, 1);

        System.out.println("============================");
        System.out.println(produto);
        System.out.println("============================");

        entityManager.close();
        entityManagerFactory.close();


    }
}
