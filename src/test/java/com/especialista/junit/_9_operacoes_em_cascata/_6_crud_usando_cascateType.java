package com.especialista.junit._9_operacoes_em_cascata;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class _6_crud_usando_cascateType extends EntityManagerTest {

    @Test
    public void persistirPedidoComItemPedido(){
        logger.info(">>> Buscando Cliente");
        Cliente cliente = entityManager.find(Cliente.class, 1);

        logger.info(">>> Buscando Produto");
        Produto produto1 = entityManager.find(Produto.class, 1);

        logger.info(">>> Criando novo Produto");
        Produto produto2 = new Produto();
        produto2.setId(2);
        produto2.setNome("Monitor");
        produto2.setDescricao("Monitor com a melhor tela");
        produto2.setPreco(new BigDecimal("4000"));

        logger.info(">>> Instanciando novo Pedido");
        Pedido pedido = new Pedido();
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setCliente(cliente);
//        pedido.setTotal(produto1.getPreco()); // não precisa porque o método é chamado em callback
        pedido.setStatus(StatusPedido.AGUARDANDO);

        logger.info(">>> Instanciando novo ItemPedido1");
        ItemPedido itemPedido1 = new ItemPedido();
        itemPedido1.setId(new ItemPedidoId());
        itemPedido1.setPedido(pedido);
        itemPedido1.setProduto(produto1);
        itemPedido1.setQuantidade(2);
        itemPedido1.setPrecoProduto(produto1.getPreco());

        logger.info(">>> Iniciando uma transação...");
        entityManager.getTransaction().begin();

        logger.info("Fazendo a busca do produto2 no banco de dados e no retorno cria uma cópia do objeto" +
            " e coloca na memória para ser gerenciada pelo EntityManager...");
        produto2 = entityManager.merge(produto2);

        logger.info(">>> Instanciando novo ItemPedido2");
        ItemPedido itemPedido2 = new ItemPedido();
        itemPedido2.setId(new ItemPedidoId());
        itemPedido2.setPedido(pedido);
        itemPedido2.setProduto(produto2);
        itemPedido2.setQuantidade(1);
        itemPedido2.setPrecoProduto(produto2.getPreco());

        logger.info(">>> Adicionando lista de ItemPedidos ao Pedido");
        pedido.setItensPedido(Arrays.asList(itemPedido1, itemPedido2));

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
    public void removerItensPedidoEPedido(){
        logger.info(">>> Buscando ItemPedido");
        ItemPedido itemPedido = entityManager.find(ItemPedido.class, new ItemPedidoId(29, 1));

        logger.info(">>> Iniciando uma transação...");
        entityManager.getTransaction().begin();

        logger.info("Fazendo a remoção do itemPedido no banco de dados...");

//      Não dá erro porque não tem nenhuma outra tabela referenciando a tabela itemPedido
        entityManager.remove(itemPedido); // Tem que ter configurado o CascadeType.REMOVE para remover pedido em cascada quando remove o ItemPedido

//      entityManager.persist(pedido); Não necessário porque está removendo em cascata

        logger.info(">>> JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();

        logger.info(">>>  Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        logger.info(">>> Buscando Pedido no banco de dados...");
        Pedido pedidoVerificacao = entityManager.find(Pedido.class, itemPedido.getPedido().getId());
        Assert.assertNull(pedidoVerificacao);
    }

    public static long numero11SemZerosAEsquerda() {
        return ThreadLocalRandom.current().nextLong(10_000_000_000L, 100_000_000_000L);
    }

}
