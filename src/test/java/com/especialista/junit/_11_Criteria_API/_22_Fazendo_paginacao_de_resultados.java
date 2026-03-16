package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido_;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class _22_Fazendo_paginacao_de_resultados extends EntityManagerTest {


    @Test
    public void paginarResultados(){
//      Convertendo uma JPQL em Criteria Query
//        String jpql = "SELECT p FROM Pedido p "
//            + " ORDER BY p.id ASC";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // Query vai retornar Pedido

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p

        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).

        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Pedido_.id))); // ORDER BY p.id ASC


//      FIRST_RESULT = MAX_RESULTS * (página - 1);

        int maxResults = 2; // máximo de resultados por página
        int pagina = 1;
        int firstResults = maxResults * (pagina - 1);

        TypedQuery<Pedido> typedQuery =
//            entityManager.createQuery(jpql, Pedido.class)
            entityManager.createQuery(criteriaQuery)
                .setFirstResult(firstResults)
                .setMaxResults(maxResults);


        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID" + " - " + c.getId()));

    }


}
