package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _12_Usando_Expressao_Condicional_LIKE extends EntityManagerTest {

    @Test
    public void usandoExpressaoCondicional_LIKE() { // Todos parâmetros criados no JPQL tem que ser especificado senão JPA solta EXCEPTION
        String jpql = "SELECT c FROM Cliente c "
            + " WHERE c.nome LIKE :nome";

        String nome = "f";

        logger.info("Buscando Cliente ...");
        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class)
            .setParameter("nome", "%" + nome + "%");

        List<Cliente> clientes = typedQuery.getResultList();

        Assert.assertFalse(clientes.isEmpty());

        logger.info("Clientes retornados...");
        clientes.forEach(c -> logger.info("Nome" + " - " + c.getNome()));
    }


    @Test
    public void usandoExpressaoCondicional_LIKE_e_CONCAT() { // Todos parâmetros criados no JPQL tem que ser especificado senão JPA solta EXCEPTION
        String jpql = "SELECT c FROM Cliente c "
            + " WHERE c.nome LIKE CONCAT('%', :nome, '%')";

        String nome = "f";

        logger.info("Buscando Cliente ...");
        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class)
            .setParameter("nome", nome);

        List<Cliente> clientes = typedQuery.getResultList();

        Assert.assertFalse(clientes.isEmpty());

        logger.info("Clientes retornados...");
        clientes.forEach(c -> logger.info("Nome" + " - " + c.getNome()));
    }
}
