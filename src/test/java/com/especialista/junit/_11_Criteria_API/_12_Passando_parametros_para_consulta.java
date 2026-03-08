package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.NotaFiscal;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class _12_Passando_parametros_para_consulta extends EntityManagerTest {

    @Test
    public void passando_Parametros(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Pedido p "
//            + " WHERE p.id = ?1"; // O 1 depois do ? não tem relação com a ordem dos parâmetros, e sim para especificar a posição

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // SELECT p

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p


        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).


//      criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 2)); // WHERE prod.id = ?1
        ParameterExpression<Integer> parameterExpression = criteriaBuilder.parameter(Integer.class); // O mesmo que acima mas usando o ParameterExpression
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), parameterExpression));

        TypedQuery<Pedido> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
//              .setParameter(1, 2);
            entityManager.createQuery(criteriaQuery)
                .setParameter(parameterExpression, 2);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> {
            logger.info(a.toString());
        });
    }


    @Test
    public void passando_Parametros_Pelo_Nome(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT p FROM Pedido p "
//            + " WHERE p.id = :pedidoId"; // O 1 depois do ? não tem relação com a ordem dos parâmetros, e sim para especificar a posição

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); // SELECT p

        Root<Pedido> root = criteriaQuery.from(Pedido.class); // FROM Pedido p


        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).


//      criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 2)); // WHERE prod.id = :pedidoId
        ParameterExpression<Integer> parameterExpression = criteriaBuilder.parameter(Integer.class, "pedidoId"); // O mesmo que acima mas usando o ParameterExpression
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), parameterExpression));

        TypedQuery<Pedido> typedQuery =
//          entityManager.createQuery(jpql, Pedido.class)
//              .setParameter("pedidoId", 2);
            entityManager.createQuery(criteriaQuery)
                .setParameter("pedidoId", 2);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> {
            logger.info(a.toString());
        });
    }


    @Test
    public void passando_Parametros_Pelo_Nome_com_Date(){
//      Convertendo uma JPQL em Criteria Query
//      String jpql = "SELECT nf FROM NotaFiscal nf "
//         + " WHERE nf.dataEmissao <= ?11 "; // O 11 depois do ? não tem relação com a ordem dos parâmetros, e sim para especificar a posição

        Calendar dataInicial = Calendar.getInstance();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<NotaFiscal> criteriaQuery = criteriaBuilder.createQuery(NotaFiscal.class); // SELECT nf

        Root<NotaFiscal> root = criteriaQuery.from(NotaFiscal.class); // FROM NotaFiscal nf

        criteriaQuery.select(root); // quando o tipo de retorno do CriteriaQuery é o mesmo que o tipo do Root, não é obrigado a chamar select(root).


//      criteriaQuery.where(criteriaBuilder.lessThanOrEqualTo(root.get("dataEmissao"), dataInicial.getTime())); //  WHERE nf.dataEmissao <= ?11
        ParameterExpression<Date> parameterExpression = criteriaBuilder.parameter(Date.class, "dataInicial"); // O mesmo que acima mas usando o ParameterExpression
        criteriaQuery.where(criteriaBuilder.lessThanOrEqualTo(root.get("dataEmissao"), parameterExpression));


        TypedQuery<NotaFiscal> typedQuery =
//          entityManager.createQuery(jpql, NotaFiscal.class)
//              .setParameter(11, new Date(), TemporalType.TIMESTAMP); // TemporalType tem que ser o mesmo do @Temporal
            entityManager.createQuery(criteriaQuery)
                .setParameter("dataInicial", dataInicial.getTime(), TemporalType.TIMESTAMP);

        List<NotaFiscal> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(a -> {
            logger.info(a.toString());
        });
    }



}
