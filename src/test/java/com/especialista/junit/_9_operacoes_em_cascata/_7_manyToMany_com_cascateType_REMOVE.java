package com.especialista.junit._9_operacoes_em_cascata;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Categoria;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class _7_manyToMany_com_cascateType_REMOVE extends EntityManagerTest {

    @Test
    public void removerRelacaoProdutoCategoria(){
        logger.info(">>> Buscando Produto");
        Produto produto1 = entityManager.find(Produto.class, 1);

        logger.info(">>> Buscando Categoria");
        Categoria categoria = entityManager.find(Categoria.class, 1);

        logger.info(">>> Iniciando uma transação...");
        entityManager.getTransaction().begin();

        produto1.setCategorias(Arrays.asList(categoria));

        logger.info("Fazendo a inserção da relação do produto com categoria no banco de dados...");
        entityManager.persist(produto1);

        logger.info(">>> JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();

        logger.info(">>>  Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        logger.info(">>> Buscando Produto atualizado");
        Produto produto2 = entityManager.find(Produto.class, 1);

        Assert.assertFalse(produto2.getCategorias().isEmpty());

        logger.info(">>> Iniciando uma transação...");
        entityManager.getTransaction().begin();

        logger.info("Fazendo a remoção da relação do produto com categoria no banco de dados...");
        produto2.getCategorias().clear();

//      entityManager.persist(categoria); Não necessário porque está removendo em cascata

        logger.info(">>> JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();
//
        logger.info(">>>  Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();
//
        logger.info(">>> Buscando Pedido no banco de dados...");
        Produto produtoVerificado = entityManager.find(Produto.class, produto2.getId());
        Assert.assertTrue(produtoVerificado.getCategorias().isEmpty());
    }






}
