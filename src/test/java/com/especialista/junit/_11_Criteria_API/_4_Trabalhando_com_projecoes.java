package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class _4_Trabalhando_com_projecoes extends EntityManagerTest {

/*
        - Projeção no contexto do JPA é quando você não busca a entidade completa,
        e sim um recorte/forma específica dos dados: certos atributos (escalares),
        agregações (ex.: count, sum) ou um DTO com apenas o que você precisa.
*/
    @Test
    public void projetarOResultado(){

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p.id, p.nome FROM Produto p";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // SELECT p.id, p.nome

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Pedido p

        criteriaQuery.multiselect(root.get("id"), root.get("nome")); // atributos p.id, p.nome


        TypedQuery<Object[]> typedQuery = entityManager
//            .createQuery(jpql, Object[].class);
            .createQuery(criteriaQuery);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertNotNull(lista);

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> logger.info("ID: " + a[0] + ", Nome: " + a[1]));
    }

}
