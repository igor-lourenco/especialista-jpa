package com.especialista.junit._10_JPQL;

import com.especialista.jpa.DTOs.PedidoComItensPedidoDTO;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.ItemPedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _6_Fazendo_inner_join_entre_as_entidades extends EntityManagerTest {

/*      - JOIN = INNER JOIN

        - Só vai trazer os Pedidos que tiverem Pagamentos associados, ou seja, retorna apenas as entidades do
      lado esquerdo com correspondência no relacionamento do join

        - JOIN com projeção é recomendado projetar campos e não entidades, assim faz um JOIN simples e o
      Hibernate traz tudo em uma única consulta, sem precisar de IDs ou selects extras.
*/

    @Test
    public void fazerjoin() {
        String jpql1 = "SELECT p FROM Pedido p JOIN p.pagamento pag";

        logger.info("Buscando uma lista de Pedido com Pagamento...");
        TypedQuery<Pedido> typedQuery1 = entityManager.createQuery(jpql1, Pedido.class);
        List<Pedido> listaPedidoComPagamento = typedQuery1.getResultList();

        Assert.assertFalse(listaPedidoComPagamento.isEmpty());

        logger.info("Lista de Pedido com Pagamento retornados...");
        listaPedidoComPagamento.forEach(dto ->
            logger.info(dto.getId() + " - " + dto.getStatus()));
    }

    @Test
    public void fazerJoinComWhere() {
        String jpql = "SELECT p FROM Pedido p JOIN p.itensPedido item WHERE item.precoProduto >= 500";

        logger.info("Buscando uma Lista de Pedido com ItemPedido...");
        TypedQuery<Pedido> typedQuery2 = entityManager.createQuery(jpql, Pedido.class);
        List<Pedido> listaPedidoComItens = typedQuery2.getResultList();

        Assert.assertFalse(listaPedidoComItens.isEmpty());

        logger.info("Tamanho da lista: " + listaPedidoComItens.size());

        logger.info("Lista de Pedido com ItemPedido retornados...");
        listaPedidoComItens.forEach(pedido -> {
            List<ItemPedido> itensPedido = pedido.getItensPedido();

            itensPedido.forEach(itens ->
                logger.info(pedido.getId() + " - " + itens.getPrecoProduto() + " - " + itens.getQuantidade())
            );
        });
    }

    @Test
    public void fazerJoinComWhereEProjecao() {
        String jpql = "SELECT new com.especialista.jpa.DTOs.PedidoComItensPedidoDTO(p, item) FROM Pedido p JOIN p.itensPedido item WHERE item.precoProduto >= 500";

        logger.info("Buscando uma Lista de Pedido com ItemPedido...");
        TypedQuery<PedidoComItensPedidoDTO> typedQuery2 = entityManager.createQuery(jpql, PedidoComItensPedidoDTO.class);
        List<PedidoComItensPedidoDTO> listaPedidoComItens = typedQuery2.getResultList();


        Assert.assertFalse(listaPedidoComItens.isEmpty());

        logger.info("Tamanho da lista: " + listaPedidoComItens.size());

        logger.info("Lista de Pedido com ItemPedido retornados...");
        listaPedidoComItens.forEach(pedidoComItem -> {
                logger.info(pedidoComItem.getPedido().getId()
                    + " - " + pedidoComItem.getItemPedido().getPrecoProduto()
                    + " - " + pedidoComItem.getItemPedido().getQuantidade());
        });
    }
}
