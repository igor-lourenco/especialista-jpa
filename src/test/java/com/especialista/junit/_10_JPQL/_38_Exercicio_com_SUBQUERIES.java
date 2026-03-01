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

        String jpql = "SELECT p FROM Pedido p "
            + " JOIN p.itensPedido item "
            + " JOIN item.produto prod1 "
            + " JOIN prod1.categorias c "
            + " WHERE prod1.id IN ( "
            + "     SELECT prod2.id FROM Produto prod2 "
            + "      JOIN prod2.categorias c "
            + "      WHERE c.id = 2 "
            + ")";

        logger.info("Buscando Pedido...");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(ped -> {

            List<ItemPedido> itensPedido = ped.getItensPedido();

            itensPedido.forEach(item -> {
                List<Categoria> categorias = item.getProduto().getCategorias();
                categorias.forEach(c -> logger.info("ID: " + ped.getId() + ", CATEGORIA: " + c.getId()));
            });

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
    public void exercicioUsando_EXISTS() { // busca todos os produtos que ainda não foram pedidos com o preço atualizado

        String jpql = "SELECT prod1 "
            + " FROM Produto prod1 "
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
