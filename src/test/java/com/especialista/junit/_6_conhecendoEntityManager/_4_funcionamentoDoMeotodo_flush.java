package com.especialista.junit._6_conhecendoEntityManager;

import com.especialista.jpa._6_mapeamentoAvancado.modelos.Pedido;
import com.especialista.jpa._6_mapeamentoAvancado.modelos.StatusPedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

public class _4_funcionamentoDoMeotodo_flush extends EntityManagerTest {


    @Test
    public void chamarFlush() {
        try {
            System.out.println("\n>>> 1. Buscando o pedido no banco de dados...");
            Pedido pedido = entityManager.find(Pedido.class, 1);

            System.out.println("\n>>> 2. Início da transação...");
            entityManager.getTransaction().begin(); // Início da transação

            pedido.setStatus(StatusPedido.PAGO);

            System.out.println("\n>>> 3. Sincronizando as alterações feitas na entidade com o banco de dados...");
            entityManager.flush();

            if (pedido.getPagamento() == null) {
                throw new RuntimeException(">>> Pedido ainda não foi pago");
            }


//            >>> Uma consulta usando JPQL ou Criteria API obriga o JPA a sincronizar o que ele tem na memória
//            System.out.println("\n>>> 3. Executando JPQL...");
//            Pedido pedidoPago = entityManager
//                .createQuery("SELECT p FROM Pedido p WHERE p.id = 1", Pedido.class)
//                .getSingleResult();

//            Assert.assertEquals(pedido.getStatus(), pedidoPago.getStatus());


            System.out.println("\n>>> 4. Fim da transação (confirma a transação)...");
            entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

        } catch (Exception e) {

            System.out.println("\n" + e.getMessage());

            System.out.println("\n>>> 4. Fim da transação (cancela a transação)...");
            entityManager.getTransaction().rollback();
        }
    }
}
