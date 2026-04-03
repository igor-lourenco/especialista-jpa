package com.especialista.junit._13_Bean_Validation_Pool_de_conexoes_Entity_Graph_e_detalhes_avancados._5_EntityGraph;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityGraph;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _4_Configurando_Entity_Graph_com_Anotacao extends EntityManagerTest {

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
    public void usando_EntityGraph_com_anotacao_NamedEntityGraph(){
        EntityGraph<?> entityGraph = entityManager
            .createEntityGraph("Pedido.dadosEssenciais"); // EntityGraph configurado na entidade Pedido

        entityGraph.addSubgraph("pagamento")
            .addAttributeNodes("status");

        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);

//        properties.put("javax.persistence.loadgraph", entityGraph);

        logger.info("BUSCANDO UM PEDIDO:");
        logger.info("Dessa forma também está retornando Pagamento e Cliente");

        Pedido pedido = entityManager // Não está trazendo NotaFiscal porque foi configurado para não retornar usando a interface: PersistentAttributeInterceptable
            .find(Pedido.class, 1, properties);

        Assert.assertNotNull(pedido);
        logger.info("PEDIDO => " + pedido.toString());
        logger.info("NOME CLIENTE => " + pedido.getCliente().getNome());
        logger.info("CPF CLIENTE => " + pedido.getCliente().getCpf());
        logger.info("PAGAMENTO => " + pedido.getPagamento().getStatus());
    }


    @Test
    public void usando_EntityGraph_com_JPQL_com_anotacao_NamedEntityGraph(){
        EntityGraph<?> entityGraph = entityManager
            .createEntityGraph("Pedido.dadosEssenciais"); // EntityGraph configurado na entidade Pedido

        entityGraph.addSubgraph("pagamento")
            .addAttributeNodes("status");

        logger.info("BUSCANDO LISTA DE PEDIDO:");
        logger.info("Dessa forma está fazendo consulta com Pagamento e Cliente, sem ficar fazendo consultas separadas para buscar Pagamento e Cliente");

        TypedQuery<Pedido> typedQuery = entityManager // Não está trazendo NotaFiscal porque foi configurado para não retornar usando a interface: PersistentAttributeInterceptable
            .createQuery("SELECT p FROM Pedido p", Pedido.class)
            .setHint("javax.persistence.fetchgraph", entityGraph);
//          .setHint("javax.persistence.loadgraph", entityGraph);

        List<Pedido> lista2 = typedQuery.getResultList();

        Assert.assertFalse(lista2.isEmpty());
        logger.info("PEDIDO => " + lista2.get(0).toString());
        logger.info("NOME CLIENTE => " +  lista2.get(0).getCliente().getNome());
        logger.info("CPF CLIENTE => " +  lista2.get(0).getCliente().getCpf());
        logger.info("PAGAMENTO => " + lista2.get(0).getPagamento().getStatus());

    }


    @Test
    public void usando_EntityGraph_com_Criteria_API_com_anotacao_NamedEntityGraph(){
        EntityGraph<?> entityGraph = entityManager
            .createEntityGraph("Pedido.dadosEssenciais"); // EntityGraph configurado na entidade Pedido

        entityGraph.addSubgraph("pagamento")
            .addAttributeNodes("status");

        CriteriaQuery<Pedido> criteriaQuery = getCriteriaQuery();

        logger.info("BUSCANDO LISTA DE PEDIDO:");
        logger.info("Dessa forma está fazendo consulta com Pagamento e Cliente, sem ficar fazendo consultas separadas para buscar Pagamento e Cliente");

        TypedQuery<Pedido> typedQuery = entityManager // Não está trazendo NotaFiscal porque foi configurado para não retornar usando a interface: PersistentAttributeInterceptable
            .createQuery(criteriaQuery)
            .setHint("javax.persistence.fetchgraph", entityGraph);
//          .setHint("javax.persistence.loadgraph", entityGraph);

        List<Pedido> lista2 = typedQuery.getResultList();

        Assert.assertFalse(lista2.isEmpty());
        logger.info("PEDIDO => " + lista2.get(0).toString());
        logger.info("NOME CLIENTE => " +  lista2.get(0).getCliente().getNome());
        logger.info("CPF CLIENTE => " +  lista2.get(0).getCliente().getCpf());
    }

}
