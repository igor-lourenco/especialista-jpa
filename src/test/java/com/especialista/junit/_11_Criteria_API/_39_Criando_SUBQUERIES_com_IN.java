package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;

public class _39_Criando_SUBQUERIES_com_IN extends EntityManagerTest {


    @Test
    public void usandoExpresao_IN() {
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT ped"
//          + " FROM Pedido ped "
//          + " WHERE ped.id IN ( "
//          + "     SELECT ped2.id "
//          + "       FROM ItemPedido item"
//          + "       JOIN item.pedido ped2 "
//          + "       JOIN item.produto pro "
//          + "       WHERE pro.preco > 100 "
//          + ")";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        criteriaQuery.select(root); // SELECT p

//      ---
        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);  // SubQuery vai retornar Integer
        Root<ItemPedido> subRoot = subquery.from(ItemPedido.class);     // FROM ItemPedido item

        Join<ItemPedido, Pedido> subJoinPedido = subRoot.join(ItemPedido_.pedido); // JOIN item.pedido ped2
        subquery.select(subJoinPedido.get(Pedido_.id));                          // SELECT ped2.id

        Join<ItemPedido, Produto> subJoinProduto = subRoot.join(ItemPedido_.produto);  // JOIN item.produto pro
        subquery.where(criteriaBuilder.greaterThan(                                  // // WHERE pro.preco > 100
            subJoinProduto.get(Produto_.preco), new BigDecimal(100)));
//      ---

        criteriaQuery.where(// WHERE ped.id IN (SubQuery)
            root.get(Pedido_.id).in(subquery));


        TypedQuery<Pedido> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
            entityManager.createQuery(criteriaQuery);


        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }


    @Test
    public void semUsarExpresao_IN() {
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p" // -> Sem usar o IN pode trazer pedido duplicado se tiver mais um item de pedido acima de 100
//          + " FROM Pedido p "
//          + " JOIN FETCH p.itensPedido item"
//          + " JOIN item.produto prod "
//          + " WHERE prod.preco > 100 ";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        criteriaQuery.select(root); // SELECT p

        Join<Pedido, ItemPedido> joinItemPedido =
            (Join<Pedido, ItemPedido>) root.<Pedido, ItemPedido>fetch(Pedido_.ITENS_PEDIDO); // JOIN FETCH p.itensPedido item

        Join<ItemPedido, Produto> joinProduto =
            (Join<ItemPedido, Produto> ) joinItemPedido.<ItemPedido, Produto>fetch(ItemPedido_.PRODUTO); //  JOIN item.produto prod


        criteriaQuery.where(criteriaBuilder.greaterThan(  // WHERE prod.preco > 100
            joinProduto.get(Produto_.preco), new BigDecimal("100")));


        TypedQuery<Pedido> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
            entityManager.createQuery(criteriaQuery);


        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }


}
