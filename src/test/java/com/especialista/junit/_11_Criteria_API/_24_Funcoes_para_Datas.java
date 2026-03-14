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
import java.util.List;

public class _24_Funcoes_para_Datas extends EntityManagerTest {


    @Test
    public void usando_CURRENT_DATE_CURRENT_TIME_CURRENT_TIMESTAMP(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p.id, CURRENT_DATE, CURRENT_TIME, CURRENT_TIMESTAMP "
//          + " FROM Pedido p "
//          + " WHERE p.id = 1";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // SELECT p

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p


        criteriaQuery.multiselect(
            root.get(Pedido_.id),
            criteriaBuilder.currentDate(),
            criteriaBuilder.currentTime(),
            criteriaBuilder.currentTimestamp()
        );


        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery)
                .setMaxResults(1);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> {
            logger.info("pedidoId: " + c[0]
                + ",\n Data atual (America/Sao_Paulo): " + c[1] + ", como o sistema está no fuso horário de America_Sao_Paulo, e a data não tem as horas, O Java tira as 3 horas e por isso fica como um dia a menos "
                + ",\n Hora atual: " + c[2]
                + ",\n Data e hora atual (America/Sao_Paulo): " + c[3]

            );
        });
    }

    @Test
    public void usando_naClausulaWHERE(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p.id, CURRENT_TIMESTAMP, p.dataCriacao "
//          + " FROM Pedido p "
//          + " WHERE p.dataCriacao < CURRENT_TIMESTAMP ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // SELECT p

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p


        criteriaQuery.multiselect(
            root.get(Pedido_.id),
            root.get(Pedido_.dataCriacao),
            criteriaBuilder.currentTimestamp()
        );

        criteriaQuery.where(
            criteriaBuilder.lessThan( // WHERE p.dataCriacao < CURRENT_TIMESTAMP
                root.get(Pedido_.dataCriacao).as(java.sql.Date.class),
                criteriaBuilder.currentTimestamp().as(java.sql.Date.class)
            )
        );


        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery)
                .setMaxResults(1);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> {
            logger.info("pedidoId: " + c[0]
                + ",\n dataCriacao (UTC): " + c[1]
                + ",\n dataAtual (America/Sao_Paulo): " + c[2]

            );
        });
    }
    @Test
    public void pegandoANoMesEDiaDaData(){ // Pegando o DIA, MES e ANO da dataCriacao...
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT YEAR(p.dataCriacao), MONTH(p.dataCriacao), DAY(p.dataCriacao) "
//          + " FROM Pedido p "
//          + " WHERE p.dataCriacao < CURRENT_TIMESTAMP ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // SELECT p

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p


        criteriaQuery.multiselect(
            criteriaBuilder.function("YEAR", Integer.class, root.get(Pedido_.dataCriacao)), // YEAR(p.dataCriacao)
            criteriaBuilder.function("MONTH", Integer.class, root.get(Pedido_.dataCriacao)), //  MONTH(p.dataCriacao)
            criteriaBuilder.function("DAY", Integer.class, root.get(Pedido_.dataCriacao)), // DAY(p.dataCriacao)
            root.get(Pedido_.dataCriacao)
        );

        criteriaQuery.where(
            criteriaBuilder.lessThan( // WHERE p.dataCriacao < CURRENT_TIMESTAMP
                root.get(Pedido_.dataCriacao).as(java.sql.Date.class),
                criteriaBuilder.currentTimestamp().as(java.sql.Date.class)
            )
        );


        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery)
                .setMaxResults(1);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> {
            logger.info("dataCriacao (UTC): " + c[3]
                + ",\n Dia: " + c[2]
                + ",\n Mês: " + c[1]
                + ",\n Ano: " + c[0]

            );
        });
    }


    @Test
    public void pegandoHoraMinutoESegundo(){  // Pegando o HORA, MINUTO e SEGUNDO da dataCriacao...
//      Convertendo uma JPQL em Criteria Query
//        String jpql = "SELECT HOUR(p.dataCriacao), MINUTE(p.dataCriacao), SECOND(p.dataCriacao) "
//            + " FROM Pedido p "
//            + " WHERE p.dataCriacao < CURRENT_TIMESTAMP ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // SELECT p

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p


        criteriaQuery.multiselect(
            criteriaBuilder.function("HOUR", Integer.class, root.get(Pedido_.dataCriacao)), // HOUR(p.dataCriacao)
            criteriaBuilder.function("MINUTE", Integer.class, root.get(Pedido_.dataCriacao)), //   MINUTE(p.dataCriacao)
            criteriaBuilder.function("SECOND", Integer.class, root.get(Pedido_.dataCriacao)), // SECOND(p.dataCriacao)
            root.get(Pedido_.dataCriacao)
        );

        criteriaQuery.where(
            criteriaBuilder.lessThan( // WHERE p.dataCriacao < CURRENT_TIMESTAMP
                root.get(Pedido_.dataCriacao).as(java.sql.Date.class),
                criteriaBuilder.currentTimestamp().as(java.sql.Date.class)
            )
        );


        TypedQuery<Object[]> typedQuery =
//          entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery)
                .setMaxResults(1);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> {
            logger.info("dataCriacao (UTC): " + c[3]
                + ",\n Hora: " + c[0]
                + ",\n Minuto: " + c[1]
                + ",\n Segundo: " + c[2]

            );
        });
    }

}
