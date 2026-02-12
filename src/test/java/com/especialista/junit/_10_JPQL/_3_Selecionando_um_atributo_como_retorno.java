package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _3_Selecionando_um_atributo_como_retorno extends EntityManagerTest {

    @Test
    public void selecionandoUmAtributoParaRetorno() {

        String jpql = "select p.nome from Produto p"; // retorna uma lista de nomes dos Produtos

        logger.info("Buscando Lista de nomes dos Produtos...");
        TypedQuery<String> nomesTypedQuery = entityManager.createQuery(jpql, String.class);
        List<String> nomesDoProduto = nomesTypedQuery.getResultList();

        Assert.assertEquals(String.class, nomesDoProduto.get(0).getClass());

        logger.info("Lista de nomes dos Produtos retornados");
        nomesDoProduto.forEach(p -> logger.info(p));

//      -------------------------------------------------

        String clientesJpql = "select p.cliente from Pedido p"; // retorna uma lista de Clientes que tem Pedidos associado

        logger.info("Buscando Lista de Clientes que tem Pedidos usando TypedQuery...");
        TypedQuery<Cliente> clientesTypedQuery = entityManager.createQuery(clientesJpql, Cliente.class);
        List<Cliente> clientesComPedidos = clientesTypedQuery.getResultList();

        Assert.assertEquals(Cliente.class, clientesComPedidos.get(0).getClass());

        logger.info("Lista de Clientes retornados");
        clientesComPedidos.forEach(p -> logger.info(p.getNome()));
    }
}
