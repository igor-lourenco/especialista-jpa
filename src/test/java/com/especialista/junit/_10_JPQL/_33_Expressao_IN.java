package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _33_Expressao_IN extends EntityManagerTest {


/*  - IN → Compara um único valor (campo, função, ou expressão) com um conjunto de valores (lista literal, parâmetro de coleção ou subconsulta).
        - É ideal quando quer filtrar por múltiplos valores de forma concisa.
*/


    @Test
    public void usandoExpresao_IN() { //

        String jpql = "SELECT p"
            + " FROM Pedido p "
            + " JOIN FETCH p.itensPedido item"
            + " WHERE p.id IN (1, 3, 4) ";


        logger.info("Buscando Pedido...");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info(c.toString()));
    }

    @Test
    public void usandoExpresao_NOT_IN() { //

        String jpql = "SELECT p"
            + " FROM Pedido p "
            + " LEFT JOIN FETCH p.itensPedido item"
            + " WHERE p.cliente NOT IN (:clientes) ";

        Cliente cliente2 = entityManager.find(Cliente.class, 2);
        Cliente cliente3 = new Cliente(); // não precisa ter toda a entidade com os dados, apenas o id funciona
        cliente3.setId(1);

        List<Cliente> parametros = List.of(cliente2,cliente3);

        logger.info("Buscando Pedido...");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class)
            .setParameter("clientes", parametros);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info(c.toString()));
    }

    @Test
    public void usandoExpresao_IN_comParametros1() { //

        String jpql = "SELECT p"
            + " FROM Pedido p "
            + " LEFT JOIN FETCH p.itensPedido item"
            + " WHERE p.cliente IN (:clientes) ";

        Cliente cliente2 = entityManager.find(Cliente.class, 2);
        Cliente cliente3 = new Cliente(); // não precisa ter toda a entidade com os dados, apenas o id funciona também
        cliente3.setId(3);

        List<Cliente> parametros = List.of(cliente2,cliente3);

        logger.info("Buscando Pedido...");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class)
            .setParameter("clientes", parametros);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info(c.toString()));
    }

    @Test
    public void usandoExpresao_IN_comParametros2() { //

        String jpql = "SELECT p"
            + " FROM Pedido p "
            + " JOIN FETCH p.itensPedido item"
            + " WHERE p.id IN (:lista) ";

        List<Integer> parametros = List.of(1,3, 4);

        logger.info("Buscando Pedido...");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class)
            .setParameter("lista", parametros);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info(c.toString()));
    }


}
