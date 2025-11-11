package com.especialista.junit._6_conhecendoEntityManager;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Categoria;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

public class _1_estadosECicloDeVida extends EntityManagerTest {


    @Test
    public void analisarEstados() {
        entityManager.getTransaction().begin(); // Início da transação

//      Estado -> Transient
        System.out.println("\n>>> 1. Estado Transient...");
        Categoria categoriaNovo = new Categoria();
        categoriaNovo.setNome("Eletrônicos");

//      Estado -> Managed
        System.out.println("\n>>> 2. Estado Managed...");
        Categoria categoriaGerenciadaMerge = entityManager.merge(categoriaNovo);
        Categoria categoriaGerenciada = entityManager.find(Categoria.class, 1);


//      Estado -> Removed
        System.out.println("\n>>> 3. Estado Removed...");
        entityManager.remove(categoriaGerenciada);

        System.out.println("\n>>> 4. Voltando para o Estado Managed...");
        entityManager.persist(categoriaGerenciada);

        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)


//      Estado -> Detached
        System.out.println("\n>>> 5. Estado Detached...");
        entityManager.detach(categoriaGerenciada);
        entityManager.clear();
    }
}
