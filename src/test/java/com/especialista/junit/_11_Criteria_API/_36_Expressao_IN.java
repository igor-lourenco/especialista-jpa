package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente_;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido_;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

public class _36_Expressao_IN extends EntityManagerTest {

/*  - IN → Compara um único valor (campo, função, ou expressão) com um conjunto de valores (lista literal, parâmetro de coleção ou subconsulta).
        - É ideal quando quer filtrar por múltiplos valores de forma concisa.
*/


    @Test
    public void usandoExpresao_IN() {
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p"
//          + " FROM Pedido p "
//          + " JOIN FETCH p.itensPedido item"
//          + " WHERE p.id IN (1, 3, 4) ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        root.fetch(Pedido_.itensPedido); // JOIN FETCH p.itensPedido item

        List<Integer> in = Arrays.asList(1, 3, 4);

        criteriaQuery.where(root.get(Pedido_.id).in(in)); //  WHERE p.id IN (1, 3, 4)

        TypedQuery<Pedido> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
            entityManager.createQuery(criteriaQuery);


        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(c.toString()));
    }


    @Test
    public void usandoExpresao_IN_comParametros1() {
//      Convertendo uma JPQL em Criteria Query
//        String jpql = "SELECT p"
//            + " FROM Pedido p "
//            + " LEFT JOIN FETCH p.itensPedido item"
//            + " WHERE p.cliente IN (:clientes) ";

        Cliente cliente2 = entityManager.find(Cliente.class, 2);
        Cliente cliente3 = new Cliente(); // não precisa ter toda a entidade com os dados, apenas o id funciona também
        cliente3.setId(3);
        List<Cliente> clientes = List.of(cliente2,cliente3);


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        root.fetch(Pedido_.itensPedido, JoinType.LEFT); // LEFT JOIN FETCH p.itensPedido item



        criteriaQuery.where(root.get(Pedido_.cliente).in(clientes)); // WHERE p.cliente IN (:clientes)

        TypedQuery<Pedido> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
            entityManager.createQuery(criteriaQuery);


        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(c.toString()));
    }


    @Test
    public void usandoExpresao_IN_comParametros2() {
//      Convertendo uma JPQL em Criteria Query
//        String jpql = "SELECT p"
//            + " FROM Pedido p "
//            + " LEFT JOIN FETCH p.itensPedido item"
//            + " WHERE p.cliente.id IN (:clientesId) ";

        Cliente cliente2 = entityManager.find(Cliente.class, 2);
        Cliente cliente3 = new Cliente(); // não precisa ter toda a entidade com os dados, apenas o id funciona também
        cliente3.setId(3);
        List<Integer> clientesId = List.of(cliente2.getId(),cliente3.getId());


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        root.fetch(Pedido_.itensPedido, JoinType.LEFT); // LEFT JOIN FETCH p.itensPedido item


        criteriaQuery.where(root.get(Pedido_.cliente).get(Cliente_.id).in(clientesId)); // WHERE p.cliente.id IN (:clientesId)

        TypedQuery<Pedido> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
            entityManager.createQuery(criteriaQuery);


        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(c.toString()));
    }

}
