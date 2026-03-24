package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._2_iniciandoComJPA.modelo.ProdutoIniciandoComJPA;
import com.especialista.jpa._2_iniciandoComJPA.modelo.ProdutoIniciandoComJPA_;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;

public class _46_Remover_em_lote extends EntityManagerTest {


    @Test
    public void atualizarEmLote() { //  Busca todos os produtos que sempre foram pedidos pelo mesmo preço

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "DELETE from ProdutoIniciandoComJPA p1 "
//          + " WHERE p1.id BETWEEN 16 AND 19";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<ProdutoIniciandoComJPA> criteriaDelete = criteriaBuilder.createCriteriaDelete(ProdutoIniciandoComJPA.class);

        Root<ProdutoIniciandoComJPA> root = criteriaDelete.from(ProdutoIniciandoComJPA.class); // DELETE FROM ProdutoIniciandoComJPA p1

        criteriaDelete.where(
            criteriaBuilder.between(root.get(ProdutoIniciandoComJPA_.id), 16, 19)      //  WHERE p1.id BETWEEN 16 AND 19
        );


        entityManager.getTransaction().begin();

        Query query = entityManager
//          .createQuery(jpql);
            .createQuery(criteriaDelete);

        int executedUpdate = query.executeUpdate();
        logger.info("Registros afetados: " + executedUpdate);


        entityManager.getTransaction().commit();
    }
}
