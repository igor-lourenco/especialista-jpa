package com.especialista.junit._4_mapeamentoBasico;

import com.especialista.jpa._3_mapeamentoBasico.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class _2_MapeandoObjetosEmbutidosCom_embeddable extends EntityManagerTest {

    @Test
    public void analisarMapeandoObjetoEmbutido(){
        System.out.println(">>> 1. Instanciando o pedido...");

        Endereco enderecoEntrega = new Endereco();
        enderecoEntrega.setCep("12345-67");
        enderecoEntrega.setLogradouro("Rua das Laranjeiras");
        enderecoEntrega.setNumero("123");
        enderecoEntrega.setComplemento("2° andar");
        enderecoEntrega.setBairro("Centro");
        enderecoEntrega.setCidade("Uberlândia");
        enderecoEntrega.setEstado("MG");

        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(new BigDecimal("1000"));
        pedido.setEnderecoEntrega(enderecoEntrega);

        entityManager.getTransaction().begin(); // Início da transação

        entityManager.persist(pedido);

        System.out.println(">>> 2. Fazendo a inserção do novo pedido no banco de dados...");
        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

        entityManager.clear(); //Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.

        System.out.println(">>> 3. Fazendo a consulta do pedido no banco de dados...");
        Pedido pedidoVerificado = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificado);
        Assert.assertNotNull(pedidoVerificado.getEnderecoEntrega());
        Assert.assertNotNull(pedidoVerificado.getEnderecoEntrega().getCep());
    }
}
