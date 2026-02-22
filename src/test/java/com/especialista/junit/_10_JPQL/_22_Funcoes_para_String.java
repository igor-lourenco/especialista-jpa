package com.especialista.junit._10_JPQL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _22_Funcoes_para_String extends EntityManagerTest {


//    CONCAT, LENGTH, LOCATE, SUBSTRING, LOWER, UPPER, TRIM

    @Test
    public void usando_CONCAT() {
        String jpql = "SELECT c.nome, CONCAT('Categoria: ', c.nome) " // concatena (junta) strings
            + " FROM Categoria c "
            + " ORDER BY c.nome ASC";


        logger.info("Buscando Categorias...");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class)
            .setMaxResults(10);

        List<Object[]> categorias = typedQuery.getResultList();

        Assert.assertFalse(categorias.isEmpty());

        logger.info("Buscando Categorias");
        categorias.forEach(c -> logger.info("Nome: " + c[0] + " - " + c[1]));
    }

    @Test
    public void usando_LENGTH() {
        String jpql = "SELECT c.nome, LENGTH(c.nome) " // retorna o tamanho da string (número de caracteres ou bytes, depende do banco).
            + " FROM Categoria c "
            + " ORDER BY c.nome ASC";


        logger.info("Buscando Categorias ...");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class)
            .setMaxResults(10);

        List<Object[]> categorias = typedQuery.getResultList();

        Assert.assertFalse(categorias.isEmpty());

        logger.info("Buscando Categorias");
        categorias.forEach(c -> logger.info("Nome: " + c[0] + " - " + c[1]));
    }

    @Test
    public void usando_LOCATE() {
        String jpql = "SELECT c.nome, LOCATE('a', c.nome) " // devolve a posição (1‑based) de uma substring dentro de outra; 0 se não encontrar.
            + " FROM Categoria c "
            + " ORDER BY c.nome ASC";


        logger.info("Buscando Categorias ...");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class)
            .setMaxResults(10);

        List<Object[]> categorias = typedQuery.getResultList();

        Assert.assertFalse(categorias.isEmpty());

        logger.info("Buscando Categorias");
        categorias.forEach(c -> logger.info("Nome: " + c[0] + " - " + c[1]));
    }

    @Test
    public void usando_SUBSTRING() {
        String jpql = "SELECT c.nome, SUBSTRING(c.nome, 1, 4) " // extrai uma parte da string.
            + " FROM Categoria c "
            + " ORDER BY c.nome ASC";


        logger.info("Buscando Categorias ...");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class)
            .setMaxResults(10);

        List<Object[]> categorias = typedQuery.getResultList();

        Assert.assertFalse(categorias.isEmpty());

        logger.info("Buscando Categorias");
        categorias.forEach(c -> logger.info("Nome: " + c[0] + " - " + c[1]));
    }

    @Test
    public void usando_LOWER() {
        String jpql = "SELECT c.nome, LOWER(c.nome) " //  converte texto para minúsculas.
            + " FROM Categoria c "
            + " ORDER BY c.nome ASC";


        logger.info("Buscando Categorias ...");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class)
            .setMaxResults(10);

        List<Object[]> categorias = typedQuery.getResultList();

        Assert.assertFalse(categorias.isEmpty());

        logger.info("Buscando Categorias");
        categorias.forEach(c -> logger.info("Nome: " + c[0] + " - " + c[1]));
    }

    @Test
    public void usando_UPPER() {
        String jpql = "SELECT c.nome, UPPER(c.nome) " // converte texto para maiúsculas.
            + " FROM Categoria c "
            + " ORDER BY c.nome ASC";


        logger.info("Buscando Categorias ...");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class)
            .setMaxResults(10);

        List<Object[]> categorias = typedQuery.getResultList();

        Assert.assertFalse(categorias.isEmpty());

        logger.info("Buscando Categorias");
        categorias.forEach(c -> logger.info("Nome: " + c[0] + " - " + c[1]));
    }

    @Test
    public void usando_TRIM() {
        String jpql = "SELECT c.nome, TRIM(c.nome) " // remove espaços (ou caracteres especificados) das extremidades da string.
            + " FROM Categoria c "
            + " ORDER BY c.nome ASC";


        logger.info("Buscando Categorias ...");
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class)
            .setMaxResults(10);

        List<Object[]> categorias = typedQuery.getResultList();

        Assert.assertFalse(categorias.isEmpty());

        logger.info("Buscando Categorias");
        categorias.forEach(c -> logger.info("Nome: " + c[0] + " - " + c[1]));
    }

}
