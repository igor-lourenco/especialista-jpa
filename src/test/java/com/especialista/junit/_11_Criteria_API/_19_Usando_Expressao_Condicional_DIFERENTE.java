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

public class _19_Usando_Expressao_Condicional_DIFERENTE extends EntityManagerTest {



    @Test
    public void usandoExpressaoCondicional_BETWEEN(){// Busca Produto com preco maior ou igual a 1.00 e menor ou igual a 50.00
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT c FROM Cliente c "
//          + " WHERE c.nome <> :nome"; // O diferente é representado pelo sinal <>

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class); // SELECT c

        Root<Cliente> root = criteriaQuery.from(Cliente.class); // FROM Cliente c

        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).

        criteriaQuery.where(criteriaBuilder.notEqual(
            root.get(Cliente_.nome), "Marcos Mariano" //   WHERE c.nome <> :nome
        ));

        TypedQuery<Cliente> typedQuery =
//          entityManager.createQuery(jpql, Cliente.class)
//            .setParameter("precoInicial", precoInicial)
//            .setParameter("precoFinal", precoFinal);
            entityManager.createQuery(criteriaQuery);

        List<Cliente> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> {
            logger.info("Nome: " + a.getNome());
        });

    }

}
