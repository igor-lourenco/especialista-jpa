package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.StatusPedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

public class _18_Usand_Operador_Logico_AND_e_OR extends EntityManagerTest {

    @Test // Todos parâmetros criados no JPQL tem que ser especificado senão JPA solta EXCEPTION
    public void usandoExpressaoCondicional_AND_e_OR() {

        // "Buscando todos os Pedidos com total MAIOR que 400.00 E (status=AGUARDANDO OU status=PAGO) E DIFERENTE de clienteId 1
        String jpql = "SELECT p FROM Pedido p "
            + " WHERE p.total > :total "
            + " AND (p.status = :status1 OR p.status = :status2) "
            + " AND p.cliente.id <> :clienteId";

        BigDecimal total = new BigDecimal("400.00");
        StatusPedido status1 = StatusPedido.AGUARDANDO;
        StatusPedido status2 = StatusPedido.PAGO;
        Integer clienteId = 1;

        logger.info("Buscando Cliente ...");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class)
            .setParameter("total", total)
            .setParameter("status1", status1)
            .setParameter("status2", status2)
            .setParameter("clienteId", clienteId);

        List<Pedido> pedidos = typedQuery.getResultList();

        Assert.assertFalse(pedidos.isEmpty());

        logger.info("Buscando todos os Pedidos com total MAIOR que " + total + " E (status=" + status1 + " OU status=" + status2 + ") E DIFERENTE de clienteId=" + clienteId);
        pedidos.forEach(p -> logger.info("Id:" + p.getId()
            + ", total: " + p.getTotal()
            + ", status: " + p.getStatus()
            + ", clienteId: " + p.getCliente().getId()));
    }

    @Test // Todos parâmetros criados no JPQL tem que ser especificado senão JPA solta EXCEPTION
    public void usandoExpressaoCondicional_AND() {

//      Busca todos os Pedidos com total MAIOR que 400.00 E status=AGUARDANDO E clienteId=2
        String jpql = "SELECT p FROM Pedido p "
            + " WHERE p.total > :total "
            + " AND p.status = :status "
            + " AND p.cliente.id = :clienteId";

        BigDecimal total = new BigDecimal("400.00");
        StatusPedido status = StatusPedido.AGUARDANDO;
        Integer clienteId = 2;

        logger.info("Buscando Cliente ...");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class)
            .setParameter("total", total)
            .setParameter("status", status)
            .setParameter("clienteId", clienteId);

        List<Pedido> pedidos = typedQuery.getResultList();

        Assert.assertFalse(pedidos.isEmpty());

        logger.info("Buscando todos os Pedidos com total MAIOR que " + total + " E status=" + status + " E clienteId= " + clienteId);
        pedidos.forEach(p -> logger.info("Id:" + p.getId()
                + ", total: " + p.getTotal()
                + ", status: " + p.getStatus()
                + ", clienteId: " + p.getCliente().getId()));
    }

    @Test // Todos parâmetros criados no JPQL tem que ser especificado senão JPA solta EXCEPTION
    public void usandoExpressaoCondicional_OR() {

//      Busca todos os Pedidos com total MAIOR que 400.00 E status=AGUARDANDO OU status=PAGO
        String jpql = "SELECT p FROM Pedido p "
            + " WHERE p.total > :total "
            + " AND p.status = :status1 "
            + " OR p.status = :status2";

        BigDecimal total = new BigDecimal("400.00");
        StatusPedido status1 = StatusPedido.AGUARDANDO;
        StatusPedido status2 = StatusPedido.PAGO;

        logger.info("Buscando Cliente ...");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class)
            .setParameter("total", total)
            .setParameter("status1", status1)
            .setParameter("status2", status2);

        List<Pedido> pedidos = typedQuery.getResultList();

        Assert.assertFalse(pedidos.isEmpty());

        logger.info("Buscando todos os Pedidos com total MAIOR que " + total + " E status=" + status1 + "OU status= " + status2);
        pedidos.forEach(p -> logger.info("Id:" + p.getId()
                + ", total: " + p.getTotal()
                + ", status: " + p.getStatus()
                + ", clienteId: " + p.getCliente().getId()));
    }

}
