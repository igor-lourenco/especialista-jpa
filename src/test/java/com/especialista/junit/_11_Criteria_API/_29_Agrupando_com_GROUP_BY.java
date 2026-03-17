package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

public class _29_Agrupando_com_GROUP_BY extends EntityManagerTest {


    @Test
    public void agrupandoResultados1(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT c.nome, COUNT(p.id) " // Agrupa quantidade de produtos por Categoria
//          + " FROM Categoria c "
//          + " JOIN c.produtos p "
//          + " GROUP BY c.id";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // Query vai retornar Object[]

        Root<Categoria> root = criteriaQuery.from(Categoria.class); // FROM Categoria c

        Join<Categoria, Produto> joinProduto = root.join(Categoria_.produtos); // JOIN c.produtos p

        criteriaQuery.multiselect(                                 // SELECT
            root.get(Categoria_.nome),                  // c.nome
            criteriaBuilder.count(joinProduto.get(Produto_.id)) // COUNT(p.id)

        );

        criteriaQuery.groupBy(root.get(Categoria_.id));  // Agrupa quantidade de produtos por Categoria

        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("Categoria: " + c[0] + " - Quantidade de produtos: " + c[1]));
    }


    @Test
    public void agrupandoResultados2(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT YEAR(p.dataCriacao), MONTH(p.dataCriacao), SUM(p.total) " // Agrupa total de vendas por mês
//          + " FROM Pedido p "
//          + " GROUP BY YEAR(p.dataCriacao), MONTH(p.dataCriacao) ";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // Query vai retornar Object[]

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p


        criteriaQuery.multiselect( // SELECT
            criteriaBuilder.function("YEAR", LocalDateTime.class, root.get(Pedido_.dataCriacao)), // YEAR(p.dataCriacao)
            criteriaBuilder.function("MONTH", LocalDateTime.class, root.get(Pedido_.dataCriacao)), //  MONTH(p.dataCriacao)
            criteriaBuilder.sum(root.get(Pedido_.total)) // SUM(p.total)

        );

        criteriaQuery.groupBy( // Agrupa total de vendas por mês
            criteriaBuilder.function("YEAR", LocalDateTime.class, root.get(Pedido_.dataCriacao)),
            criteriaBuilder.function("MONTH", LocalDateTime.class, root.get(Pedido_.dataCriacao))
        );

        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(c[0] + "/" + c[1] + " TOTAL: " + c[2]));
    }


    @Test
    public void agrupandoResultados3(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT YEAR(p.dataCriacao), MONTH(p.dataCriacao), SUM(p.total) " // Agrupa total de vendas por mês apenas no ano 2025
//          + " FROM Pedido p "
//          + " WHERE YEAR(p.dataCriacao) = 2025 "
//          + " GROUP BY YEAR(p.dataCriacao), MONTH(p.dataCriacao) ";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // Query vai retornar Object[]

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p


        criteriaQuery.multiselect( // SELECT
            criteriaBuilder.function("YEAR", LocalDateTime.class, root.get(Pedido_.dataCriacao)), // YEAR(p.dataCriacao)
            criteriaBuilder.function("MONTH", LocalDateTime.class, root.get(Pedido_.dataCriacao)), //  MONTH(p.dataCriacao)
            criteriaBuilder.sum(root.get(Pedido_.total)) // SUM(p.total)

        );

        criteriaQuery.where(criteriaBuilder.equal( // WHERE YEAR(p.dataCriacao) = 2025
            criteriaBuilder.function("YEAR", LocalDateTime.class, root.get(Pedido_.dataCriacao)),
            2025
        ));

        criteriaQuery.groupBy( // Agrupa total de vendas por mês
            criteriaBuilder.function("YEAR", LocalDateTime.class, root.get(Pedido_.dataCriacao)),
            criteriaBuilder.function("MONTH", LocalDateTime.class, root.get(Pedido_.dataCriacao))
        );

        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(c[0] + "/" + c[1] + " TOTAL: " + c[2]));
    }


    @Test
    public void agrupandoResultados4(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT c.nome, SUM(item.precoProduto) " // Agrupa total de vendas por categoria
//          + " FROM ItemPedido item "
//          + " JOIN item.produto p "
//          + " JOIN p.categorias c"
//          + " GROUP BY c.id ";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // Query vai retornar Object[]

        Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class); // FROM ItemPedido item

        Join<ItemPedido, Produto> joinProduto = root.join(ItemPedido_.produto);     // JOIN item.produto p
        Join<Produto, Categoria> joinCategoria = joinProduto.join(Produto_.categorias); // JOIN p.categorias c

        criteriaQuery.multiselect(                                     // SELECT
            joinCategoria.get(Categoria_.nome),             // c.nome
            criteriaBuilder.sum(root.get(ItemPedido_.precoProduto)) //  SUM(item.precoProduto)
        );


        criteriaQuery.groupBy( // Agrupa total de vendas por categoria
            joinCategoria.get(Categoria_.id)
        );

        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("Categoria: " + c[0] + " - TOTAL: " + c[1]));
    }



}
