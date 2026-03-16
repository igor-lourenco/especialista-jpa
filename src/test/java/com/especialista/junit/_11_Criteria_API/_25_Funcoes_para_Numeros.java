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

public class _25_Funcoes_para_Numeros extends EntityManagerTest {


    @Test
    public void usando_FuncaoParaNumero3(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT ABS(p.total), MOD(p.total, 7), SQRT(p.total), p.total "
//          + " FROM Pedido p "
//          + " WHERE p.id = 1";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // SELECT p

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p


        criteriaQuery.multiselect(
            criteriaBuilder.abs(root.get(Pedido_.total)),
            criteriaBuilder.mod(root.get(Pedido_.TOTAL), 7), // Funciona, mas o recomendado é usar a função nativa do sql, usando o criteriaBuilder.function
            criteriaBuilder.sqrt(criteriaBuilder.prod(root.get(Pedido_.total), 1)),  // Funciona, mas o recomendado é usar a função nativa do sql, usando o criteriaBuilder.function
            root.get(Pedido_.total)
        );


        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1)); // WHERE p.id = 1


        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery)
                .setMaxResults(1);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("RETORNA VALOR ABSOLUTO ABS(" + c[3] + "): " + c[0]));
        lista.forEach(c -> logger.info("RETORNA A SOBRA DA DIVISÃO DE DOIS NÚMEROS MOD(" + c[3] + ", 7):  " + c[1]));
        lista.forEach(c -> logger.info("RETORNA A RAIZ QUADRADA DO NUMERO SQRT(" + c[3] + "): " + c[2]));
    }

    @Test
    public void usando_FuncaoParaNumero1(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT ABS(-10), MOD(3, 2), SQRT(9) "
//          + " FROM Pedido p "
//          + " WHERE p.id = 1";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // SELECT p

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p


        criteriaQuery.multiselect(
            criteriaBuilder.abs(criteriaBuilder.prod(root.get(Pedido_.id), -10)), //  1 * -10
            criteriaBuilder.mod( criteriaBuilder.prod(root.get(Pedido_.id), 3), 2), //  3 / 2
            criteriaBuilder.sqrt(criteriaBuilder.prod(root.get(Pedido_.id), 9)) //  1 * 9
        );

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1)); // WHERE p.id = 1


        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery)
                .setMaxResults(1);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("RETORNA VALOR ABSOLUTO ABS(-10): " + c[0]));
        lista.forEach(c -> logger.info("RETORNA A SOBRA DA DIVISÃO DE DOIS NÚMEROS MOD(3, 2):  " + c[1]));
        lista.forEach(c -> logger.info("RETORNA A RAIZ QUADRADA DO NUMERO SQRT(9): " + c[2]));
    }



}
