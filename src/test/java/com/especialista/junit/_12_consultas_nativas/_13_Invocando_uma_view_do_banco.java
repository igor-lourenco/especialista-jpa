package com.especialista.junit._12_consultas_nativas;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class _13_Invocando_uma_view_do_banco extends EntityManagerTest {

/*  - Uma view é uma tabela virtual criada a partir de uma consulta SQL (SELECT).
      - Não armazena os dados fisicamente (na maioria dos casos), apenas mostra os dados que já existem em uma ou mais tabelas, conforme a consulta definida.
      - É uma consulta SQL salva no banco, que pode ser usada como se fosse uma tabela.
      - É uma consulta SQL salva no banco que se comporta como uma tabela virtual, usada para simplificar, proteger e padronizar o acesso aos dados.

    Ou seja:
      - A tabela guarda dados.
      - A view guarda uma consulta.
      - Toda vez que você consulta a view, o banco executa o SELECT que a define.

*/

    @Test
    public void executandoUmaViewretornandoEntidade(){

        String sql = "SELECT * "
            + " FROM view_clientes_acima_media"; //  Se não especificar todas as colunas para ser retornadas, solta Exception

        Query query = entityManager.createNativeQuery(sql, Cliente.class);

        List<Cliente> lista = query.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(String.format("Cliente => ID: %s, Nome: %s", c.getId(), c.getNome())));

    }


    @Test
    public void executandoUmaView(){

        String sql = "SELECT cli.id, cli.nome, SUM(ped.total) "
            + " FROM tb_pedido ped "
            + " JOIN view_clientes_acima_media cli ON cli.id = ped.cliente_id " // Juntando o retorno da view para ser usada em outra query
            + " GROUP BY ped.cliente_id";

        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> lista = query.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> logger.info(String.format("Cliente => ID: %s, Nome: %s, Total: %s", c)));

    }

}
