package com.especialista.junit._3_inciandoComJPA;

import com.especialista.jpa._2_iniciandoComJPA.modelo.ProdutoIniciandoComJPA;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class _5_AtualizandoBancoDeDadosCom_merge extends EntityManagerTest {


    @Test
    public void atualizandoObjeto() {
        System.out.println(">>> 1. Instanciando o produto...");

        ProdutoIniciandoComJPA produto = new ProdutoIniciandoComJPA(); // os atributos que não estiver preenchido o JPA irá salvar como null
        produto.setId(1);
        produto.setNome("Kindle Paperwhite");
        produto.setDescricao("Conheça o novo Kindle");
        produto.setPreco(new BigDecimal("599"));

        entityManager.getTransaction().begin(); // Início da transação

//      Faz consulta no banco para verificar se esse objeto existe ou não
//      E cria um novo objeto, copia os valores, retorna esse novo objeto e o adiciona na memória para ser gerenciada pelo EntityManager
        entityManager.merge(produto);

        System.out.println(">>> 2. Fazendo a atualização do produto no banco de dados...");
        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

//      Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.
        entityManager.clear();

        System.out.println(">>> 3. Fazendo a consulta no banco de dados para verificar se o produto foi atualizado...");
        ProdutoIniciandoComJPA produtoVerificado = entityManager.find(ProdutoIniciandoComJPA.class, 1);
        Assert.assertNotNull(produtoVerificado);
        Assert.assertEquals("Kindle Paperwhite", produtoVerificado.getNome());
    }
}
