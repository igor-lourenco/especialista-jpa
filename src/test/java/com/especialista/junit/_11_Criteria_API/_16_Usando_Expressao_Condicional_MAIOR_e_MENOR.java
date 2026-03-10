package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto_;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

public class _16_Usando_Expressao_Condicional_MAIOR_e_MENOR extends EntityManagerTest {


    @Test
    public void usandoExpressaoCondicional_MAIOR_ou_IGUAL_E_MENOR_ou_IGUAL(){ // Busca Produto com preco maior ou igual a 1.00 e menor ou igual a 50.00
//      Convertendo uma JPQL em Criteria Query
//        String jpql = "SELECT p FROM Produto p "
//            + " LEFT JOIN FETCH p.estoque est" // Usando LEFT JOIN FETCH para trazer o Estoque com ou sem correspondência com Produto
//            + " WHERE p.preco >= :precoInicial "
//            + " AND p.preco <= :precoFinal";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); // SELECT p

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Produto p

        root.fetch("estoque", JoinType.LEFT); // LEFT JOIN FETCH p.estoque est

        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).


        criteriaQuery.where(criteriaBuilder.and(
            criteriaBuilder.greaterThanOrEqualTo(root.get(Produto_.preco), new BigDecimal("1.00")), //  WHERE p.preco >= :precoInicial
            criteriaBuilder.lessThanOrEqualTo(root.get(Produto_.preco), new BigDecimal("50.00")) // AND p.preco <= :precoFinal
        ));


        TypedQuery<Produto> typedQuery =
//          entityManager.createQuery(jpql, Produto.class)
//            .setParameter("precoInicial", precoInicial)
//            .setParameter("precoFinal", precoFinal);
            entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> {
            logger.info("Nome: " + a.getNome() + " | preço: " + a.getPreco());
        });
    }

}
