package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _34_Usando_DISTINCT extends EntityManagerTest {


    @Test
    public void semUsar_DISTINCT() { //

        String jpql = "SELECT p"
            + " FROM Pedido p "
            + " JOIN FETCH p.itensPedido item "
            + " JOIN item.produto prod "
            + " WHERE prod.id IN (1, 2, 3) ";


        logger.info("Buscando Pedido...");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado sem usar DISTINCT: " + lista.size());
        lista.forEach(c -> logger.info(c.toString()));
    }

    @Test
    public void usando_DISTINCT() { //

        String jpql = "SELECT DISTINCT p"
            + " FROM Pedido p "
            + " JOIN FETCH p.itensPedido item "
            + " JOIN item.produto prod "
            + " WHERE prod.id IN (1, 2, 3) ";


        logger.info("Buscando Pedido...");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado usando DISTINCT: " + lista.size());
        lista.forEach(c -> logger.info(c.toString()));
    }


}
