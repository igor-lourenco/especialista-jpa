package com.especialista.junit._5_mapeamentoDeRelacionamentos;

import com.especialista.jpa._6_mapeamentoAvancado.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class _2_ExercicioMapeandoRelacionamentosCom_ManyToOne extends EntityManagerTest {

    @Test
    public void verificarRelacionamento_ManyToOne() {
        print("\n>>> 1. Iniciando uma transação...");
        entityManager.getTransaction().begin(); // Início da transação
        
        print("\n>>> 2. Buscando o cliente...");
        Cliente cliente = entityManager.find(Cliente.class, 1);

        print("\n>>> 3. Buscando o produto...");
        Produto produto = entityManager.find(Produto.class, 1);

        print("\n>>> 4. Instanciando o endereco...");
        Endereco enderecoEntrega = getEnderecoEntrega();

        print("\n>>> 5. Instanciando o pedido...");
        Pedido pedido = getPedido();

        print("\n>>> 6. Associando pedido(owner) ao cliente(não owner) e endereço ...");
        pedido.setEnderecoEntrega(enderecoEntrega);
        pedido.setCliente(cliente);


        print("\n>>> 7. Colocando uma novo Pedido no contexto de persistência usando o persist()...");
        entityManager.persist(pedido);
        
        print("\n>>> 8. Sincronizando as alterações feitas na entidade com o banco de dados...");
        entityManager.flush();

        print("\n>>> 9. Instanciando itemPedido...");
        ItemPedido itemPedido = getItemPedido(produto);

        print("\n>>> 10. Associando itemPedido(owner) a produto(não owner) e pedido(não owner) ...");
        itemPedido.setProduto(produto);
        itemPedido.setPedido(pedido);
        itemPedido.setPedidoId(pedido.getId());
        itemPedido.setProdutoId(produto.getId());


        print("\n>>> 11. Colocando uma novo ItemPedido no contexto de persistência usando o persist()...");
        entityManager.persist(itemPedido);

        System.out.println("\n>>> 12. JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)


        entityManager.clear(); //Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.


        print("\n>>> 13. Fazendo a consulta do pedido e itemPedido no banco de dados...");
        Pedido pedidoVerificado = entityManager.find(Pedido.class, pedido.getId());
        ItemPedido itemPedidoVErificado = entityManager.find(ItemPedido.class, new ItemPedidoId(pedido.getId(), produto.getId()));
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


    private Pedido getPedido() {
        Pedido pedido = new Pedido();
//      pedido.setId(1); // Comentado porque está usando o GenerationType.IDENTITY e causa PersistentObjectException: detached entity passed to persist
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(new BigDecimal("1000"));
        return pedido;
    }

    private Endereco getEnderecoEntrega() {
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
    
    public void print(String text){
        System.out.println(text);
    }
}
