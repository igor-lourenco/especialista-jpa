package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;

public class _34_Usando_HAVING_para_condicionar_o_agrupamento extends EntityManagerTest {

/*  HAVING → Vem depois do GROUP BY e serve para filtrar grupos após a agregação (como COUNT, SUM, AVG, MIN, MAX), ou colunas utilizadas pelo GROUP BY

    - Diferença entre WHERE e HAVING:
        → WHERE: filtra linhas antes da agregação (mais eficiente quando possível)

        → HAVING: filtra grupos após a agregação (necessário quando a condição usa agregados, ex.: COUNT(p) > 5).

    - Em JPQL, agregações não podem aparecer no WHERE, o certo é utilizar o HAVING para isso.

*/
    @Test
    public void agrupandoResultados3() {  // Agrupa total de vendas por categoria que vendem acima de 5000

//      Convertendo uma JPQL em Criteria Query
//        String jpql = "SELECT c.nome, SUM(item.precoProduto * item.quantidade) "
//            + " FROM ItemPedido item "
//            + " JOIN item.produto prod "
//            + " JOIN prod.categorias c"
//            + " GROUP BY c.id"
//            + " HAVING SUM(item.precoProduto * item.quantidade) > 5000 ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // Query vai retornar Object[]

        Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class); // FROM ItemPedido item

        Join<ItemPedido, Produto> joinProduto = root.join(ItemPedido_.produto);     // JOIN item.produto prod
        Join<Produto, Categoria> joinCategoria = joinProduto.join(Produto_.categorias); // JOIN prod.categorias c

        Expression<? extends Number> sum = criteriaBuilder.sum( //  SUM(item.precoProduto * item.quantidade)
            criteriaBuilder.prod(root.get(ItemPedido_.precoProduto), root.get(ItemPedido_.quantidade))
        );

        criteriaQuery.multiselect(                          // SELECT
            joinCategoria.get(Categoria_.nome), //  c.nome
            sum                                            //  SUM(item.precoProduto * item.quantidade)
        );


        criteriaQuery.groupBy(joinCategoria.get(Categoria_.id)); // GROUP BY c.id

        criteriaQuery.having( //  HAVING SUM(item.precoProduto * item.quantidade) > 5000
            criteriaBuilder.greaterThan(sum.as(BigDecimal.class),  new BigDecimal("5000"))
        );

        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
            entityManager.createQuery(criteriaQuery);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(c[0] + " - TOTAL: " + c[1]));
    }

}
