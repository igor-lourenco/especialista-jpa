package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.ItemPedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.ItemPedido_;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;

public class _44_Exercicio_com_ALL extends EntityManagerTest {


    @Test
    public void exercicioCom_ALL() { //  Busca todos os produtos que sempre foram pedidos pelo mesmo preço

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT DISTINCT prod1 FROM ItemPedido item "
//          + " JOIN  item.produto prod1 "
//          + " WHERE item.precoProduto = ALL ( "
//          + "     SELECT item2.precoProduto FROM ItemPedido item2 "
//          + "      WHERE item2.produto = prod1 "
//          + "      AND item.id <> item2.id "
//          + ")";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); // Query vai retornar Produto

        Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);              // FROM ItemPedido item
        criteriaQuery.select(root.get(ItemPedido_.produto)).distinct(true);  // SELECT DISTINCT prod1

        Join<ItemPedido, Produto> joinProduto = root.join(ItemPedido_.produto);       // JOIN  item.produto prod1

//      ---
        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);     // SubQuery vai retornar BigDecimal
        Root<ItemPedido> subRoot = subquery.from(ItemPedido.class);              // FROM ItemPedido item2

        subquery.select(subRoot.get(ItemPedido_.precoProduto));                  //   SELECT item2.precoProduto

        subquery.where(
            criteriaBuilder.equal( subRoot.get(ItemPedido_.produto), joinProduto), // WHERE item2.produto = prod1
            criteriaBuilder.notEqual(root, subRoot)                                            // AND item.id <> item2.id
        );
//      ---

        criteriaQuery.where(//   WHERE item.precoProduto = ALL (SubQuery)
            criteriaBuilder.equal(root.get(ItemPedido_.precoProduto), criteriaBuilder.all(subquery))
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
