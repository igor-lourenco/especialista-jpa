package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class _2_TypedQuery_vs_Query extends EntityManagerTest {

    @Test
    public void buscaIdentificador() {
//      ----------- JPQL Equivalente a busca usando EntityManager -----------------------------
//      entityManager.find(Pedido.class, 1)

        String jpql = "select p from Pedido p where p.id = 1";

//      TypedQuery<T>: consulta tipada, com segurança de tipos em tempo de compilação: List<T>, T, Stream<T>
//      Query: consulta genérica (sem tipo), resultados vem como: Object, List ou List<Object[]> e precisa fazer cast

        logger.info("Buscando Pedido usando TypedQuery...");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        Pedido pedidoTypedQuery = typedQuery.getSingleResult(); // senão retornar um registro apenas, solta exception
        Assert.assertNotNull(pedidoTypedQuery);


        logger.info("Buscando Pedido usando Query...");
        Query query = entityManager.createQuery(jpql);
        Pedido pedidoQuery = (Pedido) query.getSingleResult();
        Assert.assertNotNull(pedidoQuery);


        logger.info("Buscando Lista de Pedidos usando Query...");
        List<Pedido> lista = query.getResultList();
        Assert.assertFalse(lista.isEmpty());

    }
}
