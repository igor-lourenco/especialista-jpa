package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _46_Configurando_uma_named_query extends EntityManagerTest {


/*    - Uma Named Query (ou consulta nomeada) é uma consulta JPQL ou SQL pré-definida, estática e registrada
      com um nome, geralmente dentro da própria entidade JPA.
      Depois disso, você a executa simplesmente usando esse nome — sem precisar escrever a string da query novamente.

      Alguns benefícios:
      - Evita escrever a mesma JPQL várias vezes
      - Centraliza a query em um único lugar
      - É validada na inicialização (evitando erro só em runtime)
      - Pode ter melhor desempenho (o provedor JPA pré-compila e faz cache da query)
      - Melhora organização e legibilidade
 */
    
    @Test
    public void executarConsulta1() {

        TypedQuery<Produto> typedQuery = entityManager.createNamedQuery("Produto.listar", Produto.class);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId() + ", NOME: " + c.getNome()));

    }

    @Test
    public void executarConsulta2() {

        TypedQuery<Produto> typedQuery = entityManager.createNamedQuery("Produto.listarPorCategoria", Produto.class)
            .setParameter("categoriaId", 1);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId() + ", NOME: " + c.getNome()));

    }
}
