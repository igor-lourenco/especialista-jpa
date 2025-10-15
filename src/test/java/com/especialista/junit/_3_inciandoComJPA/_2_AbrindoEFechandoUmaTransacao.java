package com.especialista.junit._3_inciandoComJPA;

import com.especialista.jpa._2_iniciandoComJPA.modelo.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class _2_AbrindoEFechandoUmaTransacao extends EntityManagerTest {


    @Test
    public void abrirEFecharATransacao() {
        Produto produto = new Produto(); // apenas para não dar erro no código

        entityManager.getTransaction().begin(); // Início da transação

        // operações que vão realizar algum tipo de mudança no banco de dados
        entityManager.persist(produto);
        entityManager.merge(produto);
        entityManager.remove(produto);

        entityManager.getTransaction().commit(); // Fim da transação
    }
}
