package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _38_Exercicio_com_SUBQUERIES extends EntityManagerTest {

    @Test
    public void exercicioSubQuerieUsando_IN() { // busca todos os pedidos que contém algum produto da categoria id 2

        String jpql = "SELECT item"
            + " FROM ItemPedido item "
            + " JOIN FETCH item.produto prod1 "
            + " LEFT JOIN FETCH prod1.estoque est1 "
            + " JOIN FETCH prod1.categorias c1 "
            + " WHERE prod1.id IN ( "
            + "     SELECT prod2.id FROM Categoria c2 "
            + "      JOIN c2.produtos prod2 "
            + "      WHERE c2.id = 2 "
            + ")";

        logger.info("Buscando Pedido...");
        TypedQuery<ItemPedido> typedQuery = entityManager.createQuery(jpql, ItemPedido.class);

        List<ItemPedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado... " + lista.size());
        lista.forEach(item -> {
            List<Categoria> categorias = item.getProduto().getCategorias();
            categorias.forEach(c -> logger.info(
                  "ID: " + item.getPedido().getId()
                + ", CATEGORIA: " + c.getId()
                + ", PRODUTO: " + item.getProduto().getId()));
        });
    }

    @Test
    public void exercicioUsando_SubQuerie() { // busca todos os clientes que fizeram mais de 2 ou mais pedidos

        String jpql = "SELECT c FROM Cliente c "
            + " WHERE ( "
            + "     SELECT COUNT(ped2.cliente) FROM Pedido ped2 "
            + "      WHERE ped2.cliente = c "
            + ") >= 2";

        logger.info("Buscando Pedido...");
        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }

    @Test
    public void exercicioUsando_EXISTS() { // busca todos os produtos que foram pedidos com o preço atualizado

        String jpql = "SELECT prod1 "
            + " FROM Produto prod1 "
            + " LEFT JOIN FETCH prod1.estoque est1 "
            + " WHERE EXISTS ( "
            + "     SELECT 1 "
            + "      FROM ItemPedido item "
            + "      WHERE item.produto = prod1 "
            + "      AND item.precoProduto <> prod1.preco " // DIFERENTE
            + ")";

        logger.info("Buscando Pedido...");
        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));

    }
}
