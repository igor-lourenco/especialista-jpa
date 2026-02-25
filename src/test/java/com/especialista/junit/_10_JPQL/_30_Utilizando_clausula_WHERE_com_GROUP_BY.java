package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.StatusPedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _30_Utilizando_clausula_WHERE_com_GROUP_BY extends EntityManagerTest {


    @Test
    public void agrupandoResultados3() { // Agrupa total de vendas por mês apenas no ano corrente com status PAGO

        String jpql = "SELECT YEAR(p.dataCriacao), MONTH(p.dataCriacao), SUM(p.total) "
            + " FROM Pedido p "
            + " WHERE YEAR(p.dataCriacao) = YEAR(CURRENT_DATE) "
            + " AND p.status = :status "
            + " GROUP BY YEAR(p.dataCriacao), MONTH(p.dataCriacao) ";

        StatusPedido status = StatusPedido.PAGO;

        logger.info("Buscando total de vendas por mês apenas no ano corrente com status PAGO");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class)
            .setParameter("status", status);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info(c[0] + "/" + c[1] + " TOTAL: " + c[2]));
    }

    @Test
    public void agrupandoResultados4() {

        String jpql = "SELECT c.nome, SUM(item.precoProduto) " // Agrupa total de vendas por categoria no mês corrente
            + " FROM ItemPedido item "
            + " JOIN item.produto p "
            + " JOIN p.categorias c "
            + " JOIN item.pedido ped "
            + " WHERE MONTH(ped.dataCriacao) = MONTH(CURRENT_DATE)"
            + " GROUP BY c.id ";

        logger.info("Buscando total de vendas por categoria");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info("Categoria: " + c[0] + " - TOTAL: " + c[1]));
    }

    @Test
    public void agrupandoResultados5() {

        String jpql = "SELECT c.nome, SUM(p.total) " // Agrupa total de vendas por cliente nos últimos 3 meses
            + " FROM Pedido p "
            + " JOIN p.cliente c "
            + " WHERE YEAR(p.dataCriacao) = YEAR(CURRENT_DATE) "
            + " AND MONTH(p.dataCriacao) >= (MONTH(CURRENT_DATE) - 3) " // últimos 3 meses corrente
            + " GROUP BY c.id";

        logger.info("Buscando total de vendas por cliente");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info("Cliente: " + c[0] + " - TOTAL: " + c[1]));
    }
}
