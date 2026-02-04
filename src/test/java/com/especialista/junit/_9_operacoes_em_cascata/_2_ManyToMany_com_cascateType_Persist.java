package com.especialista.junit._9_operacoes_em_cascata;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Categoria;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class _2_ManyToMany_com_cascateType_Persist extends EntityManagerTest {

    @Test
    public void persistirPedidoComItemPedido(){
        logger.info(">>> Instanciando novo Produto");
        Produto produto = new Produto();
        produto.setDataCriacao(LocalDateTime.now());
        produto.setPreco(BigDecimal.TEN);
        produto.setNome("Fone de Ouvido");
        produto.setDescricao("A melhor qualidade de som");

        logger.info(">>> Instanciando nova Categoria");
        Categoria categoria = new Categoria();
        categoria.setNome("Áudio");

        logger.info(">>> Adicionando lista de Categorias ao Produto");
        // Tem que ter configurado o CascadeType.PERSIST para salvar categoria em cascada quando salvar o pedido
        produto.setCategorias(Arrays.asList(categoria));

        logger.info(">>> Iniciando uma transação...");
        entityManager.getTransaction().begin();

        logger.info(">>> Colocando uma novo Produto no contexto de persistência usando o persist()...");
        entityManager.persist(produto);
//      entityManager.persist(categoria); Não necessário porque está salvando em cascata

        logger.info(">>> JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();

        logger.info(">>>  Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        logger.info(">>> Buscando Categoria criado no banco de dados...");
        Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
        Assert.assertNotNull(categoriaVerificacao);
    }
}
