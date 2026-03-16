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

public class _3_Selecionando_todos extends EntityManagerTest {

    @Test
    public void selecionandoUmAtributoComoRetorno1(){

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Pedido p";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).
        criteriaQuery.select(root);


        TypedQuery<Pedido> typedQuery = entityManager
//            .createQuery(jpql, Pedido.class);
            .createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertNotNull(lista);

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> logger.info(a.toString()));
    }

}
