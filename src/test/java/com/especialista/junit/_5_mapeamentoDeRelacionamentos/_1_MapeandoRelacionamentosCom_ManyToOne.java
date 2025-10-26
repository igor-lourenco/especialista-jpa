package com.especialista.junit._5_mapeamentoDeRelacionamentos;

import com.especialista.jpa._4_mapeamentoDeRelacionamento.modelos.Cliente;
import com.especialista.jpa._4_mapeamentoDeRelacionamento.modelos.Endereco;
import com.especialista.jpa._4_mapeamentoDeRelacionamento.modelos.Pedido;
import com.especialista.jpa._4_mapeamentoDeRelacionamento.modelos.StatusPedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class _1_MapeandoRelacionamentosCom_ManyToOne extends EntityManagerTest {

    @Test
    public void verificarRelacionamento_ManyToOne(){
        System.out.println("\n>>> 1. Buscando o cliente...");
        Cliente cliente = entityManager.find(Cliente.class, 1);

        System.out.println("\n>>> 2. Instanciando o endereco...");
        Endereco enderecoEntrega = getEnderecoEntrega();

        System.out.println("\n>>> 3. Instanciando o pedido...");
        Pedido pedido = new Pedido();
//      pedido.setId(1); // Comentado porque está usando o GenerationType.IDENTITY e causa PersistentObjectException: detached entity passed to persist
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(new BigDecimal("1000"));

        System.out.println("\n>>> 4. Associando pedido(owner) ao cliente(não owner) e endereço ...");
        pedido.setEnderecoEntrega(enderecoEntrega);
        pedido.setCliente(cliente);

        entityManager.getTransaction().begin(); // Início da transação

        System.out.println("\n>>> 5. Fazendo a inserção do novo pedido no banco de dados...");
        entityManager.persist(pedido);

        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

        entityManager.clear(); //Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.

        System.out.println("\n>>> 6. Fazendo a consulta do pedido no banco de dados...");
        Pedido pedidoVerificado = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificado);
        Assert.assertNotNull(pedidoVerificado.getEnderecoEntrega());
        Assert.assertNotNull(pedidoVerificado.getCliente());
    }



    private Endereco getEnderecoEntrega(){
        Endereco enderecoEntrega = new Endereco();
        enderecoEntrega.setCep("12345-67");
        enderecoEntrega.setLogradouro("Rua das Laranjeiras");
        enderecoEntrega.setNumero("123");
        enderecoEntrega.setComplemento("2° andar");
        enderecoEntrega.setBairro("Centro");
        enderecoEntrega.setCidade("Uberlândia");
        enderecoEntrega.setEstado("MG");
        return enderecoEntrega;
    }
}
