package com.especialista.junit._12_consultas_nativas;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class _12_Configurando_procedure_com_anotacao_NamedStoredProcedureQuery extends EntityManagerTest {

/*    - Procedure é um “script SQL” salvo no banco, com nome próprio, que pode receber parâmetros, executar lógica e retornar resultados.
        - Ela funciona como uma função ou método do banco.

      - Vantagens
        - Performance (menos tráfego entre aplicação e banco)
        - Reutilização de lógica
        - Centralização de regras, geralmente não é uma boa prática centralizar regras de negócio no banco de dados, mas depende do tipo de regra.
        - Segurança (expor operações sem acesso direto as tabelas)
 */

    @Test
    public void chamando_NamedStoredProcedureQuery(){

        StoredProcedureQuery procedureQuery = entityManager
            .createNamedStoredProcedureQuery("procedure_compraram_acima_media"); // Nome de referencia da procedure

        procedureQuery.setParameter("ano", 2025); // passando o valor do parâmetro registrado acima


        List<Cliente> lista = procedureQuery.getResultList(); // retorno da procedure

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId() + ", Nome: " + c.getNome()));

    }

}
