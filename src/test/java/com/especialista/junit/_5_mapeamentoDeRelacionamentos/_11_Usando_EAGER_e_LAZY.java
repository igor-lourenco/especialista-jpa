package com.especialista.junit._5_mapeamentoDeRelacionamentos;

import com.especialista.jpa._5_conhecendoEntityManager.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

public class _11_Usando_EAGER_e_LAZY extends EntityManagerTest {

    @Test
    public void verificarRelacionamento_OneToOne_Com_JoinTable(){

        System.out.println("\n>>> 1. Buscando pedido no banco de dados...");
        Pedido pedido = entityManager.find(Pedido.class, 1);


        System.out.println("\n>>> 2. Carregamento lento com Fetch LAZY...");
        pedido.getItensPedido().isEmpty();
    }

}
