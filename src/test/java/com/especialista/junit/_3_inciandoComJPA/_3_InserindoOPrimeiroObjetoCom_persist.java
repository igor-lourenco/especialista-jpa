package com.especialista.junit._3_inciandoComJPA;

import com.especialista.jpa._2_iniciandoComJPA.modelo.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class _3_InserindoOPrimeiroObjetoCom_persist extends EntityManagerTest {


    @Test
    public void inserindoPrimeiroObjeto() {
        Produto novoProduto = new Produto();
        novoProduto.setId(2);
        novoProduto.setNome("Câmera Canon");
        novoProduto.setDescricao("A melhor definição para suas fotos");
        novoProduto.setPreco(new BigDecimal("5000"));

        entityManager.getTransaction().begin(); // Início da transação

//      Cria uma instância do objeto e coloca na memória para ser gerenciada e persistente
        entityManager.persist(novoProduto);

        System.out.println(">>> 1. Fazendo a inserção do novo produto no banco de dados...");
        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)


//      Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.
        entityManager.clear();

        System.out.println(">>> 2. Fazendo a consulta do produto no banco de dados...");
        Produto produtoCriado = entityManager.find(Produto.class, novoProduto.getId());
        Assert.assertNotNull(produtoCriado);
    }
}
