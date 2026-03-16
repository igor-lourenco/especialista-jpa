package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.ItemPedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class _10_Fazendo_JOIN_e_usando_FETCH extends EntityManagerTest {

/*      - FETCH = É usando no contexto do JOIN FETCH

        - Serve para carregar associações (relacionamentos) de uma entidade em uma única consulta SQL,
      evitando o problema clássico do N+1 e reduzindo o número total de queries.

        - Sem FETCH, um JOIN no JPQL pode ser usado só para filtrar/condicionar resultados,
      mas a associação continua LAZY (carregada depois, quando acessada). Com FETCH, a associação é materializada
      de imediato e marcada como carregada no Persistence Context.

        - Resumo:
            - JOIN normal → filtra/relaciona, não carrega a associação.

            - JOIN FETCH → carrega a associação junto, em um único SELECT.
*/

    @Test
    public void semUsarJoinFetch(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql1 = "SELECT p FROM Pedido p WHERE p.id = 1";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

//      quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).
        criteriaQuery.select(root);


        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1)); // WHERE p.id = 1


        TypedQuery<Pedido> typedQuery = entityManager
//          .createQuery(jpql, Pedido.class);
            .createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Buscando ItemPedido... (Faz uma nova consulta porque é carregando Preguiçoso, fetch = FetchType.LAZY)");
        Assert.assertFalse(lista.get(0).getItensPedido().isEmpty()); // Faz uma nova consulta porque é carregando Preguiçoso, fetch = FetchType.LAZY


        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> {
            logger.info(a.getId() + " - " + a.getStatus());
        });
    }



    @Test
    public void usandoJoinFetchComLista(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Pedido p JOIN FETCH p.itensPedido WHERE p.id = 1";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        root.fetch("itensPedido"); // JOIN FETCH p.itensPedido

//      quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).
        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1)); // WHERE p.id = 1

        TypedQuery<Pedido> typedQuery = entityManager
//          .createQuery(jpql, Pedido.class);
            .createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Buscando ItemPedido...(Não foi feita nova consulta porque a lista de ItemPedido já foi buscado pelo JOIN FETCH)");
        Assert.assertFalse(lista.get(0).getItensPedido().isEmpty()); // Não foi feita nova consulta porque a lista de ItemPedido já foi buscado pelo JOIN FETCH


        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> {
            logger.info(a.getId() + " - " + a.getStatus());
        });
    }



    @Test
    public void usandoJoinFetchComEntidades(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql1 = "SELECT p FROM Pedido p "
//            + " JOIN FETCH p.pagamento "
//            + " JOIN FETCH p.cliente "
//            + " LEFT JOIN FETCH p.notaFiscal " // Usando LEFT JOIN FETCH para trazer a notaFiscal com ou sem correspondência com Pedido, porque no banco o Pedido foi criado sem notaFiscal
//            + " WHERE p.id = 1";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        root.fetch("pagamento"); // JOIN FETCH p.pagamento
        root.fetch("cliente"); // JOIN FETCH p.cliente
        root.fetch("notaFiscal", JoinType.LEFT); // LEFT JOIN FETCH p.notaFiscal

//      quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).
        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1)); // WHERE p.id = 1

        TypedQuery<Pedido> typedQuery = entityManager
//          .createQuery(jpql, Pedido.class);
            .createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> {
            logger.info(a.getId() + " - " + a.getStatus());
        });
    }


    @Test
    public void usandoJoinFetchComEntidades2(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql1 = "SELECT p FROM Pedido p "
//          + " JOIN FETCH p.pagamento "        // O pedido tem que ter Pagamento, não pode ser null
//          + " JOIN FETCH p.cliente "          // O pedido tem que ter Cliente, não pode ser null
//          + " LEFT JOIN FETCH p.notaFiscal "  // Usando LEFT JOIN FETCH para trazer a notaFiscal com ou sem correspondência com Pedido, porque no banco o Pedido foi criado sem notaFiscal
//          + " JOIN FETCH p.itensPedido itens" // O pedido tem que ter ItemPedido, não pode ser null
//          + " JOIN FETCH itens.produto prod " // O ItemPedido tem que ter Produto, não pode ser null
//          + " LEFT JOIN FETCH prod.estoque "; // Usando LEFT JOIN FETCH para trazer estoque com ou sem correspondência com Produto, porque no banco o Produto foi criado sem Estoque


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        root.fetch("pagamento"); // JOIN FETCH p.pagamento
        root.fetch("cliente"); // JOIN FETCH p.cliente
        root.fetch("notaFiscal", JoinType.LEFT); // LEFT JOIN FETCH p.notaFiscal

        Join<Pedido, ItemPedido> joinItensPedido =
            (Join<Pedido, ItemPedido>) root.<Pedido, ItemPedido>fetch("itensPedido"); // JOIN FETCH p.itensPedido itens

        Join<ItemPedido, Produto> joinProduto =
            (Join<ItemPedido, Produto>) joinItensPedido.<ItemPedido, Produto>fetch("produto"); // JOIN FETCH itens.produto prod

        joinProduto.fetch("estoque", JoinType.LEFT); // LEFT JOIN FETCH prod.estoque


//      quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).
        criteriaQuery.select(root);


        TypedQuery<Pedido> typedQuery = entityManager
//          .createQuery(jpql, Pedido.class);
            .createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Buscando ItemPedido...(Não foi feita nova consulta porque a lista de ItemPedido já foi buscado pelo JOIN FETCH)");
        Assert.assertFalse(lista.get(0).getItensPedido().isEmpty()); // Não foi feita nova consulta porque a lista de ItemPedido já foi buscado pelo JOIN FETCH


        logger.info("Resultado: " + lista.size());
        lista.forEach(pedido -> {

            StringBuilder itens = new StringBuilder();

            itens.append("[");
            pedido.getItensPedido().forEach(item -> {
                itens.append("(precoProduto=" + item.getPrecoProduto());
                itens.append(", quantidade=" + item.getQuantidade() + ")");
            });
            itens.append("]");

            StringBuilder produtos = new StringBuilder();

            produtos.append("[");
            pedido.getItensPedido().forEach(item -> {
                produtos.append("(produtoId="+ item.getProduto().getId() + ")");
            });
            produtos.append("]");

            logger.info(
                "pedidoId: " + pedido.getId()
                + ", statusPagamento: " + pedido.getPagamento().getStatus()
                + ", nomeCliente: " + pedido.getCliente().getNome()
                + ", dataEmissaoNotaFiscal: " + (pedido.getNotaFiscal() == null ? "null" : pedido.getNotaFiscal().getDataEmissao())
                + ", itensPedido: " + itens
                + ", produtos: " + produtos


            );
        });
    }

}
