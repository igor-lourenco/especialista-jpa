package com.especialista.junit._12_consultas_nativas;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.ItemPedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class _4_Mapeando_resultado_de_queries_com_SqlResultSetMapping extends EntityManagerTest {


    @Test
    public void usando_SQLResultSetMapping1(){ //  Se não especificar todas as colunas para ser retornadas, solta Exception

        String sql = "SELECT id, nome, descricao, data_criacao, data_ultima_atualizacao, preco, foto "
            + " FROM tb_produto "                    // Obs: Pra cada Produto é executada mais uma query a mais para buscar o Estoque
            + " WHERE id = :id";

        Query query = entityManager.createNativeQuery(sql, "tb_produto.Produto");
        query.setParameter("id", 1);

        List<Produto> lista = query.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(String.format("Produto => ID: %s, Nome: %s", c.getId(), c.getNome())));
    }


    @Test
    public void usando_SQLResultSetMapping2(){

        String sql = "SELECT item.*, p.* "
            + " FROM tb_item_pedido item "
            + " JOIN tb_produto p ON p.id = item.produto_id";

        Query query = entityManager.createNativeQuery(sql, "tb_item_pedido_tb_produto.ItemPedido_Produto");

        List<Object[]> lista = query.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> {
            logger.info(String.format("ItemPedido => pedidoId : %s , produtoId: %s"
                , ((ItemPedido) c[0]).getPedido().getId(), ((ItemPedido) c[0]).getProduto().getId()));

            logger.info(String.format("Produto => ID: %s, Nome: %s"
                , ((Produto) c[1]).getId(), ((Produto) c[1]).getNome()));
        });
    }
}
