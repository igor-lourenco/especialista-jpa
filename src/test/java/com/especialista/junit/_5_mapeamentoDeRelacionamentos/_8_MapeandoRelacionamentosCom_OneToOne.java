package com.especialista.junit._5_mapeamentoDeRelacionamentos;

import com.especialista.jpa._4_mapeamentoDeRelacionamento.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class _8_MapeandoRelacionamentosCom_OneToOne extends EntityManagerTest {

    @Test
    public void verificarRelacionamento_OneToOne(){
        System.out.println("\n>>> 1. Buscando pedido no banco de dados...");
        Pedido pedido = entityManager.find(Pedido.class, 1);

        System.out.println("\n>>> 2. Instanciando pagamentoCartao...");
        PagamentoCartao pagamentoCartao = new PagamentoCartao();
        pagamentoCartao.setNumero("1234");
        pagamentoCartao.setStatus(StatusPagamento.PROCESSANDO);

        System.out.println("\n>>> 3. Fazendo o relacionamento entre pagamentoCartao(owner) e pedido(não owner)...");
        pagamentoCartao.setPedido(pedido);

        entityManager.getTransaction().begin(); // Início da transação

        System.out.println("\n>>> 4. Salvando pagamentoCartao no banco de dados...");
        entityManager.persist(pagamentoCartao);

        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

//      Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.
        entityManager.clear();

        System.out.println("\n>>> 5. Fazendo a consulta do pedido no banco de dados...");
        Pedido pedidoVerificado = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificado.getPagamento());
    }


}
