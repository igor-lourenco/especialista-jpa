package com.especialista.junit._5_mapeamentoDeRelacionamentos;

import com.especialista.jpa._5_conhecendoEntityManager.modelos.Categoria;
import com.especialista.jpa._5_conhecendoEntityManager.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class _7_MapeandoRelacionamentosCom_ManyToMany extends EntityManagerTest {

    @Test
    public void verificarRelacionamento_ManyToMany(){
        System.out.println("\n>>> 1. Buscando produto no banco de dados...");
        Produto produto = entityManager.find(Produto.class, 1);

        System.out.println("\n>>> 2. Buscando categoria no banco de dados...");
        Categoria categoria = entityManager.find(Categoria.class, 1);

        entityManager.getTransaction().begin(); // Início da transação

//      categoria.setProdutos(Arrays.asList(produto)); // não vai funcionar porque categoria não é o owner da relação

        produto.setCategorias(Arrays.asList(categoria));

        System.out.println("\n>>> 5. Salvando produto(owner) no banco de dados...");
        entityManager.persist(produto);

        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

//      Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.
        entityManager.clear();

        System.out.println("\n>>> 6. Fazendo a consulta da categoria no banco de dados...");
        Categoria categoriaVerificado = entityManager.find(Categoria.class, categoria.getId());
        Assert.assertFalse(categoriaVerificado.getProdutos().isEmpty());
    }


}
