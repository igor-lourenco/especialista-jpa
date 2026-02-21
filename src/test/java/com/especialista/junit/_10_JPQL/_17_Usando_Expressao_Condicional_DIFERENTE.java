package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _17_Usando_Expressao_Condicional_DIFERENTE extends EntityManagerTest {

    @Test
    public void usandoExpressaoCondicional_DIFERENTE() { // Todos parâmetros criados no JPQL tem que ser especificado senão JPA solta EXCEPTION
        String jpql = "SELECT c FROM Cliente c "
            + " WHERE c.nome <> :nome"; // O diferente é representado pelo sinal <>

        String nome = "Marcos Mariano";

        logger.info("Buscando Cliente ...");
        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class)
            .setParameter("nome", nome);

        List<Cliente> clientes = typedQuery.getResultList();

        Assert.assertFalse(clientes.isEmpty());

        logger.info("Buscando todos os Clientes que são diferentes de: " + nome);
        clientes.forEach(c -> logger.info("Nome" + " - " + c.getNome()));
    }

}
