package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

public class _20_Usando_Usando_Operador_Logico_AND_e_OR extends EntityManagerTest {



    @Test
    public void usandoExpressaoCondicional_AND_e_OR(){// Busca todos os Pedidos com total MAIOR que 400.00 E (status=AGUARDANDO OU status=PAGO) E DIFERENTE de clienteId 1
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Pedido p "
//          + " WHERE p.total > :total "
//          + " AND (p.status = :status1 OR p.status = :status2) "
//          + " AND p.cliente.id <> :clienteId";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // SELECT p

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).

        criteriaQuery.where(
            criteriaBuilder.greaterThan(root.get(Pedido_.total), new BigDecimal("400.00")), //  WHERE p.total > :total
            criteriaBuilder.or(
                criteriaBuilder.equal(root.get(Pedido_.status), StatusPedido.AGUARDANDO), // AND p.status = :status1
                criteriaBuilder.equal(root.get(Pedido_.status), StatusPedido.PAGO) // OR p.status = :status2
            ),
            criteriaBuilder.notEqual(root.get(Pedido_.cliente).get(Cliente_.id), 1) // AND p.cliente.id <> :clienteId
        );

        TypedQuery<Pedido> typedQuery =
//            entityManager.createQuery(jpql, Pedido.class)
//            .setParameter("total", total)
//            .setParameter("status1", status1)
//            .setParameter("status2", status2)
//            .setParameter("clienteId", clienteId);
            entityManager.createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(p -> logger.info("Id:" + p.getId()
            + ", total: " + p.getTotal()
            + ", status: " + p.getStatus()
            + ", clienteId: " + p.getCliente().getId()));

    }
    @Test
    public void usandoExpressaoCondicional_AND(){// Busca todos os Pedidos com total MAIOR que 400.00 E status=AGUARDANDO E clienteId=2
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Pedido p "
//          + " WHERE p.total > :total "
//          + " AND p.status = :status "
//          + " AND p.cliente.id = :clienteId";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // SELECT p

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).

        criteriaQuery.where(criteriaBuilder.and(
            criteriaBuilder.greaterThan(root.get(Pedido_.total), new BigDecimal("400.00")), //  WHERE p.total > :total
            criteriaBuilder.equal(root.get(Pedido_.status), StatusPedido.AGUARDANDO), // AND p.status = :status
            criteriaBuilder.equal(root.get(Pedido_.cliente).get(Cliente_.id), 2) // AND p.cliente.id = :clienteId
        ));

        TypedQuery<Pedido> typedQuery =
//            entityManager.createQuery(jpql, Pedido.class)
//            .setParameter("total", total)
//            .setParameter("status", status)
//            .setParameter("clienteId", clienteId);
            entityManager.createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(p -> logger.info("Id:" + p.getId()
            + ", total: " + p.getTotal()
            + ", status: " + p.getStatus()
            + ", clienteId: " + p.getCliente().getId()));

    }

    @Test
    public void usandoExpressaoCondicional_OR(){// Busca todos os Pedidos com total MAIOR que 400.00 E status=AGUARDANDO OU status=PAGO
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Pedido p "
//          + " WHERE p.total > :total "
//          + " AND p.status = :status1 "
//          + " OR p.status = :status2";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // SELECT p

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Pedido_.total), new BigDecimal("400.00")), //  WHERE p.total > :total
            criteriaBuilder.or(
            criteriaBuilder.equal(root.get(Pedido_.status), StatusPedido.AGUARDANDO), // AND p.status = :status
            criteriaBuilder.equal(root.get(Pedido_.status), StatusPedido.PAGO) // OR p.status = :status2
        ));

        TypedQuery<Pedido> typedQuery =
//            entityManager.createQuery(jpql, Pedido.class)
//            .setParameter("total", total)
//            .setParameter("status1", status1)
//            .setParameter("status2", status2);
            entityManager.createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(p -> logger.info("Id:" + p.getId()
            + ", total: " + p.getTotal()
            + ", status: " + p.getStatus()
            + ", clienteId: " + p.getCliente().getId()));

    }

}
