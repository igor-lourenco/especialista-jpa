package com.especialista.junit._9_operacoes_em_cascata;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class _1_cascateType_Persist extends EntityManagerTest {

    @Test
    public void persistirPedidoComItemPedido(){
        logger.info(">>> Buscando Cliente");
        Cliente cliente = entityManager.find(Cliente.class, 1);

        logger.info(">>> Buscando Produto");
        Produto produto = entityManager.find(Produto.class, 1);

        logger.info(">>> Instanciando novo Pedido");
        Pedido pedido = new Pedido();
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setCliente(cliente);
        pedido.setTotal(produto.getPreco());
        pedido.setStatus(StatusPedido.AGUARDANDO);


        logger.info(">>> Instanciando novo ItemPedido");
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(new ItemPedidoId());
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(1);
        itemPedido.setPrecoProduto(produto.getPreco());

        logger.info(">>> Adicionando lista de ItemPedido ao Pedido");
        // Tem que ter configurado o CascadeType.PERSIST para salvar itemPedido em cascada quando salvar o pedido
        pedido.setItensPedido(Arrays.asList(itemPedido));

        logger.info(">>> Iniciando uma transação...");
        entityManager.getTransaction().begin();

        logger.info(">>> Colocando uma novo Pedido no contexto de persistência usando o persist()...");
        entityManager.persist(pedido);
//      entityManager.persist(itemPedido); Não necessário porque está salvando em cascata

        logger.info(">>> JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();

        logger.info(">>>  Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        logger.info(">>> Buscando Pedido criado no banco de dados...");
        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificacao);
        Assert.assertFalse(pedidoVerificacao.getItensPedido().isEmpty());
    }

    @Test
    public void persistirPedidoComCliente(){
        logger.info(">>> Instanciando novo Cliente");
        Cliente cliente = new Cliente();
        cliente.setDataNascimento(LocalDate.of(1980, Month.JANUARY, 1));
        cliente.setSexo(SexoCliente.MASCULINO);
        cliente.setNome("José Carlos");
        cliente.setCpf(String.valueOf(numero11SemZerosAEsquerda()));

        logger.info(">>> Instanciando novo Pedido");
        Pedido pedido = new Pedido();
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setCliente(cliente);
        pedido.setTotal(BigDecimal.ZERO);
        pedido.setStatus(StatusPedido.AGUARDANDO);

        logger.info(">>> Iniciando uma transação...");
        entityManager.getTransaction().begin();

        logger.info(">>> Colocando novo Cliente no contexto de persistência usando o persist()...");
        entityManager.persist(cliente); // Alternativa caso não queira usar o CascadeType.PERSIST
        logger.info(">>> Colocando novo Pedido no contexto de persistência usando o persist()...");
        entityManager.persist(pedido);

        logger.info(">>> JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();

        logger.info(">>>  Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        logger.info(">>> Buscando Cliente criado no banco de dados...");
        Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertNotNull(clienteVerificacao);
    }


    @Test
    public void persistirItemPedidoComPedido(){
        logger.info(">>> Buscando Cliente");
        Cliente cliente = entityManager.find(Cliente.class, 1);

        logger.info(">>> Buscando Produto");
        Produto produto = entityManager.find(Produto.class, 1);

        logger.info(">>> Instanciando novo Pedido");
        Pedido pedido = new Pedido();
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setCliente(cliente);
        pedido.setTotal(produto.getPreco());
        pedido.setStatus(StatusPedido.AGUARDANDO);


        logger.info(">>> Instanciando novo ItemPedido");
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(new ItemPedidoId());
        itemPedido.setPedido(pedido);   // Não é necessário CascadeType.PERSIST porque possui @MapsId
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(1);
        itemPedido.setPrecoProduto(produto.getPreco());

        logger.info(">>> Iniciando uma transação...");
        entityManager.getTransaction().begin();

        logger.info(">>> Colocando uma novo ItemPedido no contexto de persistência usando o persist()...");
//      entityManager.persist(pedido); // Não necessário porque está salvando em cascata
        entityManager.persist(itemPedido);

        logger.info(">>> JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();

        logger.info(">>>  Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        logger.info(">>> Buscando Pedido criado no banco de dados...");
        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificacao);
        Assert.assertFalse(pedidoVerificacao.getItensPedido().isEmpty());
    }


    public static long numero11SemZerosAEsquerda() {
        return ThreadLocalRandom.current().nextLong(10_000_000_000L, 100_000_000_000L);
    }

}
