package com.especialista.junit._7_mapeamentoAvancado;

import com.especialista.jpa._6_mapeamentoAvancado.modelos.Cliente;
import com.especialista.jpa._6_mapeamentoAvancado.modelos.SexoCliente;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

public class _13_mapeandoTabelaSecundariaCom_SecondaryTable extends EntityManagerTest {


    @Test
    public void salvarCliente(){
        System.out.println("\n>>> 1. Instânciando novo Cliente...");
        Cliente cliente = new Cliente();
        cliente.setNome("Carlos Finotti");
        cliente.setSexo(SexoCliente.MASCULINO);
        cliente.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));

        System.out.println("\n>>> 2. Iniciando uma transação...");
        entityManager.getTransaction().begin();// Início da transação

        System.out.println("\n>>> 3. Colocando uma novo Cliente no contexto de persistência usando o persist()...");
        entityManager.persist(cliente);

        System.out.println("\n>>> 4. JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();// Fim da transação (confirma a transação)

        System.out.println("\n>>> 5. Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();


        System.out.println("\n>>> 6. Buscando Cliente criado no banco de dados...");
        Cliente clienteVerificado = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertNotNull(clienteVerificado.getSexo());

    }

}
