package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class _47_Externalizando_queries_em_arquivo_XML extends EntityManagerTest {


/*    - Para externalizar, o JPQL tem que ser Named Querie e o arquivo XML tem que ser criado em META-INF
        - Por padrão o nome do arquivo é o orm.xml
        - E para criar arquivos XML específicos tem que criar a partir do META-INF e ser especificado no persistence.xml na tag <mapping-file>
 */
    
    @Test // usando o arquivo orm.xml
    public void executarArquivoExterno() {

        TypedQuery<Pedido> typedQuery = entityManager.createNamedQuery("Pedido.listar", Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));

    }


    @Test  // usando o arquivo consultas/produto.xml criado em META-INF e especificado no persistence.xml na tag <mapping-file>
    public void executarArquivoExternoEspecifico1() {

        TypedQuery<Pedido> typedQuery = entityManager.createNamedQuery("Pedido.buscarTodos", Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId()));

    }


    @Test // usando o arquivo consultas/produto.xml criado em META-INF e especificado no persistence.xml na tag <mapping-file>
    public void executarArquivoExternoEspecifico2() {

        TypedQuery<Produto> typedQuery = entityManager.createNamedQuery("Produto.buscarTodos", Produto.class);

        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info("ID: " + c.getId() + ", NOME: " + c.getNome()));

    }
}
