package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pagamento;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _7_Usando_LEFT_OUTER_JOIN extends EntityManagerTest {

/*      - LEFT JOIN = LEFT OUTER JOIN

        - Diferente do JOIN(INNER JOIN) que só vai trazer os Pedidos que tiverem Pagamentos associados, o LEFT JOIN retorna todas as
      entidades do lado esquerdo, com ou sem correspondência. Quando não há correspondência, os atributos do lado direito vêm como NULL

        - LEFT JOIN permite trazer também os Pagamentos quando houver e ainda manter os Pedidos sem Pagamentos, com pag = NULL na linha
      (ou coleção vazia no caso de LEFT JOIN FETCH).

        - LEFT JOIN com projeção é recomendado projetar campos e não entidades, assim faz um JOIN simples e o
      Hibernate traz tudo em uma única consulta, sem precisar de IDs ou selects extras.

        - Diferença entre WHERE e ON:
            - ON → a condição é aplicada no momento do join, limitando quais linhas do lado direito podem casar com a linha do lado esquerdo.
          Preserva todas as linhas do lado esquerdo, mesmo quando não houver match (o lado direito vira NULL).

            - WHERE → a condição é aplicada depois do join. Se você filtra por uma coluna do lado direito (ex.: p.status = 'CANCELADO'),
          as linhas onde o lado direito é NULL serão removidas, o que na prática transforma o LEFT JOIN em INNER JOIN.
*/

    @Test
    public void fazerLeftJoin() {
        String jpql1 = "SELECT p FROM Pedido p LEFT JOIN p.pagamento pag";

        logger.info("Buscando uma lista de Pedido com Pagamento...");
        TypedQuery<Pedido> typedQuery1 = entityManager.createQuery(jpql1, Pedido.class);
        List<Pedido> listaPedidoComPagamento = typedQuery1.getResultList();

        Assert.assertFalse(listaPedidoComPagamento.isEmpty());

        logger.info("Lista de Pedido com Pagamento retornados...");
        listaPedidoComPagamento.forEach(dto ->
            logger.info(dto.getId() + " - " + dto.getStatus()));
    }


    @Test
    public void fazerLeftJoinComON() {
        String jpql = "SELECT p, pag FROM Pedido p LEFT JOIN p.pagamento pag ON pag.status = 'PROCESSANDO'";

        logger.info("Buscando uma lista de Pedido com Pagamento...");
        TypedQuery<Object[]> typedQuery1 = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> lista = typedQuery1.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Lista de Pedido com Pagamento retornados usando o ON...");
        lista.forEach(dto -> {
            Pedido pedido = (Pedido) dto[0];
            Pagamento pagamento = (Pagamento) dto[1];
            logger.info("Pedido: " + pedido.getId() + " ,Pagamento: " + pagamento);
        });
    }


    @Test
    public void fazerLeftJoinComWHERE() {
        String jpql = "SELECT p, pag FROM Pedido p LEFT JOIN p.pagamento pag WHERE pag.status = 'PROCESSANDO'";

        logger.info("Buscando uma lista de Pedido com Pagamento...");
        TypedQuery<Object[]> typedQuery1 = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> lista = typedQuery1.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Lista de Pedido com Pagamento retornados usando o WHERE...");
        lista.forEach(dto -> {
            Pedido pedido = (Pedido) dto[0];
            Pagamento pagamento = (Pagamento) dto[1];
            logger.info("Pedido: " + pedido.getId() + " ,Pagamento: " + pagamento);
        });
    }
}
