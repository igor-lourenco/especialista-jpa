package com.especialista.junit._9_operacoes_em_cascata;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.ItemPedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.ItemPedidoId;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

public class _5_cascateType_REMOVE extends EntityManagerTest {

//    @Test
    public void removerPedidoEItens(){
        logger.info(">>> Buscando Pedido");
        Pedido pedido = entityManager.find(Pedido.class, 21);


        logger.info(">>> Iniciando uma transação...");
        entityManager.getTransaction().begin();

        logger.info("Fazendo a remoção do pedido no banco de dados...");
        entityManager.remove(pedido); // Tem que ter configurado o CascadeType.REMOVE para remover itemPedido em cascada quando remove o pedido
//      entityManager.persist(itemPedido); Não necessário porque está removendo em cascata

        logger.info(">>> JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();

        logger.info(">>>  Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        logger.info(">>> Buscando Pedido no banco de dados...");
        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNull(pedidoVerificacao);
    }


    @Test
    public void removerItensPedidoEPedido(){
        logger.info(">>> Buscando ItemPedido");
        ItemPedido itemPedido = entityManager.find(ItemPedido.class, new ItemPedidoId(20, 1));

        logger.info(">>> Iniciando uma transação...");
        entityManager.getTransaction().begin();

        logger.info("Fazendo a remoção do itemPedido no banco de dados...");

//         Não dá erro porque não tem nenhuma outra tabela referenciando a tabela itemPedido
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





}
