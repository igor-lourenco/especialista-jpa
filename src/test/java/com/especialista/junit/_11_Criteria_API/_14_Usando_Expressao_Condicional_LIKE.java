package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto_;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class _14_Usando_Expressao_Condicional_LIKE extends EntityManagerTest {


    @Test
    public void usandoExpressaoCondicional_LIKE(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Produto p "
//          + " WHERE p.nome LIKE CONCAT('%', :nome, '%')";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); // SELECT p

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Produto p


        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).

        criteriaQuery.where(
            criteriaBuilder.like(root.get(Produto_.NOME), "%K%") // WHERE p.nome LIKE CONCAT('%', :nome, '%')
        );

        TypedQuery<Produto> typedQuery =
//          entityManager.createQuery(jpql, Produto.class);
            entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> {
            logger.info("Nome: " + a.getNome() + " | Descrição: " + a.getDescricao());
        });
    }

}
