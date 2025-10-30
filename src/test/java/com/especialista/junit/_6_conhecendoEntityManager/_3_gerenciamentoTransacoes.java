package com.especialista.junit._6_conhecendoEntityManager;

import com.especialista.jpa._6_mapeamentoAvancado.modelos.Pedido;
import com.especialista.jpa._6_mapeamentoAvancado.modelos.StatusPedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

public class _3_gerenciamentoTransacoes extends EntityManagerTest {


    @Test
    public void abrirFecharCancelarTransacao() {
        try {
            Pedido pedido = entityManager.find(Pedido.class, 1);

            System.out.println("\n>>> 1. Início da transação...");
            entityManager.getTransaction().begin(); // Início da transação

            pedido.setStatus(StatusPedido.PAGO);

            if (pedido.getPagamento() == null) {
                throw new RuntimeException(">>> Pedido ainda não foi pago");
            }

            System.out.println("\n>>> 2. Fim da transação (confirma a transação)...");
            entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

        } catch (Exception e) {

            System.out.println("\n" + e.getMessage());

            System.out.println("\n>>> 2. Fim da transação (cancela a transação)...");
            entityManager.getTransaction().rollback();
        }
    }
}
