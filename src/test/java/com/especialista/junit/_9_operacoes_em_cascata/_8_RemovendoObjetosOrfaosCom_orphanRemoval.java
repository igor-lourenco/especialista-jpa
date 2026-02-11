package com.especialista.junit._9_operacoes_em_cascata;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

public class _8_RemovendoObjetosOrfaosCom_orphanRemoval extends EntityManagerTest {

    @Test
    public void removerItensOrfaos(){
        logger.info(">>> Buscando Pedido");
        Pedido pedido = entityManager.find(Pedido.class, 19);

        Assert.assertFalse(pedido.getItensPedido().isEmpty());

        logger.info(">>> Iniciando uma transação...");
        entityManager.getTransaction().begin();

        logger.info("Fazendo a remoção do itemPedido no banco de dados...");
        pedido.getItensPedido().remove(0);

        logger.info(">>> JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();

        logger.info(">>>  Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        logger.info(">>> Buscando Pedido atualizado no banco de dados...");
        Pedido pedidoVerificado = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertTrue(pedidoVerificado.getItensPedido().isEmpty());
    }






}
