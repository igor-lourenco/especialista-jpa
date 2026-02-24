package com.especialista.junit._10_JPQL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _29_Exercicio_Agrupando_com_GROUP_BY extends EntityManagerTest {


    @Test
    public void agrupandoResultados1() {

        String jpql = "SELECT c.nome, SUM(p.total) " // Agrupa total de vendas por cliente
            + " FROM Pedido p "
            + " JOIN p.cliente c "
            + " GROUP BY c.id";

        logger.info("Buscando total de vendas por cliente");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info("Cliente: " + c[0] + " - TOTAL: " + c[1]));
    }

    @Test
    public void agrupandoResultados2() {

        String jpql = "SELECT YEAR(ped.dataCriacao), MONTH(ped.dataCriacao), DAY(ped.dataCriacao), c.nome, SUM(item.precoProduto * item.quantidade) "
            + " FROM ItemPedido item "  // Agrupa total de vendas por dia e por categoria
            + " JOIN item.pedido ped "
            + " JOIN item.produto prod "
            + " JOIN prod.categorias c"
            + " GROUP BY YEAR(ped.dataCriacao), MONTH(ped.dataCriacao), DAY(ped.dataCriacao), c.id ";

        logger.info("Buscando total de vendas por mês");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info(c[0] + "/" + c[1] + "/" + c[2] + " - " + c[3] + " TOTAL: " + c[4]));
    }
}
