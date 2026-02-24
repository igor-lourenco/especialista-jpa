package com.especialista.junit._10_JPQL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _28_Agrupando_com_GROUP_BY extends EntityManagerTest {


    @Test
    public void agrupandoResultados1() {

        String jpql = "SELECT c.nome, COUNT(p.id) " // Agrupa quantidade de produtos por Categoria
            + " FROM Categoria c "
            + " JOIN c.produtos p "
            + " GROUP BY c.id";

        logger.info("Buscando quantidade de produtos por Categoria");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info("Categoria: " + c[0] + " - Quantidade de produtos: " + c[1]));
    }

    @Test
    public void agrupandoResultados2() {

        String jpql = "SELECT YEAR(p.dataCriacao), MONTH(p.dataCriacao), SUM(p.total) " // Agrupa total de vendas por mês
            + " FROM Pedido p "
            + " GROUP BY YEAR(p.dataCriacao), MONTH(p.dataCriacao) ";

        logger.info("Buscando total de vendas por mês");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info(c[0] + "/" + c[1] + " TOTAL: " + c[2]));
    }

    @Test
    public void agrupandoResultados3() {

        String jpql = "SELECT YEAR(p.dataCriacao), MONTH(p.dataCriacao), SUM(p.total) " // Agrupa total de vendas por mês apenas no ano 2025
            + " FROM Pedido p "
            + " WHERE YEAR(p.dataCriacao) = 2025 "
            + " GROUP BY YEAR(p.dataCriacao), MONTH(p.dataCriacao) ";

        logger.info("Buscando total de vendas por mês apenas no ano 2025");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info(c[0] + "/" + c[1] + " TOTAL: " + c[2]));
    }

    @Test
    public void agrupandoResultados4() {

        String jpql = "SELECT c.nome, SUM(item.precoProduto) " // Agrupa total de vendas por categoria
            + " FROM ItemPedido item "
            + " JOIN item.produto p "
            + " JOIN p.categorias c"
            + " GROUP BY c.id ";

        logger.info("Buscando total de vendas por categoria");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info("Categoria: " + c[0] + " - TOTAL: " + c[1]));
    }
}
