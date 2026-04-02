package com.especialista.junit._13_Bean_Validation_Pool_de_conexoes_Entity_Graph_e_detalhes_avancados._5_EntityGraph._4_One_To_One_como_LAZY;

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

public class _1_Configurando_Entity_Graph extends EntityManagerTest {

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
    public void semUsar_EntityGraph(){

        logger.info("BUSCANDO UM PEDIDO:");
        logger.info("Dessa forma também está retornando: Cliente e Pagamento");

        Pedido pedido = entityManager // Não está trazendo NotaFiscal porque foi configurado para não retornar usando a interface: PersistentAttributeInterceptable
            .find(Pedido.class, 1);

        Assert.assertNotNull(pedido);
        logger.info("PEDIDO => " + pedido.toString());

        logger.info("------------------------------------------------------------");
        logger.info("BUSCANDO LISTA DE PEDIDO:");
        logger.info("Dessa forma está fazendo consulta separada para buscar: Cliente e Pagamento");

        List<Pedido> lista2 = entityManager // Não está trazendo NotaFiscal porque foi configurado para não retornar usando a interface: PersistentAttributeInterceptable
            .createQuery("SELECT p FROM Pedido p", Pedido.class)
            .getResultList();

        Assert.assertFalse(lista2.isEmpty());
        logger.info("PEDIDO => " + pedido.toString());
    }


    @Test
    public void usando_EntityGraph(){
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes( // vai trazer todas os atributos simples (que não seja de mapeamento) mesmo sem especificar
            "dataCriacao",
            "status",
            "total",
//            "cliente", // como o owner da relação está em Pedido, tem que informar fetch = FetchType.LAZY no atributo cliente
            "pagamento" // para não trazer Pagamento tem que configurar o PersistentAttributeInterceptable da mesma forma que foi feita com NotaFiscal
        );

        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
//        properties.put("javax.persistence.loadgraph", entityGraph);

        logger.info("BUSCANDO UM PEDIDO:");
        logger.info("Dessa forma também está retornando apenas Pagamento");

        Pedido pedido = entityManager // Não está trazendo NotaFiscal porque foi configurado para não retornar usando a interface: PersistentAttributeInterceptable
            .find(Pedido.class, 1, properties);

        Assert.assertNotNull(pedido);
        logger.info("PEDIDO => " + pedido.toString());
    }


    @Test
    public void usando_EntityGraph_com_JPQL(){
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes( // vai trazer todas os atributos simples (que não seja de mapeamento) mesmo sem especificar
            "dataCriacao",
            "status",
            "total",
//            "cliente", // como o owner da relação está em Pedido, tem que informar fetch = FetchType.LAZY no atributo cliente
            "pagamento" // para não trazer Pagamento tem que configurar o PersistentAttributeInterceptable da mesma forma que foi feita com NotaFiscal
        );

        logger.info("BUSCANDO LISTA DE PEDIDO:");
        logger.info("Dessa forma está fazendo consulta com Pagamento, sem ficar fazendo consultas separadas para buscar Pagamento");

        TypedQuery<Pedido> typedQuery = entityManager // Não está trazendo NotaFiscal porque foi configurado para não retornar usando a interface: PersistentAttributeInterceptable
            .createQuery("SELECT p FROM Pedido p", Pedido.class)
            .setHint("javax.persistence.fetchgraph", entityGraph);
//          .setHint("javax.persistence.loadgraph", entityGraph);

        List<Pedido> lista2 = typedQuery.getResultList();

        Assert.assertFalse(lista2.isEmpty());
        logger.info("PEDIDO => " + lista2.get(0).toString());
    }


    @Test
    public void usando_EntityGraph_com_Criteria_API(){
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes( // vai trazer todas os atributos simples (que não seja de mapeamento) mesmo sem especificar
            "dataCriacao",
            "status",
            "total",
//            "cliente", // como o owner da relação está em Pedido, tem que informar fetch = FetchType.LAZY no atributo cliente
            "pagamento" // para não trazer Pagamento tem que configurar o PersistentAttributeInterceptable da mesma forma que foi feita com NotaFiscal
        );

        CriteriaQuery<Pedido> criteriaQuery = getCriteriaQuery();

        logger.info("BUSCANDO LISTA DE PEDIDO:");
        logger.info("Dessa forma está fazendo consulta com Pagamento, sem ficar fazendo consultas separadas para buscar Pagamento");

        TypedQuery<Pedido> typedQuery = entityManager // Não está trazendo NotaFiscal porque foi configurado para não retornar usando a interface: PersistentAttributeInterceptable
            .createQuery(criteriaQuery)
            .setHint("javax.persistence.fetchgraph", entityGraph);
//          .setHint("javax.persistence.loadgraph", entityGraph);

        List<Pedido> lista2 = typedQuery.getResultList();

        Assert.assertFalse(lista2.isEmpty());
        logger.info("PEDIDO => " + lista2.get(0).toString());
    }

}
