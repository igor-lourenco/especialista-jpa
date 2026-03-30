package com.especialista.junit._12_consultas_nativas;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

public class _9_Invocando_Stored_Procedures_com_parametros_IN_e_OUT extends EntityManagerTest {

/*    - Procedure é um “script SQL” salvo no banco, com nome próprio, que pode receber parâmetros, executar lógica e retornar resultados.
        - Ela funciona como uma função ou método do banco.

      - Vantagens
        - Performance (menos tráfego entre aplicação e banco)
        - Reutilização de lógica
        - Centralização de regras, geralmente não é uma boa prática centralizar regras de negócio no banco de dados, mas depende do tipo de regra.
        - Segurança (expor operações sem acesso direto as tabelas)
 */

    @Test
    public void usandoParametros_IN_e_OUT(){

        StoredProcedureQuery procedureQuery = entityManager
            .createStoredProcedureQuery("buscar_nome_produto"); // Nome da procedure

        procedureQuery.  // registrando parâmetro de entrada da procedure
            registerStoredProcedureParameter("produto_id", Integer.class, ParameterMode.IN);


        procedureQuery // registrando tipo retorno da procedure
            .registerStoredProcedureParameter("produto_nome", String.class, ParameterMode.OUT);


        procedureQuery.setParameter("produto_id", 1); // passando o valor do parâmetro registrado acima


        String retorno = (String) procedureQuery.getOutputParameterValue("produto_nome"); // retorno da procedure

        Assert.assertEquals("Kindle teste", retorno);

        logger.info("RETORNO DA PROCEDURE => NOME DO PRODUTO: " + retorno);
    }

}
