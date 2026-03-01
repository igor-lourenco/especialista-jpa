package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _39_SUBQUERIES_com_ALL extends EntityManagerTest {

    @Test
    public void pesquisarCom_ALL1() { // busca todos os produtos que sempre foram vendidos pelo preco atual

        String jpql = "SELECT p FROM Produto p "
            + " WHERE p.preco = ALL ( "
            + "     SELECT precoProduto FROM ItemPedido "
            + "      WHERE produto = p "
            + ")";

        logger.info("Buscando Produto...");
        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }

    @Test
    public void pesquisarCom_ALL2() { // busca todos os produtos que não foram vendidos mais após aumentar o preço


        String jpql = " SELECT p FROM Produto p"
                + " WHERE EXISTS ( "               // buscando apenas os produtos que tiveram algum pedido
                + "     SELECT 1"                  // porque a expressão com ALL em consulta com retorno vazio é TRUE (verdade vacuamente).
                + "     FROM ItemPedido i"
                + "     WHERE i.produto = p"
                + " )"
                + " AND p.preco > ALL ("
                + "     SELECT i.precoProduto"
                + "     FROM ItemPedido i"
                + "     WHERE i.produto = p"
                + " ) ";

        logger.info("Buscando Produto...");
        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }

    @Test
    public void pesquisarCom_MAX() { // outra alternativa de busca todos os produtos que não foram vendidos mais após aumentar o preço


        String jpql = "SELECT p FROM Produto p "
            + " WHERE p.preco > ( "
            + "     SELECT MAX(precoProduto) FROM ItemPedido "
            + "      WHERE produto = p "
            + ")";

        logger.info("Buscando Produto...");
        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }
}
