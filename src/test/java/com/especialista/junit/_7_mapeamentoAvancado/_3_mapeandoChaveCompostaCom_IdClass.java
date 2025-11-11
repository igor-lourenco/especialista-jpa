package com.especialista.junit._7_mapeamentoAvancado;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class _3_mapeandoChaveCompostaCom_IdClass extends EntityManagerTest {


    @Test
    public void salvarItemPedido() {
        System.out.println("\n>>> 1. Buscando Cliente no banco de dados...");
        Cliente cliente = entityManager.find(Cliente.class, 1);

        System.out.println("\n>>> 3. Buscando Produto no banco de dados...");
        Produto produto = entityManager.find(Produto.class, 1);

        System.out.println("\n>>> 4. Instânciando novo Pedido...");
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(BigDecimal.TEN);


        System.out.println("\n>>> 5. Iniciando uma transação...");
        entityManager.getTransaction().begin();// Início da transação

        System.out.println("\n>>> 5. Colocando uma novo Pedido no contexto de persistência usando o persist()...");
        entityManager.persist(pedido);

        System.out.println("\n>>> 6. Sincronizando as alterações feitas na entidade com o banco de dados...");
        entityManager.flush();

        System.out.println("\n>>> 4. Instânciando novo ItemPedido...");
        ItemPedido itemPedido = new ItemPedido();
//        itemPedido.setPedidoId(pedido.getId()); // @IdClass - chave primária composta
        itemPedido.setPedido(pedido);
//        itemPedido.setProdutoId(produto.getId()); // @IdClass - chave primária composta
        itemPedido.setProduto(produto);
        itemPedido.setPrecoProduto(produto.getPreco());
        itemPedido.setQuantidade(1);
        itemPedido.setId(new ItemPedidoId(pedido.getId(), produto.getId()));


        System.out.println("\n>>> 5. Colocando um novo ItemPedido no contexto de persistência usando o persist()...");
        entityManager.persist(itemPedido);

        System.out.println("\n>>> 3. JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();// Fim da transação (confirma a transação)

        System.out.println("\n>>> 4. Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        System.out.println("\n>>> 5. Buscando Pedido criado no banco de dados...");
        Pedido pedidoVerificado = entityManager.find(Pedido.class, pedido.getId());

        Assert.assertNotNull(pedidoVerificado);
        Assert.assertFalse(pedidoVerificado.getItensPedido().isEmpty());

    }

    @Test
    public void buscarItem(){
        System.out.println("\n>>> 1. Buscando ItemPedido a partir da classe de chave primária composta ItemPedidoId no banco de dados...");
        ItemPedido itemPedido = entityManager.find(ItemPedido.class, new ItemPedidoId(1, 1));

        Assert.assertNotNull(itemPedido);
    }
}
