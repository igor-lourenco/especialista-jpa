package com.especialista.junit._5_mapeamentoDeRelacionamentos;

import com.especialista.jpa._4_mapeamentoDeRelacionamento.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class _5_MapeandoAutorelacionamentosCom_ManyToOne_e_OneToMany extends EntityManagerTest {

    @Test
    public void verificarRelacionamento_ManyToOne(){
        System.out.println("\n>>> 1. Instanciando categoria mãe...");
        Categoria categoriaMae = new Categoria();
        categoriaMae.setNome("Eletrônicos");

        System.out.println("\n>>> 2. Instanciando categoria filha...");
        Categoria categoriaFilha = new Categoria();
        categoriaFilha.setNome("Celulares");
        categoriaFilha.setCategoriaPai(categoriaMae);


        entityManager.getTransaction().begin(); // Início da transação


        System.out.println("\n>>> 3. Fazendo a inserção da nova categoriaMae no banco de dados...");
        entityManager.persist(categoriaMae);
        System.out.println("\n>>> 4. Fazendo a inserção da nova categoriaFilha no banco de dados...");
        entityManager.persist(categoriaFilha);


        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

        entityManager.clear(); //Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.


        System.out.println("\n>>> 5. Fazendo a consulta do pedido no banco de dados...");
        Categoria categoriaVerificado = entityManager.find(Categoria.class, categoriaFilha.getId());
        Assert.assertNotNull(categoriaVerificado.getCategoriaPai());
    }


}
