package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _40_SUBQUERIES_com_ANY extends EntityManagerTest {

/*
    - ANY e SOME são sinônimos no JPQL (assim como no SQL padrão)
       - O efeito é o mesmo. Por legibilidade o mais comum é usar o ANY.
*/

    @Test
    public void pesquisarCom_ANY1() { // busca todos os produtos que já foram pedidos pelo menos uma vez pelo preço atual

        String jpql = "SELECT p FROM Produto p "
            + " WHERE p.preco = ANY ( "
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
    public void pesquisarCom_ANY2() { // busca todos os produtos que já foram vendidos por um preço diferente do atual

        String jpql = " SELECT p FROM Produto p"
                + " WHERE EXISTS ( "               // buscando apenas os produtos que tiveram algum pedido
                + "     SELECT 1"                  // porque a expressão com ANY em consulta com retorno vazio é TRUE (verdade vacuamente).
                + "     FROM ItemPedido i"
                + "     WHERE i.produto = p"
                + " )"
                + " AND p.preco <> ANY ("
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
    public void pesquisarCom_SOME() { // busca todos os produtos que já foram vendidos por um preço diferente do atual

        String jpql = " SELECT p FROM Produto p"
                + " WHERE EXISTS ( "               // buscando apenas os produtos que tiveram algum pedido
                + "     SELECT 1"                  // porque a expressão com ANY em consulta com retorno vazio é TRUE (verdade vacuamente).
                + "     FROM ItemPedido i"
                + "     WHERE i.produto = p"
                + " )"
                + " AND p.preco <> SOME ("
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

}
