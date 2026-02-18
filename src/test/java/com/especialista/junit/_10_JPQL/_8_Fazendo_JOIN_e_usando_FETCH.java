package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _8_Fazendo_JOIN_e_usando_FETCH extends EntityManagerTest {

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
    public void semUsarJoinFetch() {
        String jpql1 = "SELECT p FROM Pedido p WHERE p.id = 1";

        logger.info("Buscando Pedido ...");
        TypedQuery<Pedido> typedQuery1 = entityManager.createQuery(jpql1, Pedido.class);
        Pedido listaPedido = typedQuery1.getSingleResult();


        logger.info("Buscando ItemPedido...");
        Assert.assertFalse(listaPedido.getItensPedido().isEmpty()); // Faz uma nova consulta porque é carregando Preguiçoso, fetch = FetchType.LAZY

        logger.info("Pedido retornado...");
            logger.info(listaPedido.getId() + " - " + listaPedido.getStatus());
    }

    @Test
    public void usandoJoinFetchComLista() {
        String jpql1 = "SELECT p FROM Pedido p JOIN FETCH p.itensPedido WHERE p.id = 1";

        logger.info("Buscando Pedido ...");
        TypedQuery<Pedido> typedQuery1 = entityManager.createQuery(jpql1, Pedido.class);
        Pedido listaPedido = typedQuery1.getSingleResult();


        logger.info("Lista de ItemPedido já foi carregado...");
        Assert.assertFalse(listaPedido.getItensPedido().isEmpty()); // Não foi feita nova consulta porque a lista de ItemPedido já foi buscado pelo JOIN FETCH

        logger.info("Pedido retornado...");
        logger.info(listaPedido.getId() + " - " + listaPedido.getStatus());
    }

    @Test
    public void usandoJoinFetchComEntidades() {
        String jpql1 = "SELECT p FROM Pedido p "
            + " JOIN FETCH p.pagamento "
            + " JOIN FETCH p.cliente "
            + " LEFT JOIN FETCH p.notaFiscal " // Usando LEFT JOIN FETCH para trazer a notaFiscal com ou sem correspondência com Pedido, porque no banco o Pedido foi criado sem notaFiscal
            + " WHERE p.id = 1";

        logger.info("Buscando Pedido ...");
        TypedQuery<Pedido> typedQuery1 = entityManager.createQuery(jpql1, Pedido.class);
        Pedido listaPedido = typedQuery1.getSingleResult();

        logger.info("Pedido retornado...");
        logger.info(listaPedido.getId() + " - " + listaPedido.getStatus());
    }


    @Test
    public void usandoJoinFetchComEntidadesELista() {
        String jpql1 = "SELECT p FROM Pedido p "
            + " JOIN FETCH p.pagamento "        // O pedido tem que ter Pagamento, não pode ser null
            + " JOIN FETCH p.cliente "          // O pedido tem que ter Cliente, não pode ser null
            + " LEFT JOIN FETCH p.notaFiscal "  // Usando LEFT JOIN FETCH para trazer a notaFiscal com ou sem correspondência com Pedido, porque no banco o Pedido foi criado sem notaFiscal
            + " JOIN FETCH p.itensPedido itens" // O pedido tem que ter ItemPedido, não pode ser null
            + " JOIN FETCH itens.produto prod " // O ItemPedido tem que ter Produto, não pode ser null
            + " LEFT JOIN FETCH prod.estoque "; // Usando LEFT JOIN FETCH para trazer estoque com ou sem correspondência com Produto, porque no banco o Produto foi criado sem Estoque

        logger.info("Buscando Pedido ...");
        TypedQuery<Pedido> typedQuery1 = entityManager.createQuery(jpql1, Pedido.class);
        List<Pedido> listaPedido = typedQuery1.getResultList();


        logger.info("Lista de ItemPedido já foi carregado...");
        Assert.assertFalse(listaPedido.get(0).getItensPedido().isEmpty()); // Não foi feita nova consulta porque a lista de ItemPedido já foi buscado pelo JOIN FETCH

        logger.info("Pedido retornado...");
        listaPedido.forEach(pedido -> {

        logger.info(pedido.getId() + " - " + pedido.getStatus());
        });
    }
}
