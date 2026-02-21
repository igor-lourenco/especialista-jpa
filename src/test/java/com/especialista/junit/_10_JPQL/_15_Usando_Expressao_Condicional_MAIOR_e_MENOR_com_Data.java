package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

public class _15_Usando_Expressao_Condicional_MAIOR_e_MENOR_com_Data extends EntityManagerTest {


    @Test
    public void usandoExpressaoCondicional_MIOR_ou_IGUAL_E_MENOR_ou_IGUAL() { // Busca Produto com preco maior ou igual a 1.00 e menor ou igual a 50.00

        ZoneId zoneId = ZoneId.systemDefault();
        logger.info("\nFuso horário(time-zone) padrão do sistema: " + zoneId);

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        logger.info("Alterando o fuso padrão do sistema para UTC...");

        zoneId = ZoneId.systemDefault();
        logger.info("Fuso horário(time-zone) padrão do sistema atualizado: " + zoneId);

        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        zoneId = ZoneId.systemDefault();
        logger.info("Alterando o fuso padrão do sistema de volta para America/Sao_Paulo: " + zoneId);

        String jpql = "SELECT p FROM Pedido p "
            + " WHERE p.dataCriacao >= :dataCriacaoInicial "
            + " AND p.dataCriacao <= :dataCriacaoFinal";

//      2026-02-04T14:25:00Z → especifica que esse horário está no UTC, para ser exatamente esse horário que vai fazer a busca no banco de dados que está no UTC
        LocalDateTime dataCriacaoInicial = LocalDateTime.ofInstant(
            ZonedDateTime.parse("2026-02-04T14:25:00Z[UTC]").toInstant(),
            ZoneId.of("America/Sao_Paulo")); // 2026-02-04T11:25:00 → tira às 3 horas do fuso horário porque na consulta o Hibernate adiciona às 3 horas


//      2026-02-04T14:25:00Z → especifica que esse horário está no UTC, para ser exatamente esse horário que vai fazer a busca no banco de dados que está no UTC
        LocalDateTime dataCriacaoFinal = LocalDateTime.ofInstant(
            ZonedDateTime.parse("2026-02-09T18:45:00Z[UTC]").toInstant(),
            ZoneId.of("America/Sao_Paulo")); // 2026-02-04T11:25:00 → tira às 3 horas do fuso horário porque na consulta o Hibernate adiciona às 3 horas


        logger.info("Buscando Produto ...");
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class)
            .setParameter("dataCriacaoInicial", dataCriacaoInicial) // Todos parâmetros criados no JPQL tem que ser especificado senão JPA solta EXCEPTION
            .setParameter("dataCriacaoFinal", dataCriacaoFinal);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("======================================================================================");
        logger.info("Horário que está retornando horário do banco de dados em UTC mas o Hibernate converteu com o fuso horário para padrão do sistema: America/Sao_Paulo");
        lista
            .stream().sorted(Comparator.comparing(Pedido::getDataCriacao))
            .forEach(p -> {
            logger.info("Id: " + p.getId() + ", dataCriacao: " + p.getDataCriacao());
        });


        logger.info("======================================================================================");
        logger.info("Convertendo horário de volta para UTC do mesmo jeito que está no banco de dados");
        lista
            .stream().sorted(Comparator.comparing(Pedido::getDataCriacao))
            .forEach(p -> {

                ZonedDateTime zonedDateTime = p.getDataCriacao().atZone(ZoneId.of("America/Sao_Paulo"));
                Instant instant = zonedDateTime.toInstant();
                LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));

                logger.info("Id: " + p.getId() + ", dataCriacao: " + dateTime);
        });
    }


}
