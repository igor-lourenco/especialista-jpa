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

public class _16_Usando_Expressao_Condicional_BETWEEN extends EntityManagerTest {


    @Test
    public void usandoExpressaoCondicional_BETWEEN() { // Busca Pedido entre 04/02/2026 14:25:00 e 09/02/2026 18:45:00

        fusoHorario();

        String jpql = "SELECT p FROM Pedido p "
//          + " WHERE p.dataCriacao >= :dataCriacaoInicial AND p.dataCriacao <= :dataCriacaoFinal";
            + " WHERE p.dataCriacao BETWEEN :dataCriacaoInicial AND :dataCriacaoFinal"; // Mesma coisa que acima usando o >= e <=, porém os sinais são mais flexíveis


//      2026-02-04T14:25:00Z → especifica que esse horário está no UTC, para ser exatamente esse horário que vai fazer a busca no banco de dados que está no UTC
        LocalDateTime dataCriacaoInicial = LocalDateTime.ofInstant(
            ZonedDateTime.parse("2026-02-04T14:25:00Z[UTC]").toInstant(),
            ZoneId.of("America/Sao_Paulo")); // 2026-02-04T11:25:00 → tira às 3 horas do fuso horário porque na consulta o Hibernate adiciona às 3 horas


//      2026-02-09T18:45:00Z → especifica que esse horário está no UTC, para ser exatamente esse horário que vai fazer a busca no banco de dados que está no UTC
        LocalDateTime dataCriacaoFinal = LocalDateTime.ofInstant(
            ZonedDateTime.parse("2026-02-09T18:45:00Z[UTC]").toInstant(),
            ZoneId.of("America/Sao_Paulo")); // 2026-02-09T15:45:00 → tira às 3 horas do fuso horário porque na consulta o Hibernate adiciona às 3 horas


        logger.info("Busca Pedido entre 2026/02/04 14:25:00 e 2026/02/09 18:45:00");
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
