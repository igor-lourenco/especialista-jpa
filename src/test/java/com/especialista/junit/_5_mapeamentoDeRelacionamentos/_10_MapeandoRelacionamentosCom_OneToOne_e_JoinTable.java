package com.especialista.junit._5_mapeamentoDeRelacionamentos;

import com.especialista.jpa._4_mapeamentoDeRelacionamento.modelos.NotaFiscal;
import com.especialista.jpa._4_mapeamentoDeRelacionamento.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class _10_MapeandoRelacionamentosCom_OneToOne_e_JoinTable extends EntityManagerTest {

    @Test
    public void verificarRelacionamento_OneToOne_Com_JoinTable(){
        System.out.println("\n>>> 1. Buscando pedido no banco de dados...");
        Pedido pedido = entityManager.find(Pedido.class, 1);

        System.out.println("\n>>> 2. Instanciando NotaFiscal...");
        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setXml("TESTE");
        notaFiscal.setDataEmissao(new Date());
        notaFiscal.setPedido(pedido);


        System.out.println("\n>>> 3. Fazendo o relacionamento entre notaFiscal(owner) e pedido(não owner)...");
        notaFiscal.setPedido(pedido);

        entityManager.getTransaction().begin(); // Início da transação

        System.out.println("\n>>> 4. Salvando notaFiscal no banco de dados...");
        entityManager.persist(notaFiscal);

        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

//      Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.
        entityManager.clear();

        System.out.println("\n>>> 5. Fazendo a consulta do pedido no banco de dados...");
        Pedido pedidoVerificado = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificado.getNotaFiscal());
    }

}
