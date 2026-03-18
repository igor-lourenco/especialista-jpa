package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class _33_Utilizando_WHERE_com_GROUP_BY extends EntityManagerTest {


    @Test
    public void agrupandoResultados3() { // Agrupa total de vendas por mês apenas no ano corrente

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT YEAR(p.dataCriacao), MONTH(p.dataCriacao), SUM(p.total) "
//          + " FROM Pedido p "
//          + " WHERE YEAR(p.dataCriacao) = YEAR(CURRENT_DATE) "
//          + " GROUP BY YEAR(p.dataCriacao), MONTH(p.dataCriacao) ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // Query vai retornar Object[]

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p


        Expression<Integer> anoCriacaoPedido = criteriaBuilder.function("YEAR", Integer.class, root.get(Pedido_.dataCriacao));
        Expression<Integer> anoCorrente = criteriaBuilder.function("YEAR", Integer.class, criteriaBuilder.currentDate());
        Expression<Integer> mesCriacaoPedido = criteriaBuilder.function("MONTH", Integer.class, root.get(Pedido_.dataCriacao));


        criteriaQuery.multiselect(                          // SELECT
            anoCriacaoPedido,                    //  YEAR(p.dataCriacao)
            mesCriacaoPedido,                               // MONTH(p.dataCriacao)
            criteriaBuilder.sum(root.get(Pedido_.total)) // SUM(p.total)
        );

        criteriaQuery.where( // WHERE YEAR(p.dataCriacao) = YEAR(CURRENT_DATE)
            criteriaBuilder.equal(anoCriacaoPedido, anoCorrente)
        );


        criteriaQuery.groupBy(anoCriacaoPedido, mesCriacaoPedido); // GROUP BY YEAR(p.dataCriacao), MONTH(p.dataCriacao)

        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
            entityManager.createQuery(criteriaQuery);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(c[0] + "/" + getNomeMes( (int) c[1]) + " TOTAL: " + c[2]));
    }


    @Test
    public void agrupandoResultados4() {  // Agrupa total de vendas por categoria no mês corrente

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT c.nome, SUM(item.precoProduto) "
//          + " FROM ItemPedido item "
//          + " JOIN item.produto p "
//          + " JOIN p.categorias c "
//          + " JOIN item.pedido ped "
//          + " WHERE MONTH(ped.dataCriacao) = MONTH(CURRENT_DATE)"
//          + " GROUP BY c.id ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // Query vai retornar Object[]

        Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class); // FROM ItemPedido item

        Join<ItemPedido, Produto> joinProduto = root.join(ItemPedido_.produto);       // JOIN item.produto p
        Join<ItemPedido, Pedido> joinPedido = root.join(ItemPedido_.pedido);          // JOIN p.categorias c
        Join<Produto, Categoria> joinCategorias = joinProduto.join(Produto_.categorias);  // JOIN item.pedido ped

        Expression<Integer> mesCorrente = criteriaBuilder.function("MONTH", Integer.class, criteriaBuilder.currentDate());
        Expression<Integer> mesCriacaoPedido = criteriaBuilder.function("MONTH", Integer.class, joinPedido.get(Pedido_.dataCriacao));


        criteriaQuery.multiselect(                                       // SELECT
            joinCategorias.get(Categoria_.nome),              // c.nome
            criteriaBuilder.sum(root.get(ItemPedido_.precoProduto))   // SUM(item.precoProduto)
        );

        criteriaQuery.where(  //  WHERE MONTH(ped.dataCriacao) = MONTH(CURRENT_DATE)
            criteriaBuilder.equal(mesCriacaoPedido, mesCorrente)
        );


        criteriaQuery.groupBy(joinCategorias.get(Categoria_.id)); //  GROUP BY c.id

        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
            entityManager.createQuery(criteriaQuery);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("Categoria: " + c[0] + " - TOTAL: " + c[1]));
    }


    @Test
    public void agrupandoResultados5() {  // Agrupa total de vendas por cliente nos últimos 3 meses

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT c.nome, SUM(p.total) "
//          + " FROM Pedido p "
//          + " JOIN p.cliente c "
//          + " WHERE YEAR(p.dataCriacao) = YEAR(CURRENT_DATE) "
//          + " AND MONTH(p.dataCriacao) >= (MONTH(CURRENT_DATE) - 3) " // últimos 3 meses corrente
//          + " GROUP BY c.id";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // Query vai retornar Object[]

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        Join<Pedido, Cliente> joinCliente = root.join(Pedido_.cliente);       // JOIN p.cliente c

        Expression<Integer> anoCriacaoPedido = criteriaBuilder.function("YEAR", Integer.class, root.get(Pedido_.dataCriacao));
        Expression<Integer> anoCorrente = criteriaBuilder.function("YEAR", Integer.class, criteriaBuilder.currentDate());

        Expression<Integer> mesCriacaoPedido = criteriaBuilder.function("MONTH", Integer.class, root.get(Pedido_.dataCriacao));
        Expression<Integer> mesCorrente = criteriaBuilder.function("MONTH", Integer.class, criteriaBuilder.currentDate());


        criteriaQuery.multiselect(                             // SELECT
            joinCliente.get(Cliente_.nome),        // c.nome
            criteriaBuilder.sum(root.get(Pedido_.total))   // SUM(p.total)
        );

        criteriaQuery.where(
            criteriaBuilder.and(                                   // WHERE
                criteriaBuilder.equal(anoCriacaoPedido, anoCorrente), // YEAR(p.dataCriacao) = YEAR(CURRENT_DATE)
                criteriaBuilder.greaterThanOrEqualTo(                       //  AND MONTH(p.dataCriacao) >= (MONTH(CURRENT_DATE) - 3)
                    mesCriacaoPedido, (criteriaBuilder.diff(mesCorrente, 3)))
            ));


        criteriaQuery.groupBy(joinCliente.get(Cliente_.id)); //  GROUP BY c.id

        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
            entityManager.createQuery(criteriaQuery);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("Cliente: " + c[0] + " - TOTAL: " + c[1]));
    }


    private static String getNomeMes(int c) {
        Locale br = new Locale("pt", "BR");
        return Month.of((Integer) c).getDisplayName(TextStyle.FULL, br).toUpperCase();
    }
}
