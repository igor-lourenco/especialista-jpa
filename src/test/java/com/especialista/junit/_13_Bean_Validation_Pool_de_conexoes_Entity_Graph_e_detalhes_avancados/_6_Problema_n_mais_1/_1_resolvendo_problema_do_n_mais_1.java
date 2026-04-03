package com.especialista.junit._13_Bean_Validation_Pool_de_conexoes_Entity_Graph_e_detalhes_avancados._6_Problema_n_mais_1;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityGraph;
import java.util.List;

public class _1_resolvendo_problema_do_n_mais_1 extends EntityManagerTest {
/*  - Entity Graph
      - Permite controlar dinamicamente quais associações de uma entidade serão
    carregadas em uma query, sem precisar mudar o mapeamento (EAGER / LAZY) da entidade.
      - É um mecanismo para definir por consulta quais relacionamentos e atributos devem ser carregados
    imediatamente, sem alterar o fetch da entidade.


    - javax.persistence.fetchgraph:
      - Só carrega os atributos que foram especificados
      - O restante dos atributos vira LAZY, mesmo que esteja EAGER


    - javax.persistence.loadgraph:
      - Carrega os atributos que foram especificados como EAGER
      - Respeita o EAGER definifido na entidade
      - O restante dos atributos segue o mapeamento original
*/

    @Test
    public void resolvendoCom_FETCH(){

        String jpql = "SELECT p FROM Pedido p "
            + " LEFT JOIN FETCH p.cliente c "     // Usando LEFT para trazer a cliente com ou sem correspondência com Pedido (ou seja, cliente pode ser null)
            + " LEFT JOIN FETCH p.pagamento pag " // Usando LEFT para trazer a pagamento com ou sem correspondência com Pedido, (ou seja, pagamento pode ser null)
            + " LEFT JOIN FETCH p.notaFiscal nf " // Usando LEFT para trazer a notaFiscal com ou sem correspondência com Pedido, (ou seja, notaFiscal pode ser null)
            ;

        List<Pedido> lista = entityManager.createQuery(
            jpql, Pedido.class)
            .getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }


    @Test
    public void resolvendoCom_EntityGraph(){

        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes("cliente", "notaFiscal", "pagamento");

        String jpql = "SELECT p FROM Pedido p ";

        List<Pedido> lista = entityManager.createQuery(
            jpql, Pedido.class)
            .setHint("javax.persistence.loadgraph", entityGraph)
            .getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));
    }


}
