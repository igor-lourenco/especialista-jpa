    package com.especialista.junit._11_Criteria_API;

    import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
    import com.especialista.junit.utils.EntityManagerTest;
    import org.junit.Assert;
    import org.junit.Test;

    import javax.persistence.TypedQuery;
    import javax.persistence.criteria.*;
    import java.math.BigDecimal;
    import java.util.List;

public class _38_Criando_SUBQUERIES extends EntityManagerTest {


    @Test
    public void pesquisarSubqueries() { // O produto ou os produtos mais caros da base
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p "
//          + " FROM Produto p "
//          + " WHERE p.preco = ( "
//          + "     SELECT MAX(preco) FROM Produto p2 " // subquerie só pode retornar um registro senão dá Exception
//          + " ) ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); // Query vai retornar Produto

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Produto p

        criteriaQuery.select(root); // SELECT p


//      ---
        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class); // SubQuery vai retornar BigDecimal
        Root<Produto> subRoot = subquery.from(Produto.class);                 //  FROM Produto p2

        subquery.select(criteriaBuilder.max(subRoot.get(Produto_.preco))); //  SELECT MAX(preco) FROM Produto p2
//      ---

        criteriaQuery.where( // WHERE p.preco = (SubQuery)
            criteriaBuilder.equal(root.get(Produto_.preco), subquery));


        TypedQuery<Produto> typedQuery =
//          entityManager.createQuery(jpql, Produto.class)
            entityManager.createQuery(criteriaQuery);


        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId() + ", Preco: " + c.getPreco()));
    }


    @Test
    public void pesquisarSubqueries2() {  // Todos os pedidos acima da média de vendas
//      Convertendo uma JPQL em Criteria Query
        String jpql = "SELECT p "
            + " FROM Pedido p "
            + " WHERE p.total > ( "
            + "     SELECT AVG(total) FROM Pedido p2 " // subquerie só pode retornar um registro senão dá Exception
            + " ) ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Produto

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        criteriaQuery.select(root); // SELECT p

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class); // SubQuery vai retornar BigDecimal
        Root<Pedido> subRoot = subquery.from(Pedido.class);                  //   //  FROM Pedidos p2

        subquery.select(criteriaBuilder.avg(subRoot.get(Pedido_.total)).as(BigDecimal.class)); //  SELECT AVG(total) FROM Pedido p2


        criteriaQuery.where( // WHERE p.total > (SubQuery)
            criteriaBuilder.greaterThan(root.get(Pedido_.total), subquery));


        TypedQuery<Pedido> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
            entityManager.createQuery(criteriaQuery);


        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId() + ", Total: " + c.getTotal()));
    }


    @Test
    public void pesquisarSubqueries3() {  // Bons clientes(são clientes que gastam acima de R$ 2000.00), versão 1
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT c "
//          + " FROM Cliente c "
//          + " WHERE ( "
//          + "     SELECT SUM(p.total) FROM c.pedidos p " // subquerie só pode retornar um registro senão dá Exception
//          + " ) > 2000 ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class); // Query vai retornar Cliente

        Root<Cliente> root = criteriaQuery.from(Cliente.class); // FROM Cliente p

        criteriaQuery.select(root); // SELECT p

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class); // SubQuery vai retornar BigDecimal
        Root<Pedido> subRoot = subquery.from(Pedido.class);                  //  FROM Pedidos p

        subquery.select(criteriaBuilder.sum(subRoot.get(Pedido_.total)).as(BigDecimal.class)); // SELECT SUM(p.total)
        subquery.where(criteriaBuilder.equal(root, subRoot.get(Pedido_.cliente)));                // FROM c.pedidos p

        criteriaQuery.where( // WHERE (SubQuery) > 2000
            criteriaBuilder.greaterThan( subquery, new BigDecimal("2000")));


        TypedQuery<Cliente> typedQuery =
//          entityManager.createQuery(jpql, Cliente.class)
            entityManager.createQuery(criteriaQuery);


        List<Cliente> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId() + ", Nome: " + c.getNome()));
    }

}
