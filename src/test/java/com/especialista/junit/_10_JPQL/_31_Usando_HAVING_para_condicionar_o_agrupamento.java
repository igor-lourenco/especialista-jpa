package com.especialista.junit._10_JPQL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _31_Usando_HAVING_para_condicionar_o_agrupamento extends EntityManagerTest {


/*  HAVING → Vem depois do GROUP BY e serve para filtrar grupos após a agregação (como COUNT, SUM, AVG, MIN, MAX), ou colunas utilizadas pelo GROUP BY

    - Diferença entre WHERE e HAVING:
        → WHERE: filtra linhas antes da agregação (mais eficiente quando possível)

        → HAVING: filtra grupos após a agregação (necessário quando a condição usa agregados, ex.: COUNT(p) > 5).

    - Em JPQL, agregações não podem aparecer no WHERE, o certo é utilizar o HAVING para isso.

*/

    @Test
    public void agrupandoResultados3() { // Agrupa total de vendas por categoria que vendem acima de 5000

        String jpql = "SELECT c.nome, SUM(item.precoProduto * item.quantidade) "
            + " FROM ItemPedido item "
            + " JOIN item.produto prod "
            + " JOIN prod.categorias c"
            + " GROUP BY c.id"
            + " HAVING SUM(item.precoProduto * item.quantidade) > 5000 ";


        logger.info("Agrupando total de vendas por Categoria");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info(c[0] + " - TOTAL: " + c[1]));
    }
}
