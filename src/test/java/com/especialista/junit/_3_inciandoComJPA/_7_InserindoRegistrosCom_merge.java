package com.especialista.junit._3_inciandoComJPA;

import com.especialista.jpa._2_iniciandoComJPA.modelo.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class _7_InserindoRegistrosCom_merge extends EntityManagerTest {


    @Test
    public void mostrandoDiferencaEntrePersistEMerge() {
        System.out.println("=========================================================");
        System.out.println("======================== persist ========================");
        System.out.println("=========================================================");

        System.out.println(">>> 1. Instanciando o produto...");

        Produto produtoPersist = new Produto(); // os atributos que não estiver preenchido o JPA irá salvar como null
        produtoPersist.setId(5);
        produtoPersist.setNome("Smartphone One Plus");
        produtoPersist.setDescricao("O processador mais rápido");
        produtoPersist.setPreco(new BigDecimal("2000"));

        entityManager.getTransaction().begin(); // Início da transação


        System.out.println(">>> 2. Fazendo a inserção do produto no banco de dados e coloca o objeto na " +
            "memória para ser gerenciada pelo EntityManager...");
        entityManager.persist(produtoPersist); // Coloca o objeto no estado "managed" a partir desse momento, o EntityManager começa a gerenciar essa instância.


        System.out.println(">>> 3. Fazendo a atualização do produto no banco de dados na mesma transação...");
        produtoPersist.setNome("Smartphone Two Plus");


        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

//      Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.
        entityManager.clear();

        System.out.println(">>> 4. Fazendo a consulta no banco de dados para verificar se o produto foi atualizado...");
        Produto produtoVerificadoPersist = entityManager.find(Produto.class, produtoPersist.getId());
        Assert.assertNotNull(produtoVerificadoPersist);
        Assert.assertEquals("Smartphone Two Plus", produtoVerificadoPersist.getNome());


        System.out.println("=========================================================");
        System.out.println("======================== merge ========================");
        System.out.println("=========================================================");


        System.out.println(">>> 5. Instanciando o produto...");

        Produto produtoMerge = new Produto(); // os atributos que não estiver preenchido o JPA irá salvar como null
        produtoMerge.setId(6);
        produtoMerge.setNome("Notebook Dell");
        produtoMerge.setDescricao("O melhor da categoria");
        produtoMerge.setPreco(new BigDecimal("2000"));

        entityManager.getTransaction().begin(); // Início da transação


        System.out.println(">>> 6. Fazendo a inserção do produto no banco de dados e no retorno cria uma cópia do objeto" +
            " e coloca na memória para ser gerenciada pelo EntityManager...");
        produtoMerge = entityManager.merge(produtoMerge);

        System.out.println(">>> 7. Fazendo a atualização da cópia do objeto produto que foi retornado pelo merge...");
//      Não vai atualizar porque o EntityManager está gerenciando a cópia desse objeto que foi retornado pelo merge
        produtoMerge.setNome("Smartphone Two Plus");


        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

//      Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.
        entityManager.clear();

        System.out.println(">>> 8. Fazendo a consulta no banco de dados para verificar se o produto foi atualizado...");
        Produto produtoVerificadoMerge = entityManager.find(Produto.class, produtoMerge.getId());
        Assert.assertNotNull(produtoVerificadoMerge);
        Assert.assertEquals("Smartphone Two Plus", produtoVerificadoMerge.getNome());
    }
}
