package com.especialista.junit._4_mapeamentoBasico;

import com.especialista.jpa._3_mapeamentoBasico.modelos.Cliente;
import com.especialista.jpa._3_mapeamentoBasico.modelos.SexoCliente;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

public class _1_MapeandoEnumeracoes  extends EntityManagerTest {

    @Test
    public void testarEnum(){
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("José Mineiro");
        cliente.setSexo(SexoCliente.MASCULINO);

        entityManager.getTransaction().begin();

        entityManager.persist(cliente);

        entityManager.getTransaction().commit();

        entityManager.clear();

        Cliente clienteVerificado = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertNotNull(clienteVerificado);
    }
}
