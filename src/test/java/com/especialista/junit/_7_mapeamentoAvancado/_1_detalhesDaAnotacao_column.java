package com.especialista.junit._7_mapeamentoAvancado;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class _1_detalhesDaAnotacao_column extends EntityManagerTest {

/*
   updatable = false -> impede que o dado da coluna seja atualizada depois de criado

   insertable = false -> impede que a coluna seja salva com algum dado, ou seja, seja salvo como null
*/


    @Test
    public void impedirInsercaoNaColuna_data_Atualizacao(){
        System.out.println("\n>>> 1. instanciando novo produto...");

        Produto produtoNovo = new Produto();
        produtoNovo.setNome("Teclado para Smartpone");
        produtoNovo.setDescricao("O mais confortável");
        produtoNovo.setPreco(BigDecimal.ONE);
        produtoNovo.setDataCriacao(LocalDateTime.now());
        produtoNovo.setDataUltimaAtualizacao(LocalDateTime.now()); // não deve salvar no banco de dados, ou seja, salvar com null


        System.out.println("\n>>> 2. Iniciando uma transação...");
        entityManager.getTransaction().begin();// Início da transação

        System.out.println("\n>>> 3. Colocando a nova entidade no contexto de persistência usando o persist()...");
        entityManager.persist(produtoNovo);

        System.out.println("\n>>> 4. JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();// Fim da transação (confirma a transação)

        System.out.println("\n>>> 5. Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        System.out.println("\n>>> 6. Buscando produto criado no banco de dados...");
        Produto produto = entityManager.find(Produto.class, produtoNovo.getId());
        Assert.assertNotNull(produto.getDataCriacao());
        Assert.assertNull(produto.getDataUltimaAtualizacao());

    }


    @Test
    public void impedirAtualizacaoNaColuna_data_criacao(){
        System.out.println("\n>>> 1. Buscando produto no banco de dados...");

        Produto produtoNovo = entityManager.find(Produto.class, 1);
        produtoNovo.setPreco(BigDecimal.TEN);
        produtoNovo.setDataCriacao(LocalDateTime.now()); // não deve atualizar no banco de dados
        produtoNovo.setDataUltimaAtualizacao(LocalDateTime.now());


        System.out.println("\n>>> 2. Iniciando uma transação...");
        entityManager.getTransaction().begin();// Início da transação

//      entityManager.merge(produtoNovo); // não precisa porque a entidade já está no contexto de persistência

        System.out.println("\n>>> 3. JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();// Fim da transação (confirma a transação)

        System.out.println("\n>>> 4. Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        System.out.println("\n>>> 5. Buscando produto criado no banco de dados...");
        Produto produto = entityManager.find(Produto.class, produtoNovo.getId());

        Assert.assertNotEquals(produtoNovo.getDataCriacao().truncatedTo(ChronoUnit.SECONDS),
            produto.getDataCriacao().truncatedTo(ChronoUnit.SECONDS));

        Assert.assertEquals(produtoNovo.getDataUltimaAtualizacao().truncatedTo(ChronoUnit.SECONDS),
            produto.getDataUltimaAtualizacao().truncatedTo(ChronoUnit.SECONDS));
    }
}
