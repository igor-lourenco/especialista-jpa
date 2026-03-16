package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class _1_Introducao_a_Criteria_API extends EntityManagerTest {

    @Test
    public void buscarPorIdentificador(){

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Pedido p WHERE p.id = 1";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).
        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1)); // WHERE p.id = 1


        TypedQuery<Pedido> typedQuery = entityManager
//            .createQuery(jpql, Pedido.class);
            .createQuery(criteriaQuery);

        List<Pedido> pedidos = typedQuery.getResultList();

        Assert.assertNotNull(pedidos.get(0));
        Assert.assertEquals(1, pedidos.get(0).getId().intValue());
    }
}
