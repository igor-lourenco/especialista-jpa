package com.especialista.junit._7_mapeamentoAvancado;

import com.especialista.jpa._6_mapeamentoAvancado.modelos.Cliente;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

public class _7_declarandoPropriedadesCom_transient extends EntityManagerTest {


    @Test
    public void validarPrimeiroNome(){
        System.out.println("\n>>> 1. Buscando Pedido no banco de dados...");
        Cliente cliente = entityManager.find(Cliente.class, 1);


        Assert.assertEquals("Fernando", cliente.getPrimeiroNome());
    }
}
