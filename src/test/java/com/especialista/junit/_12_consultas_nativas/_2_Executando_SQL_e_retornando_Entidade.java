package com.especialista.junit._12_consultas_nativas;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class _2_Executando_SQL_e_retornando_Entidade extends EntityManagerTest {

    @Test
    public void executandoSQL(){

        String sql = "SELECT * FROM tb_produto";  // Obs: Pra cada Produto é executada mais uma query a mais para buscar o Estoque

        Query query = entityManager.createNativeQuery(sql, Produto.class);

        List<Produto> lista = query.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(String.format("Produto => ID: %s, Nome: %s", c.getId(), c.getNome())));
    }

    @Test
    public void executandoSQL_especificandoAsColunas(){ //  Se não especificar todas as colunas para ser retornadas, solta Exception

        String sql = "SELECT id, nome, descricao, data_criacao, data_ultima_atualizacao, preco, foto "
            + " FROM tb_produto";                    // Obs: Pra cada Produto é executada mais uma query a mais para buscar o Estoque

        Query query = entityManager.createNativeQuery(sql, Produto.class);

        List<Produto> lista = query.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(String.format("Produto => ID: %s, Nome: %s", c.getId(), c.getNome())));
    }
}
