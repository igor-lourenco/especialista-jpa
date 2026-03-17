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

public class _28_Funcoes_de_agregacao extends EntityManagerTest {


    @Test
    public void usandoFuncoesNativas(){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // Query vai retornar Object[]

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        criteriaQuery.multiselect(
            criteriaBuilder.avg(root.get(Pedido_.total)), // Média de valor total de Pedidos
            criteriaBuilder.count(root.get(Pedido_.total)), // Total de pedidos
            criteriaBuilder.min(root.get(Pedido_.total)), // Pedido com menor valor
            criteriaBuilder.sum(root.get(Pedido_.total)), // Pedido com maior valor
            criteriaBuilder.max(root.get(Pedido_.total)) // Soma total de todos os pedidos

        );


        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("Média de valor total de Pedidos: " + c[0]));
        lista.forEach(c -> logger.info("Total de pedidos: " + c[1]));
        lista.forEach(c -> logger.info("Pedido com menor valor: " + c[2]));
        lista.forEach(c -> logger.info("Pedido com maior valor: " + c[3]));
        lista.forEach(c -> logger.info("Soma total de todos os pedidos: " + c[4]));
    }



}
