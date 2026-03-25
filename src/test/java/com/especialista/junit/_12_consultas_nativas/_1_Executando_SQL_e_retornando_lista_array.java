package com.especialista.junit._12_consultas_nativas;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class _1_Executando_SQL_e_retornando_lista_array extends EntityManagerTest {

    @Test
    public void executandoSQL(){

        String sql = "SELECT * FROM tb_produto";

        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> lista = query.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(String.format("Produto => ID: %s, Nome: %s", c[0], c[5])));
    }

    @Test
    public void executandoSQL_especificandoAsColunas(){

        String sql = "SELECT id, nome FROM tb_produto";

        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> lista = query.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(String.format("Produto => ID: %s, Nome: %s", c[0], c[1])));
    }
}
