package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

public class _14_Usando_Expressao_Condicional_MAIOR_e_MENOR extends EntityManagerTest {


    @Test
    public void usandoExpressaoCondicional_MIOR_ou_IGUAL_E_MENOR_ou_IGUAL() { // Busca Produto com preco maior ou igual a 1.00 e menor ou igual a 50.00
        String jpql = "SELECT p FROM Produto p "
            + " LEFT JOIN FETCH p.estoque est" // Usando LEFT JOIN FETCH para trazer o Estoque com ou sem correspondência com Produto
            + " WHERE p.preco >= :precoInicial "
            + " AND p.preco <= :precoFinal";

        BigDecimal precoInicial = new BigDecimal("1.00");
        BigDecimal precoFinal = new BigDecimal("50.00");

        logger.info("Buscando Produto ...");
        TypedQuery<Object> typedQuery = entityManager.createQuery(jpql, Object.class)
            .setParameter("precoInicial", precoInicial) // Todos parâmetros criados no JPQL tem que ser especificado senão JPA solta EXCEPTION
            .setParameter("precoFinal", precoFinal);

        List<Object> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Produtos retornados com preco maior ou igual a 1.00 e menor ou igual a 50.00");
        lista.forEach(c -> {
            Produto p = (Produto) c;
            logger.info("Nome: " + p.getNome() + ", preco: " + p.getPreco());
        });
    }

    @Test
    public void usandoExpressaoCondicional_MAIOR_ou_IGUAL() { // Busca Produto com preco maior ou IGUAL a 500.00
        String jpql = "SELECT p FROM Produto p "
            + " WHERE p.preco >= :preco";

        BigDecimal preco = new BigDecimal("500.00");

        logger.info("Buscando Produto ...");
        TypedQuery<Object> typedQuery = entityManager.createQuery(jpql, Object.class)
            .setParameter("preco", preco);

        List<Object> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Produtos retornados com preco maior ou igual a 500.00");
        lista.forEach(c -> {
            Produto p = (Produto) c;
            logger.info("Nome: " + p.getNome() + ", preco: " + p.getPreco());
        });
    }

    @Test
    public void usandoExpressaoCondicional_MENOR() { // Busca Produto com preco menor que 500.00
        String jpql = "SELECT p FROM Produto p "
            + " WHERE p.preco < :preco";

        BigDecimal preco = new BigDecimal("500.00");

        logger.info("Buscando Produto ...");
        TypedQuery<Object> typedQuery = entityManager.createQuery(jpql, Object.class)
            .setParameter("preco", preco);

        List<Object> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Produtos retornados com preco menor que 500.00");
        lista.forEach(c -> {
            Produto p = (Produto) c;
            logger.info("Nome: " + p.getNome() + ", preco: " + p.getPreco());
        });
    }

    @Test
    public void usandoExpressaoCondicional_MENOR_ou_IGUAL() { // Busca Produto com preco menor ou IGUAL a 500.00
        String jpql = "SELECT p FROM Produto p "
            + " WHERE p.preco <= :preco";

        BigDecimal preco = new BigDecimal("500.00");

        logger.info("Buscando Produto ...");
        TypedQuery<Object> typedQuery = entityManager.createQuery(jpql, Object.class)
            .setParameter("preco", preco);

        List<Object> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Produtos retornados com preco menor ou igual a 500.00");
        lista.forEach(c -> {
            Produto p = (Produto) c;
            logger.info("Nome: " + p.getNome() + ", preco: " + p.getPreco());
        });
    }
}
