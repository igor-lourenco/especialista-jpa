package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido_;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class _26_Funcoes_para_Colecoes extends EntityManagerTest {


    @Test
    public void usando_SIZE1(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT SIZE(p.itensPedido) "
//          + " FROM Pedido p ";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class); // Query vai retornar Integer

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p


        criteriaQuery.multiselect(
            criteriaBuilder.size(root.get(Pedido_.itensPedido)) // SELECT SIZE(p.itensPedido)
        );


        TypedQuery<Integer> typedQuery =
//          entityManager.createQuery(jpql, Integer.class)
            entityManager.createQuery(criteriaQuery)
                .setMaxResults(1);


        List<Integer> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("Quantidade total de itensPedido: " + c));
    }


    @Test
    public void usando_SIZE2(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT SIZE(p.itensPedido) "
//          + " FROM Pedido p "
//          + " WHERE SIZE(p.itensPedido) > 1 ";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class); // Query vai retornar Integer

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p


        criteriaQuery.multiselect(
            criteriaBuilder.size(root.get(Pedido_.itensPedido)) // SELECT SIZE(p.itensPedido)
        );


        criteriaQuery.where( // WHERE SIZE(p.itensPedido) > 1
            criteriaBuilder.greaterThan(criteriaBuilder.size(root.get(Pedido_.itensPedido)), 1)
        );


        TypedQuery<Integer> typedQuery =
//          entityManager.createQuery(jpql, Integer.class)
            entityManager.createQuery(criteriaQuery)
                .setMaxResults(1);


        List<Integer> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("Quantidade total de itensPedido maior que 1: " + c));
    }


}
