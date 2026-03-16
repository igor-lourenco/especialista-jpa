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

public class _13_Tipagem_forte_com_MetaModel extends EntityManagerTest {

/*      → O Metamodel JPA é um conjunto de classes geradas automaticamente que representam as entidades da aplicação de forma tipada,
        permitindo que escreva consultas Criteria API com segurança de tipos (type‑safe).


        → Sem o Metamodel:
          - No Criteria API seria obrigado a usar strings, que são frágeis (erra o nome de um campo e só descobre no runtime)

          Exemplo:
            root.get("nome"); // Se escrever "nom" por engano, só falha em runtime

        → Com o Metamodel
          - Usa classes geradas pelo compilador, evitando erro de digitação e ganhando navegação no IDE:

          Exemplo:
            root.get(Produto_.nome); // agora é type‑safe


        → Obs: As classes são geradas na pasta: target/generated-sources/annotations
*/


    @Test
    public void utilizar_MetaModel(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Produto p "
//            + " WHERE p.nome LIKE CONCAT('%', 'K', '%')"
//            + " OR p.descricao LIKE CONCAT('%', 'K', '%')";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); // Query vai retornar Produto

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Produto p


        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).

        criteriaQuery.where(criteriaBuilder.or(
            criteriaBuilder.like(root.get(Produto_.NOME), "%K%"), // WHERE p.nome LIKE CONCAT('%', 'K', '%')
            criteriaBuilder.like(root.get(Produto_.DESCRICAO), "%K%") // OR p.descricao LIKE CONCAT('%', 'K', '%')
        ));

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
