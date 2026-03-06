package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa.DTOs.PedidoComItensPedidoDTO;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.ItemPedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pagamento;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class _7_Fazendo_INNER_JOIN_entre_as_entidades extends EntityManagerTest {

/*      - JOIN = INNER JOIN

        - Só vai trazer os Pedidos que tiverem Pagamentos associados, ou seja, retorna apenas as entidades do
      lado esquerdo com correspondência no relacionamento do join

        - JOIN com projeção é recomendado projetar campos e não entidades, assim faz um JOIN simples e o
      Hibernate traz tudo em uma única consulta, sem precisar de IDs ou selects extras.

        - O JOIN nunca retorna registros cujo lado associado é nulo. Só retorna linhas onde a relação existe dos dois lados.
*/
    @Test
    public void fazendo_JOIN1(){

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Pedido p JOIN p.pagamento pag"; // Selecionando Pedido

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // SELECT p

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento"); // JOIN p.pagamento pag (obs: mesmo não usando a variável o JPA faz o JOIN)

//      quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).
        criteriaQuery.select(root);


        TypedQuery<Pedido> typedQuery = entityManager
//          .createQuery(jpql, Pedido.class);
            .createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertNotNull(lista);

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> logger.info("ID: " + a.getId() + ", Pagamento: " + a.getPagamento()));
    }

    @Test
    public void fazendo_JOIN2(){

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p.pagamento FROM Pedido p JOIN p.pagamento pag"; // Selecionando Pagamento através do Pedido

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pagamento> criteriaQuery = criteriaBuilder.createQuery(Pagamento.class); // SELECT p.pagamento

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento"); // JOIN p.pagamento pag

//      quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).
        criteriaQuery.select(joinPagamento);


        TypedQuery<Pagamento> typedQuery = entityManager
//          .createQuery(jpql, Pagamento.class);
            .createQuery(criteriaQuery);

        List<Pagamento> lista = typedQuery.getResultList();

        Assert.assertNotNull(lista);

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> logger.info(a.toString()));
    }


    @Test
    public void fazendo_JOIN_com_WHERE(){

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Pedido p JOIN p.itensPedido item WHERE item.precoProduto >= 500";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // SELECT p

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        Join<Pedido, ItemPedido> joinItemPedido = root.join("itensPedido"); // JOIN p.itensPedido item

        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).

        criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(joinItemPedido.get("precoProduto"), 500)); // WHERE item.precoProduto >= 500


        TypedQuery<Pedido> typedQuery = entityManager
//          .createQuery(jpql, Pedido.class);
            .createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertNotNull(lista);

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> logger.info(a.toString()));
    }


    @Test
    public void fazendo_JOIN_com_WHERE_e_Projecao(){

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT new com.especialista.jpa.DTOs.PedidoComItensPedidoDTO(p, item) FROM Pedido p JOIN p.itensPedido item WHERE item.precoProduto >= 500";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PedidoComItensPedidoDTO> criteriaQuery = criteriaBuilder.createQuery(PedidoComItensPedidoDTO.class); // SELECT new com.especialista.jpa.DTOs.PedidoComItensPedidoDTO(p, item)

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        Join<Pedido, ItemPedido> joinItemPedido = root.join("itensPedido"); // JOIN p.itensPedido item

        criteriaQuery.select( criteriaBuilder.construct(PedidoComItensPedidoDTO.class, // classe DTO
            root, joinItemPedido)); // atributos p, item de Pedido para ser passados no construtor do DTO

        criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(joinItemPedido.get("precoProduto"), 500)); // WHERE item.precoProduto >= 500


        TypedQuery<PedidoComItensPedidoDTO> typedQuery = entityManager
//          .createQuery(jpql, PedidoComItensPedidoDTO.class);
            .createQuery(criteriaQuery);

        List<PedidoComItensPedidoDTO> lista = typedQuery.getResultList();

        Assert.assertNotNull(lista);

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> logger.info("pedidoId: " + a.getPedido().getId()));
    }


}
