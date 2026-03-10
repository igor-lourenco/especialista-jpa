package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _13_Usando_Expressao_Condicional_IS_NULL_e_IS_EMPTY extends EntityManagerTest {

/*  - IS NULL

    - O que testa:
       - Se um valor singular é null
    - Onde usar:
       - Atributos simples (campos básicos) ou relacionamentos singulares (@ManyToOne, @OneToOne)

    - IS EMPTY

    - O que testa:
      - Se uma coleção mapeada está vazia (tamanho 0)
    - Onde usar:
      - Em coleções de relacionamento (@OneToMany, @ManyToMany) e, na prática, também funciona na maioria dos provedores para @ElementCollection
*/

    @Test
    public void usandoExpressaoCondicional_IS_NULL() { // Busca todos os produtos que NÃO TEM dataUltimaAtualizacao
        String jpql = "SELECT p FROM Produto p "
            + " WHERE p.dataUltimaAtualizacao IS NULL ";

        logger.info("Buscando Produto ...");
        TypedQuery<Object> typedQuery = entityManager.createQuery(jpql, Object.class);

        List<Object> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Produtos retornados que NÃO TEM dataUltimaAtualizacao...");
        lista.forEach(c -> {
            Produto p = (Produto) c;
            logger.info("Nome: " + p.getNome() + ", dataUltimaAtualizacao: " + p.getDataUltimaAtualizacao());
        });
    }

    @Test
    public void usandoExpressaoCondicional_IS_NOT_NULL() { // Busca todos os produtos que TEM dataUltimaAtualizacao
        String jpql = "SELECT p FROM Produto p "
            + " WHERE p.dataUltimaAtualizacao IS NOT NULL ";

        logger.info("Buscando Produto ...");
        TypedQuery<Object> typedQuery = entityManager.createQuery(jpql, Object.class);

        List<Object> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Produtos retornados que TEM dataUltimaAtualizacao...");
        lista.forEach(c -> {
            Produto p = (Produto) c;
            logger.info("Nome: " + p.getNome() + ", dataUltimaAtualizacao: " + p.getDataUltimaAtualizacao());
        });
    }


    @Test
    public void usandoExpressaoCondicional_IS_EMPTY() { // Busca todos os produtos que NÃO TEM Categorias
        String jpql = "SELECT p FROM Produto p "
            + " LEFT JOIN FETCH p.categorias cat "
            + " LEFT JOIN FETCH p.estoque est "
            + " WHERE p.categorias IS EMPTY";

        logger.info("Buscando Produto ...");
        TypedQuery<Object> typedQuery = entityManager.createQuery(jpql, Object.class);

        List<Object> lista = typedQuery.getResultList();

//        Assert.assertFalse(lista.isEmpty()); // não existe produto no banco de dados sem categoria

        logger.info("Produtos retornados que não tem Categoria...");
        lista.forEach(c -> {
            Produto p = (Produto) c;
            logger.info("Nome: " + p.getNome() + ", categorias: " + p.getCategorias().size());
        });
    }

    @Test
    public void usandoExpressaoCondicional_IS_NOT_EMPTY() {// Busca todos os produtos que TEM Categorias
        String jpql = "SELECT p FROM Produto p "
            + " LEFT JOIN FETCH p.categorias cat " // Usando LEFT JOIN FETCH para trazer as Categorias com ou sem correspondência com Produto
            + " LEFT JOIN FETCH p.estoque est "    // Usando LEFT JOIN FETCH para trazer o Estoque com ou sem correspondência com Produto
            + " WHERE p.categorias IS NOT EMPTY";

        logger.info("Buscando Produto ...");
        TypedQuery<Object> typedQuery = entityManager.createQuery(jpql, Object.class);

        List<Object> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Produtos retornados que não tem Categoria...");
        lista.forEach(c -> {
            Produto p = (Produto) c;
            logger.info("Nome: " + p.getNome() + ", categorias: " + p.getCategorias().size());
        });
    }
}
