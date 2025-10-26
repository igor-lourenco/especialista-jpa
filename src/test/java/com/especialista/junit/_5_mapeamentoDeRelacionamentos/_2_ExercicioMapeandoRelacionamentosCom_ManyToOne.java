package com.especialista.junit._5_mapeamentoDeRelacionamentos;

import com.especialista.jpa._4_mapeamentoDeRelacionamento.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class _2_ExercicioMapeandoRelacionamentosCom_ManyToOne extends EntityManagerTest {

    @Test
    public void verificarRelacionamento_ManyToOne(){
        System.out.println("\n>>> 1. Buscando o cliente...");
        Cliente cliente = entityManager.find(Cliente.class, 1);

        System.out.println("\n>>> 2. Buscando o produto...");
        Produto produto = entityManager.find(Produto.class, 1);

        System.out.println("\n>>> 3. Instanciando o endereco...");
        Endereco enderecoEntrega = getEnderecoEntrega();

        System.out.println("\n>>> 4. Instanciando o pedido...");
        Pedido pedido = getPedido();

        System.out.println("\n>>> 5. Associando pedido(owner) ao cliente(não owner) e endereço ...");
        pedido.setEnderecoEntrega(enderecoEntrega);
        pedido.setCliente(cliente);

        System.out.println("\n>>> 6. Instanciando itemPedido...");
        ItemPedido itemPedido = getItemPedido(produto);

        System.out.println("\n>>> 7. Associando itemPedido(owner) a produto(não owner) e pedido(não owner) ...");
        itemPedido.setProduto(produto);
        itemPedido.setPedido(pedido);

        entityManager.getTransaction().begin(); // Início da transação


        System.out.println("\n>>> 8. Fazendo a inserção do novo pedido no banco de dados...");
        entityManager.persist(pedido);
        System.out.println("\n>>> 9. Fazendo a inserção do novo itemPedido no banco de dados...");
        entityManager.persist(itemPedido);


        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

        entityManager.clear(); //Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.


        System.out.println("\n>>> 10. Fazendo a consulta do pedido e itemPedido no banco de dados...");
        Pedido pedidoVerificado = entityManager.find(Pedido.class, pedido.getId());
        ItemPedido itemPedidoVErificado = entityManager.find(ItemPedido.class, itemPedido.getId());
        Assert.assertNotNull(pedidoVerificado);
        Assert.assertNotNull(pedidoVerificado.getCliente());
        Assert.assertNotNull(itemPedidoVErificado);
        Assert.assertNotNull(itemPedidoVErificado.getProduto());
        Assert.assertNotNull(itemPedidoVErificado.getPedido());
    }

    private static ItemPedido getItemPedido(Produto produto) {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPrecoProduto(produto.getPreco());
        itemPedido.setQuantidade(1);
        return itemPedido;
    }


    private Pedido getPedido(){
        Pedido pedido = new Pedido();
//      pedido.setId(1); // Comentado porque está usando o GenerationType.IDENTITY e causa PersistentObjectException: detached entity passed to persist
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(new BigDecimal("1000"));
        return pedido;
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
