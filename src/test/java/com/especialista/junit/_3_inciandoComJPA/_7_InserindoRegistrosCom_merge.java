package com.especialista.junit._3_inciandoComJPA;

import com.especialista.jpa._2_iniciandoComJPA.modelo.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class _7_InserindoRegistrosCom_merge extends EntityManagerTest {


    @Test
    public void inserindoObjetoComMerge() {
        System.out.println(">>> 1. Instanciando o produto...");

        Produto produto = new Produto(); // os atributos que não estiver preenchido o JPA irá salvar como null
        produto.setId(4);
        produto.setNome("Microfone Rode Videmic");
        produto.setDescricao("Melhor qualidade de som");
        produto.setPreco(new BigDecimal("1000"));

        entityManager.getTransaction().begin(); // Início da transação

//      O método merge permite atualizar e também inserir
        entityManager.merge(produto);

        System.out.println(">>> 2. Fazendo a atualização do produto no banco de dados...");
        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

//      Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.
        entityManager.clear();

        System.out.println(">>> 3. Fazendo a consulta no banco de dados para verificar se o produto foi criado...");
        Produto produtoVerificado = entityManager.find(Produto.class, produto.getId());
        Assert.assertNotNull(produtoVerificado);
        Assert.assertEquals("Microfone Rode Videmic", produtoVerificado.getNome());
    }
}
