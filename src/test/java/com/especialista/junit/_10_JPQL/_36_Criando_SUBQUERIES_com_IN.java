package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _36_Criando_SUBQUERIES_com_IN extends EntityManagerTest {


    @Test
    public void usandoExpresao_IN() { //

//        String jpql = "SELECT p"  -> Pode trazer pedido duplicado se tiver mais um item de pedido acima de 100
//            + " FROM Pedido p "
//            + " JOIN FETCH p.itensPedido item"
//            + " JOIN item.produto prod "
//            + " WHERE prod.preco > 100 ";

        String jpql = "SELECT ped"
            + " FROM Pedido ped "
            + " WHERE ped.id IN ( "
            + "     SELECT ped2.id "
            + "       FROM ItemPedido item"
            + "       JOIN item.pedido ped2 "
            + "       JOIN item.produto pro "
            + "       WHERE pro.preco > 100 "
            + ")";

        logger.info("Buscando Pedido...");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }

}
