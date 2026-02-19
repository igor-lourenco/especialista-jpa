package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;

public class _10_Exercicio_Buscando_Pedidos_com_Produto_especifico extends EntityManagerTest {


    @Test
    public void buscarPedidoComProdutoEspecifico() {
        String jpql1 = "SELECT p FROM Pedido p "
            + " JOIN FETCH p.itensPedido itens " // JOIN para que o ItemPedido não seja null e FETCH para carregar os itemPedido na mesma query
            + " JOIN FETCH itens.produto prod " // JOIN para que o Produto não seja null e FETCH para carregar os produtos na mesma query
            + " WHERE prod.id = 2";

        logger.info("Buscando Nome dos Clientes com Pedido...");
        TypedQuery<Pedido> typedQuery1 = entityManager.createQuery(jpql1, Pedido.class);
        Pedido pedido = typedQuery1.getSingleResult();


        Assert.assertNotNull(pedido);

        logger.info("Pedido carregado...");
        logger.info(pedido.toString());
    }
}
