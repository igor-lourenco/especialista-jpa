package com.especialista.junit._7_mapeamentoAvancado;

import com.especialista.jpa._6_mapeamentoAvancado.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class _8_colecoesDeTiposBasicos extends EntityManagerTest {


    @Test
    public void aplicarTags(){
        System.out.println("\n>>> 1. Buscando Produto no banco de dados...");
        Produto produto = entityManager.find(Produto.class, 1);
        produto.setTags(Arrays.asList("ebook", "livro-digital"));

        System.out.println("\n>>> 2. Iniciando uma transação...");
        entityManager.getTransaction().begin();// Início da transação


        System.out.println("\n>>> 3. JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();// Fim da transação (confirma a transação)

        System.out.println("\n>>> 4. Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        System.out.println("\n>>> 5. Buscando Produto criado no banco de dados...");
        Produto produtoVerificado = entityManager.find(Produto.class, produto.getId());
        Assert.assertEquals(2, produtoVerificado.getTags().size());

    }
}
