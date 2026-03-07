package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa.DTOs.PedidoComPagamentoDTO;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pagamento;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.StatusPagamento;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class _8_JOIN_com_a_clausula_ON extends EntityManagerTest {

/*      - Diferença entre WHERE e ON:
            - ON → a condição é aplicada no momento do join, limitando quais linhas do lado direito podem casar com a linha do lado esquerdo.
          Preserva todas as linhas do lado esquerdo, mesmo quando não houver match (o lado direito vira NULL).

            - WHERE → a condição é aplicada depois do join. Se você filtra por uma coluna do lado direito (ex.: p.status = 'CANCELADO'),
          as linhas onde o lado direito é NULL serão removidas, o que na prática transforma o LEFT JOIN em INNER JOIN.
 */

    @Test
    public void fazendo_JOIN_com_ON(){

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p, pag FROM Pedido p LEFT JOIN p.pagamento pag ON pag.status = 'PROCESSANDO'";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery(); // SELECT p, pag

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento"); // JOIN p.pagamento pag (obs: mesmo não usando a variável o JPA faz o JOIN)


        criteriaQuery.select(criteriaBuilder.tuple(
            root.alias("pedido"), // atributo p do jpql
            joinPagamento.alias("pagamento"))); // atributo pag do jpql


        joinPagamento.on(criteriaBuilder.equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO)); // ON pag.status = 'PROCESSANDO'

        TypedQuery<Tuple> typedQuery = entityManager
//          .createQuery(jpql, Object[].class);
            .createQuery(criteriaQuery);

        List<Tuple> lista = typedQuery.getResultList();

        Assert.assertNotNull(lista);

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> logger.info(
            "ID: " + a.get("pedido", Pedido.class).getId() +
            ", Pagamento: " + a.get("pagamento", Pagamento.class).getStatus()));
    }

    @Test
    public void fazendo_JOIN_com_ON_e_projecao(){

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p, pag FROM Pedido p JOIN p.pagamento pag ON pag.status = 'PROCESSANDO'";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery(); // SELECT p, pag

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento"); // JOIN p.pagamento pag (obs: mesmo não usando a variável o JPA faz o JOIN)


        criteriaQuery.select(criteriaBuilder.tuple(
            root.alias("pedido"),  // atributo p do jpql
            joinPagamento.alias("pagamento"))); // atributo pag do jpql


        joinPagamento.on(criteriaBuilder.equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO)); // ON pag.status = 'PROCESSANDO'

        TypedQuery<Tuple> typedQuery = entityManager
//          .createQuery(jpql, Object[].class);
            .createQuery(criteriaQuery);

        List<Tuple> lista = typedQuery.getResultList();

        Assert.assertNotNull(lista);

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> {
            PedidoComPagamentoDTO dto = new PedidoComPagamentoDTO(
                a.get("pedido", Pedido.class), a.get("pagamento", Pagamento.class));

            logger.info(
                "ID: " + dto.getPedido().getId() +
                ", Pagamento: " + dto.getPagamento());
        });
    }


}
