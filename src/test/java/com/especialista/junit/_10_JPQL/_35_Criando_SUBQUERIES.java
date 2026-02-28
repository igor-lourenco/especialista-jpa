package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _35_Criando_SUBQUERIES extends EntityManagerTest {


    @Test
    public void pesquisarSubqueries() { // O produto ou os produtos mais caros da base

        String jpql = "SELECT p "
            + " FROM Produto p "
            + " WHERE p.preco = ( "
            + "     SELECT MAX(preco) FROM Produto p2 " // subquerie só pode retornar um registro senão dá Exception
            + " ) ";

        logger.info("Buscando produto ou os produtos mais caros da base...");
        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId() + ", Preco: " + c.getPreco()));
    }

    @Test
    public void pesquisarSubqueries2() { // Todos os pedidos acima da média de vendas

        String jpql = "SELECT p "
            + " FROM Pedido p "
            + " WHERE p.total > ( "
            + "     SELECT AVG(total) FROM Pedido p2 " // subquerie só pode retornar um registro senão dá Exception
            + " ) ";

        logger.info("Buscando todos os pedidos acima da média de vendas...");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId() + ", Total: " + c.getTotal()));
    }

    @Test
    public void pesquisarSubqueries3() { // Bons clientes(são clientes que gastam acima de R$ 2000.00), versão 1

        String jpql = "SELECT c "
            + " FROM Cliente c "
            + " WHERE 2000 < ( "
            + "     SELECT SUM(p.total) FROM c.pedidos p " // subquerie só pode retornar um registro senão dá Exception
            + " ) ";

        logger.info("Buscando bons clientes(são clientes que gastam acima de R$ 2000.00)...");
        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId() + ", Nome: " + c.getNome()));
    }

    @Test
    public void pesquisarSubqueries4() { // Bons clientes(são clientes que gastam acima de R$ 2000.00), versão 2

        String jpql = "SELECT c "
            + " FROM Cliente c "
            + " WHERE 2000 < ( "
            + "     SELECT SUM(p.total) FROM Pedido p " // subquerie só pode retornar um registro senão dá Exception
            + "       WHERE p.cliente = c"
            + " ) ";

        logger.info("Buscando bons clientes(são clientes que gastam acima de R$ 2000.00)...");
        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId() + ", Nome: " + c.getNome()));
    }

}
