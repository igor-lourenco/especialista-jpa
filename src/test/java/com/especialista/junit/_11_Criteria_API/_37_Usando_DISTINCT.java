package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.List;

public class _37_Usando_DISTINCT extends EntityManagerTest {


    @Test
    public void semUsar_DISTINCT() {
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p"
//          + " FROM Pedido p "
//          + " JOIN FETCH p.itensPedido item "
//          + " JOIN item.produto prod "
//          + " WHERE prod.id IN (1, 2, 3) ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        Join<Pedido, ItemPedido> joinItensPedido =
            (Join<Pedido, ItemPedido>) root.<Pedido, ItemPedido>fetch(Pedido_.ITENS_PEDIDO);// JOIN FETCH p.itensPedido item

        Join<ItemPedido, Produto> joinProduto =
            (Join<ItemPedido, Produto>) joinItensPedido.<ItemPedido, Produto>fetch(ItemPedido_.PRODUTO); // JOIN FETCH itens.produto prod

        criteriaQuery.select(root); // SELECT p

        List<Integer> in = Arrays.asList(1, 2, 3);

        criteriaQuery.where(joinProduto.get(Produto_.id).in(in)); // WHERE prod.id IN (1, 2, 3)

        TypedQuery<Pedido> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
            entityManager.createQuery(criteriaQuery);


        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }

    @Test
    public void usando_DISTINCT() {
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT DISTINCT p"
//          + " FROM Pedido p "
//          + " JOIN FETCH p.itensPedido item "
//          + " JOIN item.produto prod "
//          + " WHERE prod.id IN (1, 2, 3) ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        Join<Pedido, ItemPedido> joinItensPedido =
            (Join<Pedido, ItemPedido>) root.<Pedido, ItemPedido>fetch(Pedido_.ITENS_PEDIDO);// JOIN FETCH p.itensPedido item

        Join<ItemPedido, Produto> joinProduto =
            (Join<ItemPedido, Produto>) joinItensPedido.<ItemPedido, Produto>fetch(ItemPedido_.PRODUTO); // JOIN FETCH itens.produto prod

        List<Integer> in = Arrays.asList(1, 2, 3);

        criteriaQuery.select(root).distinct(true); // SELECT DISTINCT p

        criteriaQuery.where(joinProduto.get(Produto_.id).in(in)); // WHERE prod.id IN (1, 2, 3)

        TypedQuery<Pedido> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
            entityManager.createQuery(criteriaQuery);


        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }



}
