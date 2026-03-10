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
import java.util.List;

public class _15_Usando_Expressao_Condicional_IS_NULL_e_IS_EMPTY extends EntityManagerTest {

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
    public void usandoExpressaoCondicional_IS_NULL(){ // Busca todos os produtos que NÃO TEM dataUltimaAtualizacao
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Produto p "
//          + " WHERE p.dataUltimaAtualizacao IS NULL ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); // SELECT p

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Produto p


        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).

        criteriaQuery.where(root.get(Produto_.DATA_ULTIMA_ATUALIZACAO).isNull()); // WHERE p.dataUltimaAtualizacao IS NULL
//        criteriaQuery.where(criteriaBuilder.isNull(root.get(Produto_.DATA_ULTIMA_ATUALIZACAO))); // o mesmo que o acima de uma forma diferente


        TypedQuery<Produto> typedQuery =
//          entityManager.createQuery(jpql, Produto.class);
            entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> {
            logger.info("Nome: " + a.getNome() + " | dataUltimaAtualização: " + a.getDataUltimaAtualizacao());
        });
    }


    @Test
    public void usandoExpressaoCondicional_IS_NOT_NULL(){  // Busca todos os produtos que TEM dataUltimaAtualizacao
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Produto p "
//          + " WHERE p.dataUltimaAtualizacao IS NOT NULL ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); // SELECT p

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Produto p


        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).

        criteriaQuery.where(root.get(Produto_.DATA_ULTIMA_ATUALIZACAO).isNotNull()); // WHERE p.dataUltimaAtualizacao IS NOT NULL
//        criteriaQuery.where(criteriaBuilder.isNotNull(root.get(Produto_.DATA_ULTIMA_ATUALIZACAO))); // o mesmo que o acima de uma forma diferente


        TypedQuery<Produto> typedQuery =
//          entityManager.createQuery(jpql, Produto.class);
            entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> {
            logger.info("Nome: " + a.getNome() + " | dataUltimaAtualização: " + a.getDataUltimaAtualizacao());
        });
    }


    @Test
    public void usandoExpressaoCondicional_IS_EMPTY(){  // Busca todos os produtos que NÃO TEM Categorias
//      Convertendo uma JPQL em Criteria Query
//        String jpql = "SELECT p FROM Produto p "
//            + " LEFT JOIN FETCH p.categorias cat "
//            + " LEFT JOIN FETCH p.estoque est "
//            + " WHERE p.categorias IS EMPTY";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); // SELECT p

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Produto p

        root.fetch("categorias", JoinType.LEFT); // LEFT JOIN FETCH p.categorias cat
        root.fetch("estoque", JoinType.LEFT); // LEFT JOIN FETCH p.estoque est


        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).

        criteriaQuery.where(criteriaBuilder.isEmpty((root.get(Produto_.CATEGORIAS)))); // WHERE p.categorias IS EMPTY


        TypedQuery<Produto> typedQuery =
//          entityManager.createQuery(jpql, Produto.class);
            entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();

//        Assert.assertFalse(lista.isEmpty()); // não existe produto no banco de dados sem categoria

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> {
            logger.info("Nome: " + a.getNome() + " | categorias: " + a.getCategorias().size());
        });
    }



    @Test
    public void usandoExpressaoCondicional_IS_NOT_EMPTY(){ // Busca todos os produtos que TEM Categorias
//      Convertendo uma JPQL em Criteria Query
        String jpql = "SELECT p FROM Produto p "
            + " LEFT JOIN FETCH p.categorias cat " // Usando LEFT JOIN FETCH para trazer as Categorias com ou sem correspondência com Produto
            + " LEFT JOIN FETCH p.estoque est "    // Usando LEFT JOIN FETCH para trazer o Estoque com ou sem correspondência com Produto
            + " WHERE p.categorias IS NOT EMPTY";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); // SELECT p

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Produto p

        root.fetch("categorias", JoinType.LEFT); // LEFT JOIN FETCH p.categorias cat
        root.fetch("estoque", JoinType.LEFT); // LEFT JOIN FETCH p.estoque est


        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).

        criteriaQuery.where(criteriaBuilder.isNotEmpty((root.get(Produto_.CATEGORIAS)))); // WHERE p.categorias IS NOT EMPTY


        TypedQuery<Produto> typedQuery =
//          entityManager.createQuery(jpql, Produto.class);
            entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> {
            logger.info("Nome: " + a.getNome() + " | categorias: " + a.getCategorias().size());
        });
    }

}
