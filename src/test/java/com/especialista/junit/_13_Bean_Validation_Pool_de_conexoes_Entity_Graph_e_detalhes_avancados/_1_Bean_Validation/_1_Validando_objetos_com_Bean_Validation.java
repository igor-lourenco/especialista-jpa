package com.especialista.junit._13_Bean_Validation_Pool_de_conexoes_Entity_Graph_e_detalhes_avancados._1_Bean_Validation;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

public class _1_Validando_objetos_com_Bean_Validation extends EntityManagerTest {

    @Test
    public void validarCliente() {

        try {

            entityManager.getTransaction().begin();

            Cliente cliente = new Cliente();
            entityManager.merge(cliente);

            entityManager.getTransaction().commit();

        } catch (ConstraintViolationException ex) {
            logger.error("ERROR => " + ex.getMessage());

            ex.getConstraintViolations().forEach(x ->
                logger.info("PROPRIEDADE => " + x.getPropertyPath() +  ", MENSAGEM DE ERRO => " + x.getMessage())
            );


            ex.printStackTrace();
        }
    }
}
