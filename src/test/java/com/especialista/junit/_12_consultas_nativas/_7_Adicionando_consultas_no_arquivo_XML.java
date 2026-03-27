package com.especialista.junit._12_consultas_nativas;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Categoria;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class _7_Adicionando_consultas_no_arquivo_XML extends EntityManagerTest {

/*    - Para externalizar, o SQL tem que ser Named Native Querie e o arquivo XML tem que ser criado em META-INF
        - Por padrão o nome do arquivo é o orm.xml
        - E para criar arquivos XML específicos tem que criar a partir do META-INF e ser especificado no persistence.xml na tag <mapping-file>
 */

    @Test // usando o arquivo consultas/categoria.xml criado em META-INF e especificado no persistence.xml na tag <mapping-file>
    public void usando_NamedNativeQuery1(){

        Query query = entityManager.createNamedQuery("tb_categoria.buscarTodos");

        List<Categoria> lista = query.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(String.format("Categoria => ID: %s, Nome: %s", c.getId(), c.getNome())));
    }

}
