package com.especialista.junit._9_operacoes_em_cascata;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class _4_manyToMany_com_cascateType_MERGE extends EntityManagerTest {

    @Test
    public void atualizarProdutoComCategoria(){
        Produto produto = new Produto();
        logger.info(">>> Criando Produto");
        produto.setId(1);
        produto.setDataUltimaAtualizacao(LocalDateTime.now());
        produto.setPreco(new BigDecimal("500"));
        produto.setNome("Kindle teste");
        produto.setDescricao("Agora com iluminação embutida ajustável");

        logger.info(">>> Criando Categoria");
        Categoria categoria = new Categoria();
        categoria.setId(2);
        categoria.setNome("Tablets teste");

        // Tem que ter configurado o CascadeType.MERGE para salvar categoria em cascada quando salvar o  produto
        produto.setCategorias(Arrays.asList(categoria));


        logger.info(">>> Iniciando uma transação...");
        entityManager.getTransaction().begin();

        logger.info("Fazendo a busca do produto no banco de dados e no retorno cria uma cópia do objeto" +
            " e coloca na memória para ser gerenciada pelo EntityManager...");
        entityManager.merge(produto);
//      entityManager.persist(categoria); Não necessário porque está salvando em cascata

        logger.info(">>> JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();

        logger.info(">>>  Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        logger.info(">>> Buscando Categoria atualizado no banco de dados...");
        Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
        Assert.assertTrue(categoriaVerificacao.getNome().equals("Tablets teste"));
    }


}
