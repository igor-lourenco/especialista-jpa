package com.especialista.junit._5_mapeamentoDeRelacionamentos;

import com.especialista.jpa._5_conhecendoEntityManager.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

public class _6_RemovendoObjetosReferenciados extends EntityManagerTest {

    @Test
    public void verificarRelacionamento_ManyToOne(){
        System.out.println("\n>>> 1. Buscando pedido na base de dados...");
        Pedido pedido = entityManager.find(Pedido.class, 1);

        Assert.assertFalse(pedido.getItensPedido().isEmpty());

        entityManager.getTransaction().begin(); // Início da transação

        System.out.println("\n>>> 4. Fazendo a remoção dos itemPedido(owner) no banco de dados...");
        pedido.getItensPedido().forEach(entityManager::remove);

        System.out.println("\n>>> 5. Fazendo a remoção do pedido(não owner) no banco de dados...");
        entityManager.remove(pedido);


        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

//      Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.
//      entityManager.clear(); // Não é necessário para operação de remoção

        System.out.println("\n>>> 6. Fazendo a consulta do pedido no banco de dados...");
        Pedido pedidoVerificado = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNull(pedidoVerificado);
    }


}
