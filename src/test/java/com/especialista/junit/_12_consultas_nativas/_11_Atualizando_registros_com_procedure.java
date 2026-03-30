package com.especialista.junit._12_consultas_nativas;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;

public class _11_Atualizando_registros_com_procedure extends EntityManagerTest {

/*    - Procedure é um “script SQL” salvo no banco, com nome próprio, que pode receber parâmetros, executar lógica e retornar resultados.
        - Ela funciona como uma função ou método do banco.

      - Vantagens
        - Performance (menos tráfego entre aplicação e banco)
        - Reutilização de lógica
        - Centralização de regras, geralmente não é uma boa prática centralizar regras de negócio no banco de dados, mas depende do tipo de regra.
        - Segurança (expor operações sem acesso direto as tabelas)
 */

    @Test
    public void atualizandoRegistrosComProcedure(){

        entityManager.getTransaction().begin(); // Iniciando uma transação...

        StoredProcedureQuery procedureQuery = entityManager
            .createStoredProcedureQuery("ajustar_preco_produto"); // Nome da procedure

        procedureQuery.  // registrando parâmetros de entrada da procedure
            registerStoredProcedureParameter("produto_id", Integer.class, ParameterMode.IN)
            .registerStoredProcedureParameter("percentual_ajuste", BigDecimal.class, ParameterMode.IN);


        procedureQuery // registrando tipo retorno da procedure
            .registerStoredProcedureParameter("preco_ajustado", BigDecimal.class, ParameterMode.OUT);


        procedureQuery // passando o valor do parâmetro registrado acima
            .setParameter("produto_id", 1)
            .setParameter("percentual_ajuste", new BigDecimal("10.00"));


        BigDecimal retorno = // retorno da procedure
            (BigDecimal) procedureQuery.getOutputParameterValue("preco_ajustado");

        logger.info("RESULTADO => " + retorno);

        Assert.assertEquals(new BigDecimal("5489.0"), retorno);

        entityManager.getTransaction().commit(); // JPA confirmando a transação, salvando as alterações no banco de dados...

    }

}
