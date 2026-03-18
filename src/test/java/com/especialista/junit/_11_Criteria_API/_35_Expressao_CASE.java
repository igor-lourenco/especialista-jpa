package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido_;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.StatusPedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;

public class _35_Expressao_CASE extends EntityManagerTest {

/*   - CASE → Expressão condicional para produzir valores
        - Usando quando precisa escolher valores com base em condições. Funciona em qualquer lugar onde uma expressão
      é válida (tipicamente SELECT, WHERE, HAVING, ORDER BY (expressões).
        - Independente de herança


    - TYPE → Operador para herança/polimorfismo
        - Usado quando quer filtrar ou saber o subtipo de uma entidade em uma hierarquia JPA (@Inheritance). Ele retorna o
      tipo concreto da instância da entidade do alias (Principalmente em WHERE, SELECT, GROUP BY, ORDER BY com aliases de entidade)
        - Específico para entidades e herança
*/


    @Test
    public void usandoExpresao_CASE() {  // Agrupa total de vendas por categoria que vendem acima de 5000

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p.id,  "
//          + " CASE p.status "                             // CASO status
//          + "     WHEN 'PAGO' THEN 'Está Pago' "          // QUANDO for 'PAGO' ENTÃO 'Está Pago'
//          + "     WHEN 'CANCELADO' THEN 'Foi cancelado' " // QUANDO for 'CANCELADO' ENTÃO 'Foi cancelado'
//          + "     ELSE 'Está aguardando' "                // SENÃO 'Está aguardando'
//          + "END "                                        // FIM
//          + "FROM Pedido p";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // Query vai retornar Object[]

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p


        criteriaQuery.multiselect(                                                       // SELECT
            root.get(Pedido_.id),                                             // p.id
            criteriaBuilder.selectCase(root.get(Pedido_.STATUS))              // CASE p.status                         (tem que passar como string senão não funciona)
                .when(StatusPedido.PAGO.toString(), "Está Pago")         // WHEN 'PAGO' THEN 'Está Pago'          (se passar status 'PAGO' como parâmetro não funciona)
                .when(StatusPedido.CANCELADO.toString(), "Foi cancelado")// WHEN 'CANCELADO' THEN 'Foi cancelado' (se passar status 'CANCELADO' como parâmetro não funciona)
                .otherwise("Está aguardando")                                     // ELSE 'Está aguardando'
        );


        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
            entityManager.createQuery(criteriaQuery);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(c[0] + ", " + c[1]));
    }


    @Test
    public void usandoExpresao_TYPE() {  // Agrupa total de vendas por categoria que vendem acima de 5000

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p.id,  "
//          + " CASE TYPE(p.pagamento) "                                 // CASO TIPO pagamento
//          + "     WHEN PagamentoBoleto THEN 'Pagamento com boleto.' "  // QUANDO for PagamentoBoleto ENTÃO 'Pagamento com boleto.'
//          + "     WHEN PagamentoCartao THEN 'Pagamento com cartão.' "  // QUANDO for PagamentoCartao ENTÃO 'Pagamento com cartão.'
//          + "     ELSE 'Nenhum tipo de pagamento.' "                   // SENÃO 'Nenhum tipo de pagamento.'
//          + "END "                                                     // FIM
//          + "FROM Pedido p";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // Query vai retornar Object[]

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p


//      Para converter para string e poder usar ps valores que estão no @DiscriminatorValue das classes que herdam Pagamento
        Expression<String> pagamento = root.get(Pedido_.pagamento).type().as(String.class);


//      Obs: O 'PagamentoBoleto' e o 'PagamentoCartao' são os valores que estão no @DiscriminatorValue das classes que herdam Pagamento

        criteriaQuery.multiselect(                                                 // SELECT
            root.get(Pedido_.id),                                       // p.id
            criteriaBuilder.selectCase(pagamento)                        // CASE TYPE(p.pagamento)                           (tem que passar como string senão não funciona)
                .when("PagamentoBoleto", "Pagamento com boleto.")  // WHEN PagamentoBoleto THEN 'Pagamento com boleto.' (se passar status 'PagamentoBoleto' como parâmetro não funciona)
                .when("PagamentoCartao", "Pagamento com cartão.")  // WHEN PagamentoCartao THEN 'Pagamento com cartão.' (se passar status 'PagamentoCartao' como parâmetro não funciona)
                .otherwise("Nenhum tipo de pagamento.")                     // ELSE 'Nenhum tipo de pagamento.'
        );


        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
            entityManager.createQuery(criteriaQuery);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(c[0] + ", " + c[1]));
    }

}
