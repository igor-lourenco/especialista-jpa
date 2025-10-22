package com.especialista.junit._4_mapeamentoBasico;

import com.especialista.jpa._3_mapeamentoBasico.modelos.Categoria;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

public class _4_ConfigurandoAGeracaoDeIdentificadorCom_SequenceGenerator extends EntityManagerTest {

    @Test
    public void testarEstrategia_GenerationType_SEQUENCE(){
        System.out.println("\n>>> 1. Instanciando a categoria...");

        Categoria categoria = new Categoria();
        categoria.setNome("Eletrônicos");

        System.out.println("\n>>> 2. Buscando na tabela 'tb_categoria_sequencias_chave_primaria' o valor do id ...");
        entityManager.getTransaction().begin(); // Início da transação

        entityManager.persist(categoria);

        System.out.println("\n>>> 3. Fazendo a inserção da nova categoria no banco de dados...");
        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

        entityManager.clear(); //Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.

        System.out.println("\n>>> 4. Fazendo a consulta da categoria no banco de dados...");
        Categoria categoriaVerificado = entityManager.find(Categoria.class, categoria.getId());
        Assert.assertNotNull(categoriaVerificado);
    }
}
