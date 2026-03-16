package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class _5_Usando_Tuple_para_projecoes extends EntityManagerTest {

/*
        - Tuple é usada para representar resultados parciais de consultas, especialmente quando
        não retorna entidades inteiras, mas sim colunas/expressões específicas (projeções).
        Como se fosse uma “linha” com vários campos nomeados e tipados, mais legível e segura do que usar Object[].
*/
    @Test
    public void projetarOResultado(){

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p.id, p.nome FROM Produto p";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery(); // Query vai retornar Tuple

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Produto p

//        criteriaQuery.multiselect(root.get("id"), root.get("nome")); // atributos p.id, p.nome
        criteriaQuery.select(criteriaBuilder.tuple(root.get("id").alias("id"), // O mesmo que acima mas usando o Tuple
            root.get("nome").alias("nome")
        ));


        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Tuple> lista = typedQuery.getResultList();

        Assert.assertNotNull(lista);

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> logger.info("ID: " + a.get("id") + ", Nome: " + a.get("nome")));
    }

    @Test
    public void projetarOResultado2(){

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p.id, p.nome FROM Produto p";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery(); // Query vai retornar Tuple

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Produto p

//        criteriaQuery.multiselect(root.get("id"), root.get("nome")); // atributos p.id, p.nome
        criteriaQuery.select(criteriaBuilder.tuple(root.get("id"), root.get("nome"))); // O mesmo que acima mas usando o Tuple


        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Tuple> lista = typedQuery.getResultList();

        Assert.assertNotNull(lista);

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> logger.info("ID: " + a.get(0) + ", Nome: " + a.get(1)));
    }

}
