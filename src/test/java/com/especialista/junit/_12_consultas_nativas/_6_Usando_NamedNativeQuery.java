package com.especialista.junit._12_consultas_nativas;

import com.especialista.jpa.DTOs.ProdutoDTO;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class _6_Usando_NamedNativeQuery extends EntityManagerTest {


    @Test
    public void usando_NamedNativeQuery1(){

        Query query = entityManager.createNamedQuery("tb_produto.listarTodos");

        List<Produto> lista = query.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(String.format("Produto => ID: %s, Nome: %s", c.getId(), c.getNome())));
    }

    @Test
    public void usando_NamedNativeQuery2(){

        Query query = entityManager.createNamedQuery("tb_produto.listarTodosDTO");

        List<ProdutoDTO> lista = query.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(String.format("ProdutoDTO => ID: %s, Nome: %s", c.getId(), c.getNome())));
    }

}
