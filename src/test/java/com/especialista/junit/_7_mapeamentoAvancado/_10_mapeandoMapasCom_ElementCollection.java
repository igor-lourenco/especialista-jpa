package com.especialista.junit._7_mapeamentoAvancado;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class _10_mapeandoMapasCom_ElementCollection extends EntityManagerTest {


    @Test
    public void aplicarContatos(){
        System.out.println("\n>>> 1. Buscando Cliente no banco de dados...");
        Cliente cliente = entityManager.find(Cliente.class, 1);

        System.out.println("\n>>> 2. Adicionando um Map de contatos ao Cliente...");
        cliente.setContatos(Collections.singletonMap("email", "fernando@email.com"));

        System.out.println("\n>>> 3. Iniciando uma transação...");
        entityManager.getTransaction().begin();// Início da transação


        System.out.println("\n>>> 4. JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();// Fim da transação (confirma a transação)

        System.out.println("\n>>> 5. Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        System.out.println("\n>>> 6. Buscando Cliente criado no banco de dados...");
        Cliente clienteVerificado = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertEquals("fernando@email.com", clienteVerificado.getContatos().get("email"));

    }
}
