package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _21_Limitando_quantidade_de_registros_retornados extends EntityManagerTest {

    @Test
    public void paginarResultados() {
        String jpql = "SELECT p FROM Pedido p "
            + " ORDER BY p.id ASC";


        logger.info("Buscando Pedidos ...");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class)
            .setMaxResults(10);

        List<Pedido> pedidos = typedQuery.getResultList();

        Assert.assertFalse(pedidos.isEmpty());

        logger.info("Pedido paginados, com limte máximo de 10");
        pedidos.forEach(p -> logger.info("Nome" + " - " + p.getId()));
    }

}
