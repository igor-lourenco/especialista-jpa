package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pagamento;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.StatusPagamento;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class _9_Usando_LEFT_OUUTER_JOIN extends EntityManagerTest {

/*      - LEFT JOIN = LEFT OUTER JOIN

        - Diferente do JOIN(INNER JOIN) que só vai trazer os Pedidos que tiverem Pagamentos associados, o LEFT JOIN retorna todas as
      entidades do lado esquerdo, com ou sem correspondência. Quando não há correspondência, os atributos do lado direito vêm como NULL

        - LEFT JOIN permite trazer também os Pagamentos quando houver e ainda manter os Pedidos sem Pagamentos, com pag = NULL na linha
      (ou coleção vazia no caso de LEFT JOIN FETCH).

        - LEFT JOIN com projeção é recomendado projetar campos e não entidades, assim faz um JOIN simples e o
      Hibernate traz tudo em uma única consulta, sem precisar de IDs ou selects extras.

*/

    @Test
    public void fazerLeftJoinComONEProjecao(){

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p, pag FROM Pedido p LEFT JOIN p.pagamento pag ON pag.status = 'PROCESSANDO'";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);// Query vai retornar Object[]

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento", JoinType.LEFT); // LEFT JOIN p.pagamento pag (obs: mesmo não usando a variável o JPA faz o JOIN)


        criteriaQuery.multiselect(root, joinPagamento); // atributos p e pag do jpql


        joinPagamento.on(criteriaBuilder.equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO)); // ON pag.status = 'PROCESSANDO'

        TypedQuery<Object[]> typedQuery = entityManager
//          .createQuery(jpql, Object[].class);
            .createQuery(criteriaQuery);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertNotNull(lista);

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> {
            Pedido pedido = (Pedido) a[0];
            Pagamento pagamento = (Pagamento) a[1];
            logger.info("Pedido: " + pedido.getId() + ", pagamentoStatus: " + pagamento.getStatus());
        });
    }



    @Test
    public void fazerLeftJoin(){

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Pedido p LEFT JOIN p.pagamento pag";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento", JoinType.LEFT); // LEFT JOIN p.pagamento pag (obs: mesmo não usando a variável o JPA faz o JOIN)

//      quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).
        criteriaQuery.select(root);


        TypedQuery<Pedido> typedQuery = entityManager
//          .createQuery(jpql, Pedido.class);
            .createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertNotNull(lista);

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> logger.info(
            "ID: " + a.getId() +
            ", Pagamento: " + (a.getPagamento() == null ? "null" : a.getPagamento().getStatus())));
    }




}
