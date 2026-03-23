package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.ItemPedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.ItemPedido_;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto_;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.math.BigDecimal;
import java.util.List;

public class _43_SUBQUERIES_com_ANY extends EntityManagerTest {


    @Test
    public void pesquisarCom_ANY1() {  // busca todos os produtos que já foram pedidos pelo menos uma vez pelo preço atual

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Produto p "
//          + " WHERE p.preco = ANY ( "
//          + "     SELECT precoProduto FROM ItemPedido "
//          + "      WHERE produto = p "
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

        criteriaQuery.where(//  WHERE p.preco = ANY (SubQuery)
            criteriaBuilder.equal(root.get(Produto_.preco), criteriaBuilder.any(subquery))
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
    public void pesquisarCom_ANY2() {  // busca todos os produtos que já foram vendidos por um preço diferente do atual

//      Convertendo uma JPQL em Criteria Query
//      String jpql = " SELECT p FROM Produto p"
//          + " WHERE EXISTS ( "               // buscando apenas os produtos que tiveram algum pedido
//          + "     SELECT 1"                  // porque a expressão com ANY em consulta com retorno vazio é TRUE (verdade vacuamente).
//          + "     FROM ItemPedido i"
//          + "     WHERE i.produto = p"
//          + " )"
//          + " AND p.preco <> ANY ("
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


        criteriaQuery.where(//  WHERE EXISTS (SubQuery) AND p.preco <> ANY (SubQuery)
            criteriaBuilder.exists(subquery),
            criteriaBuilder.notEqual(root.get(Produto_.preco), criteriaBuilder.any(subquery))
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
