package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _41_Exercicio_com_ALL extends EntityManagerTest {


    @Test
    public void exercicioCom_ALL() {

//      Busca todos os produtos que sempre foram pedidos pelo mesmo preço

//      - Não é para usar a coluna preço da tabela Produto
//      - Tem que usar o DISTINCT
//      - O FROM tem que ser para ItemPedido
//      - Tem que ter o JOIN com a tabela Produto


        String jpql = "SELECT DISTINCT prod1 FROM ItemPedido item "
            + " JOIN  item.produto prod1 "
            + " WHERE item.precoProduto = ALL ( "
            + "     SELECT item2.precoProduto FROM ItemPedido item2 "
            + "      WHERE item2.produto = prod1 AND item.id <> item2.id "
            + ")";

        logger.info("Buscando Produto...");
        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());


        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }

}
