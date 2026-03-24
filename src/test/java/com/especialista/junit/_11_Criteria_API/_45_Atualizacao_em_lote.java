package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._2_iniciandoComJPA.modelo.ProdutoIniciandoComJPA;
import com.especialista.jpa._2_iniciandoComJPA.modelo.ProdutoIniciandoComJPA_;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

public class _45_Atualizacao_em_lote extends EntityManagerTest {


    @Test
    public void atualizarEmLote() { //  Busca todos os produtos que sempre foram pedidos pelo mesmo preço

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "UPDATE ProdutoIniciandoComJPA p1 "
//          + " SET p1.preco = 1000 "
//          + " WHERE LENGTH(p1.nome) > 27 "
//          + " AND p1.id BETWEEN 1 AND 10";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<ProdutoIniciandoComJPA> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(ProdutoIniciandoComJPA.class);

        Root<ProdutoIniciandoComJPA> root = criteriaUpdate.from(ProdutoIniciandoComJPA.class); // UPDATE ProdutoIniciandoComJPA p1

        criteriaUpdate.set(
            root.get(ProdutoIniciandoComJPA_.preco), new BigDecimal("1000000")        // SET p1.preco = 1000
        );

        criteriaUpdate.where(criteriaBuilder.greaterThan(
                criteriaBuilder.length(root.get(ProdutoIniciandoComJPA_.nome)), 27),  // WHERE LENGTH(p1.nome) > 27
            criteriaBuilder.between(root.get(ProdutoIniciandoComJPA_.id), 1, 10)      // AND p1.id BETWEEN 1 AND 10
        );


        entityManager.getTransaction().begin();

        Query query = entityManager
//          .createQuery(jpql);
            .createQuery(criteriaUpdate);

        int executedUpdate = query.executeUpdate();
        logger.info("Registros afetados: " + executedUpdate);


        entityManager.getTransaction().commit();
    }
}
