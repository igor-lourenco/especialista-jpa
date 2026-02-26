package com.especialista.junit._10_JPQL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _32_Expressao_CASE extends EntityManagerTest {


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
    public void usandoExpresao_CASE() { //

        String jpql = "SELECT p.id,  "
            + " CASE p.status "                             // CASO status
            + "     WHEN 'PAGO' THEN 'Está Pago' "          // QUANDO for 'PAGO' ENTÃO 'Está Pago'
            + "     WHEN 'CANCELADO' THEN 'Foi cancelado' " // QUANDO for 'CANCELADO' ENTÃO 'Foi cancelado'
            + "     ELSE 'Está aguardando' "                // SENÃO 'Está aguardando'
            + "END "                                        // FIM
            + "FROM Pedido p";


        logger.info("Buscando Pedido...");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info(c[0] + ", " + c[1]));
    }


    @Test
    public void usandoExpresao_TYPE() {

        String jpql = "SELECT p.id,  "
            + " CASE TYPE(p.pagamento) "                                 // CASO TIPO pagamento
            + "     WHEN PagamentoBoleto THEN 'Pagamento com boleto.' "  // QUANDO for PagamentoBoleto ENTÃO 'Pagamento com boleto.'
            + "     WHEN PagamentoCartao THEN 'Pagamento com cartão.' "  // QUANDO for PagamentoCartao ENTÃO 'Pagamento com cartão.'
            + "     ELSE 'Nenhum tipo de pagamento.' "                   // SENÃO 'Nenhum tipo de pagamento.'
            + "END "                                                     // FIM
            + "FROM Pedido p";


        logger.info("Buscando Pedido...");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado...");
        lista.forEach(c -> logger.info(c[0] + ", " + c[1]));
    }
}
