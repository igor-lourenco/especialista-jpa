package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.ItemPedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.ItemPedido_;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class _40_Criando_SUBQUERIES_com_EXISTS extends EntityManagerTest {

/*   - EXISTS é um predicado booleano usado em subconsultas dentro de WHERE (ou HAVING).
       - Ele não retorna dados; apenas responde: “existe pelo menos uma linha na subquery que satisfaça as condições?”.
*/

    @Test
    public void usandoExpresao_EXISTS() { // busca todos os produtos que já foram pedidos alguma vez
//      Convertendo uma JPQL em Criteria Query

//      O '1' é uma semântica pra deixar clara a intenção: comunica que só queremos saber se há linhas. Evitando a impressão de que o valor selecionado será usado.
//      String jpql = "SELECT pro"
//          + " FROM Produto pro " // percorre todos os registros de Produto
//          + " WHERE EXISTS ( "
//          + "     SELECT 1 "
//          + "      FROM ItemPedido item"
//          + "      JOIN item.produto pro2 "
//          + "      WHERE pro2 = pro "
//          + ")";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); // Query vai retornar Produto

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Produto pro

        criteriaQuery.select(root); // SELECT pro

//      ---
        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);  // SubQuery vai retornar Integer
        Root<ItemPedido> subRoot = subquery.from(ItemPedido.class);     // FROM ItemPedido item

        subquery.select(criteriaBuilder.literal(1));                       //  SELECT 1
        Join<ItemPedido, Produto> joinProduto = subRoot.join(ItemPedido_.produto); // JOIN item.produto pro2

        subquery.where(criteriaBuilder.equal( joinProduto, root));   //  WHERE pro2 = pro
//      ---

        criteriaQuery.where(// WHERE EXISTS (SubQuery)
            criteriaBuilder.exists(subquery));


        TypedQuery<Produto> typedQuery =
//          entityManager.createQuery(jpql, Produto.class)
            entityManager.createQuery(criteriaQuery);


        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }

    @Test
    public void usandoExpresao_NOT_EXISTS() { // busca todos os produtos que nunca foram pedidos alguma vez
//      Convertendo uma JPQL em Criteria Query

//      O '1' é uma semântica pra deixar clara a intenção: comunica que só queremos saber se há linhas. Evitando a impressão de que o valor selecionado será usado.
//      String jpql = "SELECT pro"
//          + " FROM Produto pro "  // percorre todos os registros de Produto
//          + " WHERE NOT EXISTS ( "
//          + "     SELECT 1 "
//          + "      FROM ItemPedido item"
//          + "      JOIN item.produto pro2 "
//          + "      WHERE pro2 = pro "
//          + ")";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); // Query vai retornar Produto

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Produto pro

        criteriaQuery.select(root); // SELECT pro

//      ---
        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);  // SubQuery vai retornar Integer
        Root<ItemPedido> subRoot = subquery.from(ItemPedido.class);     // FROM ItemPedido item

        subquery.select(criteriaBuilder.literal(1));                       //  SELECT 1
        Join<ItemPedido, Produto> joinProduto = subRoot.join(ItemPedido_.produto); // JOIN item.produto pro2

        subquery.where(criteriaBuilder.equal( joinProduto, root));   //  WHERE pro2 = pro
//      ---

        criteriaQuery.where(criteriaBuilder.not( // WHERE NOT EXISTS (SubQuery)
            criteriaBuilder.exists(subquery)
        ));


        TypedQuery<Produto> typedQuery =
//          entityManager.createQuery(jpql, Produto.class)
            entityManager.createQuery(criteriaQuery);


        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }



}
