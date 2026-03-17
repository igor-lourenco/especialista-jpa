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

public class _30_Exercicio_Agrupando_com_GROUP_BY extends EntityManagerTest {


    @Test
    public void agrupandoResultados1(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT c.nome, SUM(p.total) " // Agrupa total de vendas por cliente
//          + " FROM Pedido p "
//          + " JOIN p.cliente c "
//          + " GROUP BY c.id";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // Query vai retornar Object[]

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        Join<Pedido, Cliente> joinCliente = root.join(Pedido_.cliente); // JOIN p.cliente c

        criteriaQuery.multiselect(                          // SELECT
            joinCliente.get(Cliente_.nome),      // c.nome
            criteriaBuilder.sum(root.get(Pedido_.total)) // SUM(p.total)

        );

        criteriaQuery.groupBy(joinCliente.get(Cliente_.id));  // Agrupa total de vendas por cliente

        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("Cliente: " + c[0] + " - TOTAL: " + c[1]));
    }


    @Test
    public void agrupandoResultados2(){ // Agrupa total de vendas por dia e por categoria
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT YEAR(ped.dataCriacao), MONTH(ped.dataCriacao), DAY(ped.dataCriacao), c.nome, SUM(item.precoProduto * item.quantidade) "
//         + " FROM ItemPedido item "
//          + " JOIN item.pedido ped "
//          + " JOIN item.produto prod "
//          + " JOIN prod.categorias c"
//          + " GROUP BY YEAR(ped.dataCriacao), MONTH(ped.dataCriacao), DAY(ped.dataCriacao), c.id ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // Query vai retornar Object[]

        Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class); // FROM ItemPedido item

        Join<ItemPedido, Pedido> joinPedido = root.join(ItemPedido_.pedido);        // JOIN item.pedido ped
        Join<ItemPedido, Produto> joinProduto = root.join(ItemPedido_.produto);     // JOIN item.produto prod
        Join<Produto, Categoria> joinCategoria = joinProduto.join(Produto_.categorias); // JOIN prod.categorias c

        criteriaQuery.multiselect(                                                                                                    // SELECT
            criteriaBuilder.function("YEAR", LocalDateTime.class, joinPedido.get(Pedido_.dataCriacao)), // YEAR(p.dataCriacao)
            criteriaBuilder.function("MONTH", LocalDateTime.class, joinPedido.get(Pedido_.dataCriacao)),           // MONTH(p.dataCriacao)
            criteriaBuilder.function("DAY", LocalDateTime.class, joinPedido.get(Pedido_.dataCriacao)),             // DAY(p.dataCriacao)
            joinCategoria.get(Categoria_.nome),                                                                                       // c.nome
            criteriaBuilder.sum(criteriaBuilder.prod(root.get(ItemPedido_.precoProduto), root.get(ItemPedido_.quantidade))) // SUM(item.precoProduto * item.quantidade)
        );

        criteriaQuery.groupBy( // Agrupa total de vendas por dia e por categoria
            criteriaBuilder.function("YEAR", LocalDateTime.class, joinPedido.get(Pedido_.dataCriacao)), // YEAR(p.dataCriacao)
            criteriaBuilder.function("MONTH", LocalDateTime.class, joinPedido.get(Pedido_.dataCriacao)),           // MONTH(p.dataCriacao)
            criteriaBuilder.function("DAY", LocalDateTime.class, joinPedido.get(Pedido_.dataCriacao)),             // DAY(p.dataCriacao)
            joinCategoria.get(Categoria_.id)                                                                                          // c.id
        );
        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(c[0] + "/" + c[1] + "/" + c[2] + " - " + c[3] + " TOTAL: " + c[4]));
    }



}
