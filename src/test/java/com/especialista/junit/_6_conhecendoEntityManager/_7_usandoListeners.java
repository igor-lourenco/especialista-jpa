package com.especialista.junit._6_conhecendoEntityManager;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.StatusPedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

public class _7_usandoListeners extends EntityManagerTest {


    @Test
    public void acionandoListeners() {
        System.out.println("\n>>> 1. Buscando cliente no banco de dados...");
        Cliente cliente = entityManager.find(Cliente.class, 1);


        System.out.println("\n>>> 1. Instânciando novo pedido...");
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.AGUARDANDO);

        entityManager.getTransaction().begin();// Início da transação

        System.out.println("\n>>> 2. Colocando a nova entidade no contexto de persistência usando o persist()...");
        entityManager.persist(pedido);

        System.out.println("\n>>> 3. Usando o flush...");
        entityManager.flush();


        System.out.println("\n>>> 4. Atualizando o status do pedido...");
        pedido.setStatus(StatusPedido.PAGO);

        System.out.println("\n>>> 5. JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();// Fim da transação (confirma a transação)


        System.out.println("\n>>> 6. Limpando o contexto de persistência, " +
            " fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        System.out.println("\n>>> 7. Fazendo a consulta do pedido no banco de dados...");
        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificacao.getDataCriacao());
        Assert.assertNotNull(pedidoVerificacao.getDataUltimaAtualizacao());

    }
}
