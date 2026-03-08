package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.ItemPedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class _11_Exercicio_Buscando_Pedidos_com_Produto_especifico extends EntityManagerTest {

    @Test
    public void buscarPedidoComProdutoEspecifico(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql1 = "SELECT p FROM Pedido p "
//          + " JOIN FETCH p.itensPedido itens " // JOIN para que o ItemPedido não seja null e FETCH para carregar os itemPedido na mesma query
//          + " JOIN FETCH itens.produto prod " // JOIN para que o Produto não seja null e FETCH para carregar os produtos na mesma query
//          + " WHERE prod.id = 2";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // SELECT p

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        Join<Pedido, ItemPedido> joinItensPedido =
            (Join<Pedido, ItemPedido>) root.<Pedido, ItemPedido>fetch("itensPedido"); // JOIN FETCH p.itensPedido itens


        Join<ItemPedido, Produto> joinProduto =
            (Join<ItemPedido, Produto>) joinItensPedido.<ItemPedido, Produto>fetch("produto"); // JOIN FETCH itens.produto prod


        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).


        criteriaQuery.where(criteriaBuilder.equal(joinProduto.get("id"), 2)); // WHERE prod.id = 2


        TypedQuery<Pedido> typedQuery = entityManager
//          .createQuery(jpql, Pedido.class);
            .createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Buscando ItemPedido...(Não foi feita nova consulta porque a lista de ItemPedido já foi buscado pelo JOIN FETCH)");
        Assert.assertFalse(lista.get(0).getItensPedido().isEmpty()); // Não foi feita nova consulta porque a lista de ItemPedido já foi buscado pelo JOIN FETCH


        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> {
            logger.info(a.toString());
        });
    }



}
