package com.especialista.junit._10_JPQL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

import javax.persistence.Query;

public class _44_Remover_em_lote extends EntityManagerTest {


    @Test
    public void removerEmLote() {


        String jpql = "DELETE from ProdutoIniciandoComJPA p1 "
            + " WHERE p1.id BETWEEN 10 AND 15";


        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery(jpql);
        query.executeUpdate();


        entityManager.getTransaction().commit();

    }
}
