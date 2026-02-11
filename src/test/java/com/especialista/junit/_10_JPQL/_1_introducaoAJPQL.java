package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _1_introducaoAJPQL extends EntityManagerTest {

    @Test
    public void buscaIdentificador() {
//      Java Persistence Query Language - JPQL

//      ----------------- Diferença entre JPQL e SQL --------------------------------

//      JPQL - select p from Pedido p where p.id = 1
//      SQL  - select p.* from tb_pedido p where p.id = 1


//      JPQL - referência entidades e o atributos
//      SQL  - referência a tabelas e a colunas


//      ----------------- Usando join --------------------------------

//      JPQL - select p from Pedido p join p.itens i where i.precoProduto > 10
//      SQL  - select p.* from tb_pedido p join tb_item_pedido i on i.pedido_id = p.id where i.preco_preco > 10


//      ----------- JPQL Equivalente a busca usando EntityManager -----------------------------

//        entityManager.find(Pedido.class, 1)

//      Diferente do SQL, o JPQL além de trazer os dados do Pedido, também traz os dados das outras entidades associadas ao Pedido
        TypedQuery<Pedido> typedQuery = entityManager
            .createQuery("select p from Pedido p where p.id = 1", Pedido.class);

//        Pedido pedido = typedQuery.getSingleResult(); // senão retornar um registro apenas, solta exception
        List<Pedido> pedidos = typedQuery.getResultList();

        Assert.assertNotNull(pedidos.get(0));
        Assert.assertEquals(1, pedidos.get(0).getId().intValue());

    }
}
