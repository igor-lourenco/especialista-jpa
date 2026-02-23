package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _26_Funcoes_Nativas extends EntityManagerTest {


    @Test
    public void usandoFuncoesNativas1() {

//     create function acima_media_faturamento(valor double) returns boolean reads sql data return valor > (select avg(total) from tb_pedido);

        String jpql = "SELECT p "
            + " FROM Pedido p "
            + " JOIN FETCH p.itensPedido "
            + " WHERE FUNCTION('acima_media_faturamento', p.total) = 1"; // 1 parametro é o nome da function, e o outros parametros são os parametros que a function recebe


        logger.info("Buscando Pedidos");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> pedidos = typedQuery.getResultList();

        Assert.assertFalse(pedidos.isEmpty());

        logger.info("Pedidos com total acima da média...");
        pedidos.forEach(c -> logger.info(": " + c));
    }

    @Test
    public void usandoFuncoesNativas2() {

//     create function acima_media_faturamento(valor double) returns boolean reads sql data return valor > (select avg(total) from tb_pedido);

        String jpql = "SELECT FUNCTION('dayname', p.dataCriacao) " // function que já existe no banco de dados
            + " FROM Pedido p "
            + " WHERE FUNCTION('acima_media_faturamento', p.total) = 1"; // 1 parametro é o nome da function, e o outros parametros são os parametros que a function recebe


        logger.info("Buscando Pedidos");
        TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);

        List<String> pedidos = typedQuery.getResultList();

        Assert.assertFalse(pedidos.isEmpty());

        logger.info("Data de criação de Pedidos com total acima da média...");
        pedidos.forEach(c -> logger.info("Dia da semana: " + c));
    }


}
