package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa.DTOs.ProdutoDTO;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class _6_Trabalhando_com_projecoes_e_DTO extends EntityManagerTest {

/*
        Projeção no contexto do JPA é quando você não busca a entidade completa,
        e sim um recorte/forma específica dos dados: certos atributos (escalares),
        agregações (ex.: count, sum) ou um DTO com apenas o que você precisa.
*/

    @Test
    public void projetarOResultado(){

//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT new com.especialista.jpa.DTOs.ProdutoDTO(id, nome) FROM Produto p";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProdutoDTO> criteriaQuery = criteriaBuilder.createQuery(ProdutoDTO.class);// Query vai retornar ProdutoDTO

        Root<Produto> root = criteriaQuery.from(Produto.class); // FROM Produto p

        criteriaQuery.select(criteriaBuilder.construct(ProdutoDTO.class, // classe DTO
            root.get("id"), root.get("nome"))); // atributos p.id, p.nome de Produto para ser passados no construtor do DTO


        TypedQuery<ProdutoDTO> typedQuery = entityManager
//          .createQuery(jpql, ProdutoDTO.class);
            .createQuery(criteriaQuery);

        List<ProdutoDTO> lista = typedQuery.getResultList();

        Assert.assertNotNull(lista);

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> logger.info("ID: " + a.getId() + ", Nome: " + a.getNome()));
    }

}
