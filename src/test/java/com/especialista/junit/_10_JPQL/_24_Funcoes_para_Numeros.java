package com.especialista.junit._10_JPQL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _24_Funcoes_para_Numeros extends EntityManagerTest {


    @Test
    public void usando_FuncaoParaNumero1() {

        String jpql = "SELECT ABS(-10), MOD(3, 2), SQRT(9) "
            + " FROM Categoria c "
            + " WHERE c.id = 1";


        logger.info("Buscando Categorias...");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class)
            .setMaxResults(10);

        List<Object[]> categorias = typedQuery.getResultList();

        Assert.assertFalse(categorias.isEmpty());

        categorias.forEach(c -> logger.info("RETORNA VALOR ABSOLUTO ABS(-10): " + c[0]));
        categorias.forEach(c -> logger.info("RETORNA A SOBRA DA DIVISÃO DE DOIS NÚMEROS MOD(3, 2):  " + c[1]));
        categorias.forEach(c -> logger.info("RETORNA A RAIZ QUADRADA DO NUMERO SQRT(9): " + c[2]));
    }

    @Test
    public void usando_FuncaoParaNumero3() {

        String jpql = "SELECT ABS(p.total), MOD(p.total, 7), SQRT(p.total), p.total "
            + " FROM Pedido p "
            + " WHERE ABS(p.total) > 1000"
            + " AND p.id = 1";


        logger.info("Buscando Categorias...");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class)
            .setMaxResults(10);

        List<Object[]> pedidos = typedQuery.getResultList();

        Assert.assertFalse(pedidos.isEmpty());

        pedidos.forEach(c -> logger.info("RETORNA VALOR ABSOLUTO ABS(" + c[3] + "): " + c[0]));
        pedidos.forEach(c -> logger.info("RETORNA A SOBRA DA DIVISÃO DE DOIS NÚMEROS MOD(" + c[3] + ", 7):  " + c[1]));
        pedidos.forEach(c -> logger.info("RETORNA A RAIZ QUADRADA DO NUMERO SQRT(" + c[3] + "): " + c[2]));
    }


}
