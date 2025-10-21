package com.especialista.junit._3_inciandoComJPA;

import com.especialista.jpa._2_iniciandoComJPA.modelo.ProdutoIniciandoComJPA;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.*;

public class _2_AbrindoEFechandoUmaTransacao extends EntityManagerTest {


    @Test
    public void abrirEFecharATransacao() {
        ProdutoIniciandoComJPA produto = new ProdutoIniciandoComJPA(); // apenas para não dar erro no código

        entityManager.getTransaction().begin(); // Início da transação

        // operações que vão realizar algum tipo de mudança no banco de dados
        entityManager.persist(produto);
        entityManager.merge(produto);
        entityManager.remove(produto);

        entityManager.getTransaction().commit(); // Fim da transação
    }
}
