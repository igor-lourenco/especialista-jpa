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

public class _27_Funcoes_para_Nativas extends EntityManagerTest {


    @Test
    public void usandoFuncoesNativas1(){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // Query vai retornar Object[]

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        criteriaQuery.multiselect(
            root.get(Pedido_.id),
            root.get(Pedido_.dataCriacao),
            criteriaBuilder.function(
                "DAYNAME",                     // nome da função
                String.class,                        // tipo de retorno
                root.get(Pedido_.dataCriacao) // parâmetros
            )
        );

        criteriaQuery.where(criteriaBuilder.isTrue(
            criteriaBuilder.function(
                "acima_media_faturamento",  // nome da função
                Boolean.class,                     // tipo de retorno
                root.get(Pedido_.total)     // parâmetros
            )
        ));

        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(
              "ID: " + c[0]
            + ", DATA CRIAÇÃO: " + c[1]
            + ", DIA DA SEMANA: " + c[2]));
    }



}
