package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido_;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

public class _17_Usando_Expressao_Condicional_MAIOR_e_MENOR_com_Data extends EntityManagerTest {


    @Test
    public void usandoExpressaoCondicional_MAIOR_ou_IGUAL_E_MENOR_ou_IGUAL(){ // Busca Pedido entre 04/02/2026 14:25:00 e 09/02/2026 18:45:00

        fusoHorario();

//      2026-02-04T14:25:00Z → especifica que esse horário está no UTC, para ser exatamente esse horário que vai fazer a busca no banco de dados que está no UTC
        LocalDateTime dataCriacaoInicial = getDataCriacaoInicial();

//      2026-02-09T18:45:00Z → especifica que esse horário está no UTC, para ser exatamente esse horário que vai fazer a busca no banco de dados que está no UTC
        LocalDateTime dataCriacaoFinal = getDataCriacaoFinal();


//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Pedido p "
//          + " WHERE p.dataCriacao >= :dataCriacaoInicial "
//          + " AND p.dataCriacao <= :dataCriacaoFinal";


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // SELECT p

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p


        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).


        criteriaQuery.where(criteriaBuilder.and(
            criteriaBuilder.greaterThanOrEqualTo(root.get(Pedido_.dataCriacao), dataCriacaoInicial), // WHERE p.dataCriacao >= :dataCriacaoInicial
            criteriaBuilder.lessThanOrEqualTo(root.get(Pedido_.dataCriacao), dataCriacaoFinal) // AND p.dataCriacao <= :dataCriacaoFinal
        ));


        TypedQuery<Pedido> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
//            .setParameter("precoInicial", precoInicial)
//            .setParameter("precoFinal", precoFinal);
            entityManager.createQuery(criteriaQuery);

        logger.info("Busca Pedido entre 2026/02/04 14:25:00 e 2026/02/09 18:45:00");
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

    private static LocalDateTime getDataCriacaoInicial() {
        return LocalDateTime.ofInstant(
            ZonedDateTime.parse("2026-02-04T14:25:00Z[UTC]").toInstant(), // 2026-02-04T14:25:00Z → especifica que esse horário está no UTC, para ser exatamente esse horário que vai fazer a busca no banco de dados que está no UTC
            ZoneId.of("America/Sao_Paulo")); // 2026-02-04T11:25:00 → tira às 3 horas do fuso horário porque na consulta o Hibernate adiciona às 3 horas
    }

    private static LocalDateTime getDataCriacaoFinal() {
        return LocalDateTime.ofInstant(
            ZonedDateTime.parse("2026-02-09T18:45:00Z[UTC]").toInstant(),//  2026-02-09T18:45:00Z → especifica que esse horário está no UTC, para ser exatamente esse horário que vai fazer a busca no banco de dados que está no UTC
            ZoneId.of("America/Sao_Paulo")); // 2026-02-09T15:45:00 → tira às 3 horas do fuso horário porque na consulta o Hibernate adiciona às 3 horas
    }

}
