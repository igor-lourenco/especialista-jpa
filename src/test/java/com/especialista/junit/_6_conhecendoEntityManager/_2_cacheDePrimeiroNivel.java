package com.especialista.junit._6_conhecendoEntityManager;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

public class _2_cacheDePrimeiroNivel extends EntityManagerTest {


    @Test
    public void verificarCache() {
        System.out.println("\n>>> 1. Buscando o produto no banco de dados...");

        Produto produto = entityManager.find(Produto.class, 1);
        System.out.println("\nNome produto: " + produto.getNome());

        System.out.println("------------------------------------------------------");

        System.out.println(">>> 2. Buscando o mesmo produto do cache de primeiro nível...");
        Produto produtoResgatado = entityManager.find(Produto.class, produto.getId());
        System.out.println("Nome produto resgatado: " + produtoResgatado.getNome());

    }
}
