package com.especialista.junit._10_JPQL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _25_Funcoes_para_Colecoes extends EntityManagerTest {


    @Test
    public void usando_SIZE1() {

        String jpql = "SELECT SIZE(p.itensPedido) "
            + " FROM Pedido p ";


        logger.info("Buscando Quantidade de itensPedido...");
        TypedQuery<Integer> typedQuery = entityManager.createQuery(jpql, Integer.class);

        List<Integer> pedidos = typedQuery.getResultList();

        Assert.assertFalse(pedidos.isEmpty());

        pedidos.forEach(c -> logger.info("Quantidade total de itensPedido: " + c));
    }
    @Test

    public void usando_SIZE2() {

        String jpql = "SELECT SIZE(p.itensPedido) "
            + " FROM Pedido p "
            + " WHERE SIZE(p.itensPedido) > 1 ";


        logger.info("Buscando Quantidade de itensPedido...");
        TypedQuery<Integer> typedQuery = entityManager.createQuery(jpql, Integer.class);

        List<Integer> pedidos = typedQuery.getResultList();

        Assert.assertFalse(pedidos.isEmpty());

        pedidos.forEach(c -> logger.info("Quantidade total de itensPedido maior que 1: " + c));
    }


}
