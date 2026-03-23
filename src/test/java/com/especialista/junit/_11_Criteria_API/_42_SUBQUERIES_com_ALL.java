package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;

public class _42_SUBQUERIES_com_ALL extends EntityManagerTest {


    @Test
    public void pesquisarCom_ALL1() { // busca todos os produtos que sempre foram vendidos pelo preco atual

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Produto p "
//          + " WHERE p.preco = ALL ( "
//          + "     SELECT item.precoProduto FROM ItemPedido item"
//          + "      WHERE item.produto = p "
//          + ")";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); // Query vai retornar Produto

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Produto p
        criteriaQuery.select(root);                              // SELECT p

//      ---
        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);     // SubQuery vai retornar BigDecimal
        Root<ItemPedido> subRoot = subquery.from(ItemPedido.class);              // FROM ItemPedido item

        subquery.select(subRoot.get(ItemPedido_.precoProduto));                                //  SELECT item.precoProduto
        subquery.where(criteriaBuilder.equal( subRoot.get(ItemPedido_.produto), root));  // WHERE item.produto = p
//      ---

        criteriaQuery.where(//  WHERE p.preco = ALL (SubQuery)
            criteriaBuilder.equal(root.get(Produto_.preco), criteriaBuilder.all(subquery))
        );


        TypedQuery<Produto> typedQuery =
//          entityManager.createQuery(jpql, Produto.class)
            entityManager.createQuery(criteriaQuery);


        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }


    @Test
    public void pesquisarCom_ALL2() { // busca todos os produtos que não foram vendidos mais após aumentar o preço

//      Convertendo uma JPQL em Criteria Query
//      String jpql = " SELECT p FROM Produto p"
//          + " WHERE EXISTS ( "               // buscando apenas os produtos que tiveram algum pedido
//          + "     SELECT 1"                  // porque a expressão com ALL em consulta com retorno vazio é TRUE (verdade vacuamente).
//          + "     FROM ItemPedido i"
//          + "     WHERE i.produto = p"
//          + " )"
//          + " AND p.preco > ALL ("
//          + "     SELECT i.precoProduto"
//          + "     FROM ItemPedido i"
//          + "     WHERE i.produto = p"
//          + " ) ";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); // Query vai retornar Produto

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Produto p
        criteriaQuery.select(root);                              // SELECT p

//      ---
        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);     // SubQuery vai retornar BigDecimal
        Root<ItemPedido> subRoot = subquery.from(ItemPedido.class);              // FROM ItemPedido item

        subquery.select(subRoot.get(ItemPedido_.precoProduto));                                //  SELECT item.precoProduto
        subquery.where(criteriaBuilder.equal( subRoot.get(ItemPedido_.produto), root));  // WHERE item.produto = p
//      ---

        criteriaQuery.where(//  WHERE EXISTS (SubQuery) AND p.preco > ALL (SubQuery)
            criteriaBuilder.exists(subquery),
            criteriaBuilder.greaterThan(root.get(Produto_.preco), criteriaBuilder.all(subquery))
        );


        TypedQuery<Produto> typedQuery =
//          entityManager.createQuery(jpql, Produto.class)
            entityManager.createQuery(criteriaQuery);


        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }


}
