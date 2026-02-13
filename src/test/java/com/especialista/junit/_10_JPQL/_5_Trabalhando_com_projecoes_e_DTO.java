package com.especialista.junit._10_JPQL;

import com.especialista.jpa.DTOs.ProdutoDTO;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _5_Trabalhando_com_projecoes_e_DTO extends EntityManagerTest {

    @Test
    public void projetarNoDTO() {
/*
        Projeção no contexto do JPA é quando você não busca a entidade completa,
        e sim um recorte/forma específica dos dados: certos atributos (escalares),
        agregações (ex.: count, sum) ou um DTO com apenas o que você precisa.
*/

//      Tem que passar o especificar o caminho onde tá a classe ProdutoDTO e dentro do construtor ProdutoDTO especificar os campos da entidade Produto
        String jpql = "SELECT new com.especialista.jpa.DTOs.ProdutoDTO(id, nome) FROM Produto"; // retorna uma lista de ProdutoDTO

        logger.info("Buscando uma lista de ProdutoDTO...");
        TypedQuery<ProdutoDTO> typedQuery = entityManager.createQuery(jpql, ProdutoDTO.class);
        List<ProdutoDTO> listaComIdENome = typedQuery.getResultList();

        Assert.assertFalse(listaComIdENome.isEmpty());

        logger.info("Lista de ProdutoDTO retornados...");
        listaComIdENome.forEach(dto ->
            logger.info(dto.getId() + " - " + dto.getNome()));

    }
}
