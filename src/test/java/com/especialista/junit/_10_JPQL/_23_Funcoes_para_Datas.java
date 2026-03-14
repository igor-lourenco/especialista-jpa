package com.especialista.junit._10_JPQL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _23_Funcoes_para_Datas extends EntityManagerTest {



    @Test
    public void usando_CURRENT_DATE_CURRENT_TIME_CURRENT_TIMESTAMP() {

        fusoHorario();

        String jpql = "SELECT CURRENT_DATE, CURRENT_TIME, CURRENT_TIMESTAMP "
            + " FROM Categoria c "
            + " WHERE c.id = 1";


        logger.info("Buscando Categorias...");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class)
            .setMaxResults(10);

        List<Object[]> categorias = typedQuery.getResultList();

        Assert.assertFalse(categorias.isEmpty());

        categorias.forEach(c -> logger.info("Data atual (America/Sao_Paulo): " + c[0]
            + ", como o sistema está no fuso horário de America_Sao_Paulo, e a data não tem as horas, O Java tira as 3 horas e por isso fica como um dia a menos "));

        categorias.forEach(c -> logger.info("Hora atual:  " + c[1]));
        categorias.forEach(c -> logger.info("Data e hora atual (America/Sao_Paulo): " + c[2]));
    }


    @Test
    public void usando_naClausulaWHERE() {

        fusoHorario();

        String jpql = "SELECT p.dataCriacao, CURRENT_TIMESTAMP "
            + " FROM Pedido p "
            + " WHERE p.dataCriacao < CURRENT_TIMESTAMP "
            + " AND p.id = 1";


        logger.info("Buscando Pedido ...");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class)
            .setMaxResults(10);

        List<Object[]> categorias = typedQuery.getResultList();

        Assert.assertFalse(categorias.isEmpty());

        logger.info("Buscando Pedido");
        categorias.forEach(c -> logger.info("dataCriacao (UTC): " + c[0] + " | dataAtual (America/Sao_Paulo): " + c[1]));
    }


    @Test
    public void pegandoANoMesEDiaDaData() { // Pegando o DIA, MES e ANO da dataCriacao...
        fusoHorario();

        String jpql = "SELECT YEAR(p.dataCriacao), MONTH(p.dataCriacao), DAY(p.dataCriacao) "
            + " FROM Pedido p "
            + " WHERE p.dataCriacao < CURRENT_TIMESTAMP "
            + " AND p.id = 1";


        logger.info("Buscando Pedido ...");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class)
            .setMaxResults(10);

        List<Object[]> pedidos = typedQuery.getResultList();

        Assert.assertFalse(pedidos.isEmpty());

        logger.info("Pegando o DIA, MES e ANO da dataCriacao...");
        pedidos.forEach(c -> logger.info("DIA: " + c[2] + " | MES: : " + c[1] + " | ANO: " + c[0]));
    }



    @Test
    public void pegandoHoraMinutoESegundo() { // Pegando o HORA, MINUTO e SEGUNDO da dataCriacao...
        fusoHorario();

        String jpql = "SELECT HOUR(p.dataCriacao), MINUTE(p.dataCriacao), SECOND(p.dataCriacao) "
            + " FROM Pedido p "
            + " WHERE p.dataCriacao < CURRENT_TIMESTAMP "
            + " AND p.id = 1";


        logger.info("Buscando Pedido ...");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class)
            .setMaxResults(10);

        List<Object[]> pedidos = typedQuery.getResultList();

        Assert.assertFalse(pedidos.isEmpty());

        logger.info("Pegando o HORA, MINUTO e SEGUNDO da dataCriacao...");
        pedidos.forEach(c -> logger.info("HORA: " + c[0] + " | MINUTO: : " + c[1] + " | SEGUNDO: " + c[2]));
    }

}
