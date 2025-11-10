package com.especialista.junit._5_mapeamentoDeRelacionamentos;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Categoria;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

public class _5_MapeandoAutorelacionamentosCom_ManyToOne_e_OneToMany extends EntityManagerTest {

    @Test
    public void verificarRelacionamento_ManyToOne() {
        System.out.println("\n>>> 1. Instanciando categoria pai...");
        Categoria categoriaPai = new Categoria();
        categoriaPai.setNome("Eletrônicos");

        System.out.println("\n>>> 2. Instanciando categoria filha...");
        Categoria categoriaFilha = new Categoria();
        categoriaFilha.setNome("Celulares");

        System.out.println("\n>>> 7. Associando categoriaFilha(owner) a categoriaPai(não owner)...");
        categoriaFilha.setCategoriaPai(categoriaPai);


        entityManager.getTransaction().begin(); // Início da transação


        System.out.println("\n>>> 3. Fazendo a inserção da nova categoriaPai no banco de dados...");
        entityManager.persist(categoriaPai);
        System.out.println("\n>>> 4. Fazendo a inserção da nova categoriaFilha no banco de dados...");
        entityManager.persist(categoriaFilha);


        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

        entityManager.clear(); //Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.


        System.out.println("\n>>> 5. Fazendo a consulta do pedido no banco de dados...");
        Categoria categoriaVerificado = entityManager.find(Categoria.class, categoriaFilha.getId());
        Assert.assertNotNull(categoriaVerificado.getCategoriaPai());
    }


}
