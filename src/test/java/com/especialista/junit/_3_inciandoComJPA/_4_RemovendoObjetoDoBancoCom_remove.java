package com.especialista.junit._3_inciandoComJPA;

import com.especialista.jpa._2_iniciandoComJPA.modelo.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class _4_RemovendoObjetoDoBancoCom_remove extends EntityManagerTest {


    @Test
    public void removendoObjeto() {
        System.out.println(">>> 1. Fazendo a consulta do produto no banco de dados...");
        Produto produto = entityManager.find(Produto.class, 3);

        entityManager.getTransaction().begin(); // Início da transação

//      Remova a instância da entidade, ou seja, da tabela.
        entityManager.remove(produto);

        System.out.println(">>> 2. Fazendo a remoção do produto no banco de dados...");
        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

//      Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.
//      entityManager.clear(); // Não é necessário para operação de remoção

        System.out.println(">>> 3. Fazendo a consulta no banco de dados para verificar se o produto foi removido...");
        Produto produtoVerificado = entityManager.find(Produto.class, 3);
        Assert.assertNull(produtoVerificado);
    }
}
