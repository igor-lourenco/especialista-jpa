package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente_;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class _21_Ordenando_resultado_com_ORDER_BY extends EntityManagerTest {


    @Test
    public void ordenandoResultadosCom_ORDER_BY_DESC(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT c FROM Cliente c "
//          + " ORDER BY c.nome DESC";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class); // SELECT c

        Root<Cliente> root = criteriaQuery.from(Cliente.class); // FROM Cliente c

        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).

        criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Cliente_.nome))); // ORDER BY c.nome DESC

        TypedQuery<Cliente> typedQuery =
//            entityManager.createQuery(jpql, Cliente.class)
            entityManager.createQuery(criteriaQuery);

        List<Cliente> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("Nome" + " - " + c.getNome()));

    }


    @Test
    public void ordenandoResultadosCom_ORDER_BY_ASC(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT c FROM Cliente c "
//          + " ORDER BY c.nome ASC";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class); // SELECT c

        Root<Cliente> root = criteriaQuery.from(Cliente.class); // FROM Cliente c

        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).

        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Cliente_.nome))); // ORDER BY c.nome ASC

        TypedQuery<Cliente> typedQuery =
//            entityManager.createQuery(jpql, Cliente.class)
            entityManager.createQuery(criteriaQuery);

        List<Cliente> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("Nome" + " - " + c.getNome()));

    }

}
