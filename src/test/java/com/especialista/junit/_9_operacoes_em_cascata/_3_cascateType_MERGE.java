package com.especialista.junit._9_operacoes_em_cascata;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class _3_cascateType_MERGE extends EntityManagerTest {

    @Test
    public void atualizarPedidoComItemPedido(){
        logger.info(">>> Buscando Cliente");
        Cliente cliente = entityManager.find(Cliente.class, 1);

        logger.info(">>> Buscando Produto");
        Produto produto = entityManager.find(Produto.class, 1);

        logger.info(">>> Instanciando novo Pedido");
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setCliente(cliente);
//        pedido.setTotal(produto.getPreco()); // não precisa porque o método é chamando em callback
        pedido.setStatus(StatusPedido.AGUARDANDO);


        logger.info(">>> Instanciando novo ItemPedido");
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(new ItemPedidoId());
        itemPedido.getId().setPedidoId(pedido.getId());
        itemPedido.getId().setProdutoId(produto.getId());
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(3);
        itemPedido.setPrecoProduto(produto.getPreco());

        logger.info(">>> Adicionando lista de ItemPedido ao Pedido");
        // Tem que ter configurado o CascadeType.MERGE para salvar itemPedido em cascada quando salvar o pedido
        pedido.setItensPedido(Arrays.asList(itemPedido));

        logger.info(">>> Iniciando uma transação...");
        entityManager.getTransaction().begin();

        logger.info("Fazendo a busca do pedido no banco de dados e no retorno cria uma cópia do objeto" +
            " e coloca na memória para ser gerenciada pelo EntityManager...");
        entityManager.merge(pedido);
//      entityManager.persist(itemPedido); Não necessário porque está salvando em cascata

        logger.info(">>> JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();

        logger.info(">>>  Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        logger.info(">>> Buscando ItemPedido atualizado no banco de dados...");
        ItemPedido itemPedidoVerificacao = entityManager.find(ItemPedido.class, itemPedido.getId());
        Assert.assertTrue(itemPedidoVerificacao.getQuantidade().equals(3));
    }



    @Test
    public void atualizarItemPedidoComPedido(){
        logger.info(">>> Buscando Cliente");
        Cliente cliente = entityManager.find(Cliente.class, 1);

        logger.info(">>> Buscando Produto");
        Produto produto = entityManager.find(Produto.class, 1);

        logger.info(">>> Instanciando novo Pedido");
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setCliente(cliente);
//        pedido.setTotal(produto.getPreco()); // não precisa porque o método é chamando em callback
        pedido.setStatus(StatusPedido.PAGO);


        logger.info(">>> Instanciando novo ItemPedido");
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(new ItemPedidoId());
        itemPedido.getId().setPedidoId(pedido.getId());
        itemPedido.getId().setProdutoId(produto.getId());
        itemPedido.setPedido(pedido); // Tem que ter configurado o CascadeType.MERGE para salvar pedido em cascada quando salvar o ItemPedido
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(5);
        itemPedido.setPrecoProduto(produto.getPreco());

        logger.info(">>> Adicionando lista de ItemPedido ao Pedido");
        pedido.setItensPedido(Arrays.asList(itemPedido));

        logger.info(">>> Iniciando uma transação...");
        entityManager.getTransaction().begin();

        logger.info("Fazendo a busca do produto no banco de dados e no retorno cria uma cópia do objeto" +
            " e coloca na memória para ser gerenciada pelo EntityManager...");
//        entityManager.merge(pedido); // Não necessário porque está salvando em cascata
        entityManager.merge(itemPedido);

        logger.info(">>> JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();

        logger.info(">>>  Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        logger.info(">>> Buscando ItemPedido atualizado no banco de dados...");
        ItemPedido itemPedidoVerificacao = entityManager.find(ItemPedido.class, itemPedido.getId());
        Assert.assertTrue(StatusPedido.PAGO.equals(itemPedidoVerificacao.getPedido().getStatus()));
        Assert.assertTrue(itemPedidoVerificacao.getQuantidade().equals(5));
    }

}
