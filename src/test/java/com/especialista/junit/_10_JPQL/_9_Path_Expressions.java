package com.especialista.junit._10_JPQL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _9_Path_Expressions extends EntityManagerTest {

/*      - PATH EXPRESSION

        - É a navegação por atributos e relacionamentos de uma entidade usando o operador ponto (.):
           - Ex → p.cliente.nome navega do Pedido (p) até o cliente e, a partir dele, até o nome.

        - Em relacionamento de coleção: @OneToMany / @ManyToMany (List, Set, etc.) não pode continuar navegando diretamente,
        ou seja, p.itens.produto.nome não é permitido, Precisa fazer JOIN e dar alias:
           - Ex → SELECT prod.nome
                FROM Pedido p
                JOIN p.itensPedido i
                JOIN i.produto prod
*/

    @Test
    public void usarPathExpressions() {
        String jpql1 = "SELECT p.cliente.nome FROM Pedido p";

        logger.info("Buscando Nome dos Clientes com Pedido...");
        TypedQuery<Object> typedQuery1 = entityManager.createQuery(jpql1, Object.class);
        List<Object> lista = typedQuery1.getResultList();


        Assert.assertFalse(lista.isEmpty());

        logger.info("Nome dos Clientes com Pedidos...");
        lista.forEach(p -> {
            logger.info("Cliente: " + p);
        });
    }

    @Test
    public void usarPathExpressionsComProjecao() {
        String jpql1 = "SELECT p.id, p.cliente.nome, p.pagamento.status FROM Pedido p";

        logger.info("Buscando Nome dos Clientes com Pedido...");
        TypedQuery<Object[]> typedQuery1 = entityManager.createQuery(jpql1, Object[].class);
        List<Object[]> lista = typedQuery1.getResultList();


        Assert.assertFalse(lista.isEmpty());

        logger.info("Nome dos Clientes com Pedidos...");
        lista.forEach(p -> {
            logger.info("Id: " + p[0] + ", Cliente: " + p[1] + ", Status Pagamento: " + p[2]);
        });
    }

}
