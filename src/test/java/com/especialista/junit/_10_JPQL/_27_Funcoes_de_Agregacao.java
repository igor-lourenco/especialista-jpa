package com.especialista.junit._10_JPQL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _27_Funcoes_de_Agregacao extends EntityManagerTest {

//  AVG → Retorna Double
//  COUNT → Retorna Long
//  MIN → Retorna mesmo tipo da propriedade passada como parâmetro
//  MAX → Retorna mesmo tipo da propriedade passada como parâmetro
//  SUM → Retorna mesmo tipo da propriedade passada como parâmetro


    @Test
    public void usandoFuncao_AVG() {

        String jpql = "SELECT AVG(p.total) " // Tira a média
            + " FROM Pedido p ";

        logger.info("Buscando Pedidos");
        TypedQuery<Number> typedQuery = entityManager.createQuery(jpql, Number.class);

        List<Number> pedidos = typedQuery.getResultList();

        Assert.assertFalse(pedidos.isEmpty());

        logger.info("Média de valor de total de pedidos...");
        pedidos.forEach(c -> logger.info("Média de valor total de Pedidos: " + c));
    }

    @Test
    public void usandoFuncao_COUNT() {

        String jpql = "SELECT COUNT(p) " // Faz contagem de registros
            + " FROM Pedido p ";

        logger.info("Buscando Pedidos");
        TypedQuery<Number> typedQuery = entityManager.createQuery(jpql, Number.class);

        List<Number> pedidos = typedQuery.getResultList();

        Assert.assertFalse(pedidos.isEmpty());

        logger.info("Contando os registros total dos Pedidos criados...");
        pedidos.forEach(c -> logger.info("Total de pedidos: " + c));
    }

    @Test
    public void usandoFuncao_MIN() {

        String jpql = "SELECT MIN(p.total) " // Retorna o menor valor
            + " FROM Pedido p ";

        logger.info("Buscando Pedidos");
        TypedQuery<Number> typedQuery = entityManager.createQuery(jpql, Number.class);

        List<Number> pedidos = typedQuery.getResultList();

        Assert.assertFalse(pedidos.isEmpty());

        logger.info("Pedido com menor valor...");
        pedidos.forEach(c -> logger.info("Pedido com menor valor: " + c));
    }

    @Test
    public void usandoFuncao_MAX() {

        String jpql = "SELECT MAX(p.total) " // Retorna o maior valor
            + " FROM Pedido p ";

        logger.info("Buscando Pedidos");
        TypedQuery<Number> typedQuery = entityManager.createQuery(jpql, Number.class);

        List<Number> pedidos = typedQuery.getResultList();

        Assert.assertFalse(pedidos.isEmpty());

        logger.info("Pedido com maior valor...");
        pedidos.forEach(c -> logger.info("Pedido com maior valor: " + c));
    }

    @Test
    public void usandoFuncao_SUM() {

        String jpql = "SELECT SUM(p.total) " // Retorna a soma de todos os valores
            + " FROM Pedido p ";

        logger.info("Buscando Pedidos");
        TypedQuery<Number> typedQuery = entityManager.createQuery(jpql, Number.class);

        List<Number> pedidos = typedQuery.getResultList();

        Assert.assertFalse(pedidos.isEmpty());

        logger.info("Soma total de todos os pedidos...");
        pedidos.forEach(c -> logger.info("Soma total: " + c));
    }



}
