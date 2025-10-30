package com.especialista.junit._7_mapeamentoAvancado;

import com.especialista.jpa._6_mapeamentoAvancado.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class _2_exercicioAnotacao_column extends EntityManagerTest {

/*
   updatable = false -> impede que o dado da coluna seja atualizada depois de criado

   insertable = false -> impede que a coluna seja salva com algum dado, ou seja, seja salvo como null
*/


    @Test
    public void impedirAtualizacaoNaColuna_data_criacao(){
        System.out.println("\n>>> 1. Buscando pedido no banco de dados...");

        Pedido produtoNovo = entityManager.find(Pedido.class, 1);
        produtoNovo.setDataCriacao(LocalDateTime.now()); // não deve atualizar no banco de dados
        produtoNovo.setDataUltimaAtualizacao(LocalDateTime.now());


        System.out.println("\n>>> 2. Iniciando uma transação...");
        entityManager.getTransaction().begin();// Início da transação

//      entityManager.merge(pedidoNovo); // não precisa porque a entidade já está no contexto de persistência

        System.out.println("\n>>> 3. JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();// Fim da transação (confirma a transação)

        System.out.println("\n>>> 4. Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        System.out.println("\n>>> 5. Buscando pedido criado no banco de dados...");
        Pedido pedido = entityManager.find(Pedido.class, produtoNovo.getId());

        Assert.assertNotEquals(produtoNovo.getDataCriacao().truncatedTo(ChronoUnit.SECONDS),
            pedido.getDataCriacao().truncatedTo(ChronoUnit.SECONDS));

        Assert.assertEquals(produtoNovo.getDataUltimaAtualizacao().truncatedTo(ChronoUnit.SECONDS),
            pedido.getDataUltimaAtualizacao().truncatedTo(ChronoUnit.SECONDS));
    }
}
