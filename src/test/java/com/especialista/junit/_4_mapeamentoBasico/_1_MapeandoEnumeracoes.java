package com.especialista.junit._4_mapeamentoBasico;

import com.especialista.jpa._6_mapeamentoAvancado.modelos.Cliente;
import com.especialista.jpa._6_mapeamentoAvancado.modelos.SexoCliente;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

public class _1_MapeandoEnumeracoes  extends EntityManagerTest {

    @Test
    public void testarEnum(){
        System.out.println(">>> 1. Instanciando o cliente...");

        Cliente cliente = new Cliente();
//        cliente.setId(1); // Comentado porque está usando o GenerationType.IDENTITY e causa PersistentObjectException: detached entity passed to persist
        cliente.setNome("José Mineiro");
        cliente.setSexo(SexoCliente.MASCULINO);

        entityManager.getTransaction().begin();// Início da transação

        System.out.println(">>> 1. Fazendo a inserção do novo cliente no banco de dados...");
        entityManager.persist(cliente);

        entityManager.getTransaction().commit();// Fim da transação (confirma a transação)

        entityManager.clear(); //Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.

        System.out.println(">>> 2. Fazendo a consulta do cliente no banco de dados...");
        Cliente clienteVerificado = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertNotNull(clienteVerificado);
    }
}
