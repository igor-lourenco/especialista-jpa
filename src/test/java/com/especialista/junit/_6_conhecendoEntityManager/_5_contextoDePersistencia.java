package com.especialista.junit._6_conhecendoEntityManager;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

import java.math.BigDecimal;

public class _5_contextoDePersistencia extends EntityManagerTest {


    @Test
    public void usarContextoDePersistencia() {
        System.out.println("\n>>> 1. Buscando o produto no banco de dados...");
        Produto produto = entityManager.find(Produto.class, 1);

        System.out.println("\n>>> 2. A entidade produto retornada já estará no contexto de persistência...");
        System.out.println("\n>>> 3. Ao fazer qualquer alteração na entidade que está contexto de persistência, " +
                                    "o JPA irá identificar e persistir no banco de dados quando usar o commit do EntityManager...");


        System.out.println("\n>>> 4. Alterando o preço do produto...");
        produto.setPreco(new BigDecimal("100.00"));

        entityManager.getTransaction().begin();// Início da transação

        Produto produtoNovo1 = new Produto();
        produtoNovo1.setNome("Caneca para Café");
        produtoNovo1.setPreco(new BigDecimal("10.0"));
        produtoNovo1.setDescricao("Produto para café");
        System.out.println("\n>>> 5. Colocando uma nova entidade no contexto de persistência usando o persist()...");
        entityManager.persist(produtoNovo1);


        Produto produtoNovo2 = new Produto();
        produtoNovo2.setNome("Caneca para Café");
        produtoNovo2.setPreco(new BigDecimal("10.0"));
        produtoNovo1.setDescricao("Produto para chá");
        System.out.println("\n>>> 6. Colocando uma nova entidade no contexto de persistência usando o merge()...");
        produtoNovo2 = entityManager.merge(produtoNovo2);



        System.out.println("\n>>> 6. Usando o flush...");
        entityManager.flush();


        System.out.println("\n>>> 7. Alterando as entidades...");
        produtoNovo1.setDescricao("Alterar descrição");
        produtoNovo2.setDescricao("Alterar descrição");

        System.out.println("\n>>> 8. JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();// Fim da transação (confirma a transação)

    }
}
