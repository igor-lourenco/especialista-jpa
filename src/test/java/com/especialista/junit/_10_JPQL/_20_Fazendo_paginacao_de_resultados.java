package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _20_Fazendo_paginacao_de_resultados extends EntityManagerTest {

    @Test
    public void paginarResultados() {
        String jpql = "SELECT p FROM Pedido p "
            + " ORDER BY p.id ASC";

//      FIRST_RESULT = MAX_RESULTS * (página - 1);

        int maxResults = 2; // máximo de resultados por página
        int pagina = 3;
        int firstResults = maxResults * (pagina - 1);

        logger.info("Buscando Pedidos ...");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class)
            .setFirstResult(firstResults)
            .setMaxResults(maxResults);

        List<Pedido> clientes = typedQuery.getResultList();

        Assert.assertFalse(clientes.isEmpty());

        logger.info("Pedido paginados, PÁGINA: " + pagina);
        clientes.forEach(p -> logger.info("Nome" + " - " + p.getId()));
    }

}
