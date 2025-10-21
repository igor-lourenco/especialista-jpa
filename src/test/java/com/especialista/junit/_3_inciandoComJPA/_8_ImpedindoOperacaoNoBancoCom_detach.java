package com.especialista.junit._3_inciandoComJPA;

import com.especialista.jpa._2_iniciandoComJPA.modelo.ProdutoIniciandoComJPA;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

public class _8_ImpedindoOperacaoNoBancoCom_detach extends EntityManagerTest {


    @Test
    public void impedindoOperacaoComBancoDeDados() {

        System.out.println(">>> 1. Instanciando o produto...");
        ProdutoIniciandoComJPA produto = entityManager.find(ProdutoIniciandoComJPA.class, 1);


        entityManager.getTransaction().begin(); // Início da transação

//      Objeto já está sendo gerenciado pelo EntityManager
        produto.setNome("Kindle Paperwhite 2° Geração");

//      Remove a entidade fornecida do contexto de persistência, fazendo com que uma entidade gerenciada seja desanexada.
        System.out.println(">>> 2. Desanexando entidade...");
        entityManager.detach(produto);

        System.out.println(">>> 3. Fazendo a atualização do produto no banco de dados...");
        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

//      Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.
        entityManager.clear();

        System.out.println(">>> 4. Fazendo a consulta no banco de dados para verificar se o produto foi atualizado...");
        ProdutoIniciandoComJPA produtoVerificado = entityManager.find(ProdutoIniciandoComJPA.class, produto.getId());
        Assert.assertEquals("Kindle", produtoVerificado.getNome());
    }
}
