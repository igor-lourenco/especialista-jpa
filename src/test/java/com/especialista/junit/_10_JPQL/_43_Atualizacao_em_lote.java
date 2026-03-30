package com.especialista.junit._10_JPQL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

import javax.persistence.Query;

public class _43_Atualizacao_em_lote extends EntityManagerTest {


    @Test
    public void atualizarEmLote() {


        String jpql = "UPDATE ProdutoIniciandoComJPA p1 "
            + " SET p1.preco = 1000 "
            + " WHERE LENGTH(p1.nome) > 27 "
            + " AND p1.id BETWEEN 100 AND 110";


        entityManager.getTransaction().begin(); // Iniciando uma transação...

        Query query = entityManager.createQuery(jpql);
        query.executeUpdate();


        entityManager.getTransaction().commit(); // JPA confirmando a transação, salvando as alterações no banco de dados...

    }
}
