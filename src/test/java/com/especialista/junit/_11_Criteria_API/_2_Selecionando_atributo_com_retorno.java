package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

public class _2_Selecionando_atributo_com_retorno extends EntityManagerTest {

    @Test
    public void selecionandoUmAtributoComoRetorno1(){

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p.cliente FROM Pedido p WHERE p.id = 1";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class); // Query vai retornar Cliente

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        criteriaQuery.select(root.get("cliente")); // atributo cliente de p.cliente

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1)); // WHERE p.id = 1


        TypedQuery<Cliente> typedQuery = entityManager
//            .createQuery(jpql, Cliente.class);
            .createQuery(criteriaQuery);

        List<Cliente> lista = typedQuery.getResultList();

        Assert.assertNotNull(lista);

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> logger.info(a.toString()));
    }

    @Test
    public void selecionandoUmAtributoComoRetorno2(){

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p.total FROM Pedido p WHERE p.id = 1";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class); // Query vai retornar BigDecimal

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        criteriaQuery.select(root.get("total")); // atributo total de p.total

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1)); // WHERE p.id = 1


        TypedQuery<BigDecimal> typedQuery = entityManager
//            .createQuery(jpql, BigDecimal.class);
            .createQuery(criteriaQuery);

        List<BigDecimal> lista = typedQuery.getResultList();

        Assert.assertNotNull(lista);

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> logger.info("TOTAL: " + a.toString()));
    }
}
