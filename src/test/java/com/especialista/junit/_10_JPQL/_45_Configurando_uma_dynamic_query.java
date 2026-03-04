package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _45_Configurando_uma_dynamic_query extends EntityManagerTest {


/*    - Uma dynamic query (ou consulta dinâmica) é uma consulta cujo conteúdo é montado em tempo de execução,
      normalmente de acordo com os filtros que o usuário escolheu.
      Ou seja: a query não é fixa, ela muda conforme as condições fornecidas.
 */

    @Test
    public void executarConsultaDinamica() {

        Produto produtoConsultado = new Produto();
        produtoConsultado.setNome("Smart");


        List<Produto> lista = pesquisarProduto(produtoConsultado);

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId() + ", NOME: " + c.getNome()));
    }

    private List<Produto> pesquisarProduto(Produto produtoConsultado) {

        StringBuilder jpql = new StringBuilder(  // Por que usar WHERE 1 = 1 ? É um truque comum quando se quer montar
            "SELECT p FROM Produto p WHERE 1 = 1"// consultas dinâmicas programaticamente, adicionando filtros opcionais sem se preocupar
        );                                       // com a presença de WHERE ou com os AND/OR iniciais.


        if (produtoConsultado.getNome() != null)
            jpql.append(" AND p.nome LIKE CONCAT('%', :nome, '%')");

        if (produtoConsultado.getDescricao() != null)
            jpql.append(" AND p.descricao LIKE CONCAT('%', :descricao, '%')");

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql.toString(), Produto.class);

        if (produtoConsultado.getNome() != null)
            typedQuery.setParameter("nome", produtoConsultado.getNome());

        if (produtoConsultado.getDescricao() != null)
            typedQuery.setParameter("descricao", produtoConsultado.getDescricao());

        return typedQuery.getResultList();
    }
}
