package com.especialista.junit._12_consultas_nativas;

import com.especialista.jpa.DTOs.ProdutoDTO;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class _5_Usando_SqlResultSetMapping_com_ColumnResult_e_retornando_DTO extends EntityManagerTest {


    @Test
    public void usando_SqlResultSetMapping_com_ColumnResult_e_retornando_DTO(){

        String sql = "SELECT * "
            + " FROM tb_produto ";

        Query query = entityManager.createNativeQuery(sql, "tb_produto.ProdutoDTO");

        List<ProdutoDTO> lista = query.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(String.format("ProdutoDTO => ID: %s, Nome: %s", c.getId(), c.getNome())));
    }

}
