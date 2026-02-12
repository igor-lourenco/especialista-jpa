package com.especialista.junit._10_JPQL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _4_Trabalhando_com_projecoes extends EntityManagerTest {

    @Test
    public void projetarOResultado() {
/*
        Projeção no contexto do JPA é quando você não busca a entidade completa,
        e sim um recorte/forma específica dos dados: certos atributos (escalares),
        agregações (ex.: count, sum) ou um DTO com apenas o que você precisa.
*/
        String jpql = "select id, nome from Cliente"; // retorna uma lista de id e nome dos Clientes

        logger.info("Buscando uma lista apenas com id e nome dos Clientes...");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> listaComIdENome = typedQuery.getResultList();

        Assert.assertTrue(listaComIdENome.get(0).length == 2);

        logger.info("Lista de id e nome dos Clientes retornados...");
        listaComIdENome.forEach(array ->
            logger.info(array[0] + " - " + array[1]));

    }
}
