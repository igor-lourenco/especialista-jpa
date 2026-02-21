package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _19_Ordenando_resultado_com_ORDER_BY extends EntityManagerTest {

    @Test // Todos parâmetros criados no JPQL tem que ser especificado senão JPA solta EXCEPTION
    public void ordenandoResultadosCom_ORDER_BY_DESC() {
        String jpql = "SELECT c FROM Cliente c "
            + " ORDER BY c.nome DESC";

        logger.info("Buscando Cliente ordenado pelo nome...");
        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> clientes = typedQuery.getResultList();

        Assert.assertFalse(clientes.isEmpty());

        logger.info("Clientes retornados ordenado pelo nome...");
        clientes.forEach(c -> logger.info("Nome" + " - " + c.getNome()));
    }

    @Test // Todos parâmetros criados no JPQL tem que ser especificado senão JPA solta EXCEPTION
    public void ordenandoResultadosCom_ORDER_BY_ASC() {
        String jpql = "SELECT c FROM Cliente c "
            + " ORDER BY c.nome ASC";

        logger.info("Buscando Cliente ordenado pelo nome...");
        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> clientes = typedQuery.getResultList();

        Assert.assertFalse(clientes.isEmpty());

        logger.info("Clientes retornados ordenado pelo nome...");
        clientes.forEach(c -> logger.info("Nome" + " - " + c.getNome()));
    }


}
