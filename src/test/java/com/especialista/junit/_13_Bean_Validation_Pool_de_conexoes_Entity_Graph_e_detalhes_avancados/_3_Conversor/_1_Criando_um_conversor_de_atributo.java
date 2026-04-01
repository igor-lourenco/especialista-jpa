package com.especialista.junit._13_Bean_Validation_Pool_de_conexoes_Entity_Graph_e_detalhes_avancados._3_Conversor;

import com.especialista.jpa._2_iniciandoComJPA.modelo.ProdutoIniciandoComJPA;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class _1_Criando_um_conversor_de_atributo extends EntityManagerTest {

    @Test
    public void converter(){
        ProdutoIniciandoComJPA produto = new ProdutoIniciandoComJPA();
        produto.setId(200);
        produto.setNome("Carregador Notebook Dell");
        produto.setPreco(new BigDecimal("100.00"));
        produto.setDescricao("Modelo de carregador de carregamento rápido");
        produto.setAtivo(Boolean.TRUE);


        entityManager.getTransaction().begin();// Início da transação

        entityManager.persist(produto);

        entityManager.getTransaction().commit();// Fim da transação (confirma a transação)

        entityManager.clear(); // Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...

        ProdutoIniciandoComJPA produto2 = entityManager.find(ProdutoIniciandoComJPA.class, produto.getId());
        Assert.assertTrue(produto2.getAtivo());
    }
}
