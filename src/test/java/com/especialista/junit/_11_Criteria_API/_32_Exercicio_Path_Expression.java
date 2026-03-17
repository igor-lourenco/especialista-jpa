package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class _32_Exercicio_Path_Expression extends EntityManagerTest {


    @Test
    public void buscarPedidosComProdutoDeIDIgual_1() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        root.fetch(Pedido_.itensPedido);

        criteriaQuery.where(
            criteriaBuilder.equal(root.join(Pedido_.itensPedido).get(ItemPedido_.produto).get(Produto_.id), 1)
        );


        TypedQuery<Pedido> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
            entityManager.createQuery(criteriaQuery);


        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> {

                c.getItensPedido().stream()
                    .forEach(i ->
                        logger.info("pedidoId: " + c.getId() + ", produtoId: " + i.getProduto().getId())
                    );

            }
        );
    }


}
