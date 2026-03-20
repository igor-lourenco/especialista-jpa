package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class _41_Exercicio_com_SUBQUERIES extends EntityManagerTest {


    @Test
    public void exercicioSubQuerieUsando_IN() { // busca todos os pedidos que contém algum produto da categoria id 2

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT item"
//          + " FROM ItemPedido item "
//          + " JOIN FETCH item.produto prod1 "
//          + " LEFT JOIN FETCH prod1.estoque est1 "
//          + " JOIN FETCH prod1.categorias c1 "
//          + " WHERE prod1.id IN ( "
//          + "     SELECT prod2.id FROM Categoria c2 "
//          + "      JOIN c2.produtos prod2 "
//          + "      WHERE c2.id = 2 "
//          + ")";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ItemPedido> criteriaQuery = criteriaBuilder.createQuery(ItemPedido.class); // Query vai retornar ItemPedido

        Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class); // FROM ItemPedido item

        criteriaQuery.select(root); // SELECT item

        Join<ItemPedido, Produto> joinProduto =  // JOIN FETCH item.produto prod1
            (Join<ItemPedido, Produto>) root.<ItemPedido, Produto>fetch(ItemPedido_.PRODUTO);

        joinProduto.fetch(Produto_.estoque, JoinType.LEFT); // LEFT JOIN FETCH prod1.estoque est1
        joinProduto.fetch(Produto_.categorias);                 // JOIN FETCH prod1.categorias c1


        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);         // SubQuery vai retornar Integer
        Root<Categoria> subRoot = subquery.from(Categoria.class);              // FROM Categoria c2

        Join<Categoria, Produto> subJoinProduto = subRoot.join(Categoria_.produtos); // JOIN c2.produtos prod2
        subquery.select(subJoinProduto.get(Produto_.id));                      //  SELECT prod2.id

        subquery.where(criteriaBuilder.equal( subRoot, 2));              //  WHERE c2.id = 2


        criteriaQuery.where(// WHERE prod1.id IN (SubQuery)
            joinProduto.get(Produto_.id).in(subquery));


        TypedQuery<ItemPedido> typedQuery =
//          entityManager.createQuery(jpql, ItemPedido.class)
            entityManager.createQuery(criteriaQuery);


        List<ItemPedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
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

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT c FROM Cliente c "
//          + " WHERE ( "
//          + "     SELECT COUNT(ped2.cliente) FROM Pedido ped2 "
//          + "      WHERE ped2.cliente = c "
//          + ") >= 2";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class); // Query vai retornar Cliente

        Root<Cliente> root = criteriaQuery.from(Cliente.class); // FROM Cliente c

        criteriaQuery.select(root); // SELECT c


        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);    // SubQuery vai retornar Integer
        Root<Pedido> subRoot = subquery.from(Pedido.class);              // FROM Pedido ped2

        subquery.select(criteriaBuilder.count(subRoot.get(Pedido_.cliente)).as(Integer.class)); // SELECT COUNT(ped2.cliente)

        subquery.where(criteriaBuilder.equal( subRoot.get(Pedido_.cliente), root));              //  WHERE ped2.cliente = c


        criteriaQuery.where(// WHERE (SubQuery) >= 2
           criteriaBuilder.greaterThanOrEqualTo(subquery, 2));


        TypedQuery<Cliente> typedQuery =
//          entityManager.createQuery(jpql, Cliente.class)
            entityManager.createQuery(criteriaQuery);


        List<Cliente> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }


    @Test
    public void exercicioUsando_EXISTS() {  // busca todos os produtos que foram pedidos com o preço atualizado

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT prod1 "
//          + " FROM Produto prod1 "
//          + " LEFT JOIN FETCH prod1.estoque est1 "
//          + " WHERE EXISTS ( "
//          + "     SELECT 1 "
//          + "      FROM ItemPedido item "
//          + "      WHERE item.produto = prod1 "
//          + "      AND item.precoProduto <> prod1.preco " // DIFERENTE
//          + ")";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); // Query vai retornar Produto

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Produto prod1

        criteriaQuery.select(root); // SELECT prod1

        root.fetch(Produto_.estoque, JoinType.LEFT); // LEFT JOIN FETCH prod1.estoque est1


        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);    // SubQuery vai retornar Integer
        Root<ItemPedido> subRoot = subquery.from(ItemPedido.class);       // FROM ItemPedido item

        subquery.select(criteriaBuilder.literal(1));               // SELECT 1

        subquery.where(
            criteriaBuilder.equal( subRoot.get(ItemPedido_.produto), root),                   // WHERE item.produto = prod1
                criteriaBuilder.notEqual(subRoot.get(ItemPedido_.precoProduto), root.get(Produto_.preco)) //  AND item.precoProduto <> prod1.preco
            );


        criteriaQuery.where(// WHERE (SubQuery) >= 2
           criteriaBuilder.exists(subquery)
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
