package com.especialista.junit._13_Bean_Validation_Pool_de_conexoes_Entity_Graph_e_detalhes_avancados._4_One_To_One_como_LAZY;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class _1_Problema_do_One_To_One_como_LAZY_no_Hibernate extends EntityManagerTest {

    @Test
    public void OneToOne_com_LAZY(){

        logger.info("BUSCANDO UM PEDIDO:");
        Pedido pedido = entityManager.find(Pedido.class, 1);

        Assert.assertNotNull(pedido);

        logger.info("------------------------------------------------------------");
        logger.info("BUSCANDO UMA LISTA DE PEDIDO:");

        List<Pedido> lista = entityManager
            .createQuery("SELECT p FROM Pedido p", Pedido.class)
            .getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("------------------------------------------------------------");
        logger.info("BUSCANDO UMA LISTA DE PEDIDO COM NOTAFISCAL:");

        List<Pedido> lista2 = entityManager
            .createQuery("SELECT p FROM Pedido p JOIN FETCH p.notaFiscal", Pedido.class)
            .getResultList();

        Assert.assertFalse(lista2.isEmpty());

    }

}
