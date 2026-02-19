package com.especialista.junit._10_JPQL;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.NotaFiscal;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.StatusPagamento;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Date;

public class _11_Passando_Parametros_para_as_Consultas extends EntityManagerTest {

    @Test
    public void passarParametro() { // Todos parâmetros criados no JPQL tem que ser especificado senão JPA solta EXCEPTION
        String jpql1 = "SELECT p FROM Pedido p "
            + " WHERE p.id = ?1"; // O 1 depois do ? não tem relação com a ordem dos parâmetros, e sim para especificar a posição

        logger.info("Buscando Pedido ...");
        TypedQuery<Pedido> typedQuery1 = entityManager.createQuery(jpql1, Pedido.class)
            .setParameter(1, 2);

        Pedido pedido = typedQuery1.getSingleResult();

        Assert.assertFalse(pedido.getItensPedido().isEmpty());

        logger.info("Pedido retornado...");
        logger.info(pedido.getId() + " - " + pedido.getStatus());
    }

    @Test
    public void passandoVariosParametros() { // Todos parâmetros criados no JPQL tem que ser especificado senão JPA solta EXCEPTION
        String jpql1 = "SELECT p FROM Pedido p "
            + " JOIN p.pagamento pag "
            + " WHERE p.id = ?10 " // O 10 depois do ? não tem relação com a ordem dos parâmetros, e sim para especificar a posição
            + " AND pag.status = ?11"; // O 11 depois do ? não tem relação com a ordem dos parâmetros, e sim para especificar a posição

        logger.info("Buscando Pedido ...");
        TypedQuery<Pedido> typedQuery1 = entityManager.createQuery(jpql1, Pedido.class)
            .setParameter(10, 2)
            .setParameter(11, StatusPagamento.PROCESSANDO);

        Pedido pedido = typedQuery1.getSingleResult();

        Assert.assertNotNull(pedido);

        logger.info("Pedido retornado...");
        logger.info("Id: " + pedido.getId() + ", Pagamento: " + pedido.getPagamento().getStatus());
    }


    @Test
    public void passandoVariosParametrosPeloNome() { // Todos parâmetros criados no JPQL tem que ser especificado senão JPA solta EXCEPTION
        String jpql1 = "SELECT p FROM Pedido p "
            + " JOIN p.pagamento pag "
            + " WHERE p.id = :pedidoId "
            + " AND pag.status = ?2"; // O 2 depois do ? não tem relação com a ordem dos parâmetros, e sim para especificar a posição

        logger.info("Buscando Pedido ...");
        TypedQuery<Pedido> typedQuery1 = entityManager.createQuery(jpql1, Pedido.class)
            .setParameter("pedidoId", 2)
            .setParameter(2, StatusPagamento.PROCESSANDO);

        Pedido pedido = typedQuery1.getSingleResult();

        Assert.assertNotNull(pedido);

        logger.info("Pedido retornado...");
        logger.info("Id: " + pedido.getId() + ", Pagamento: " + pedido.getPagamento().getStatus());
    }


    @Test
    public void passandoVariosParametroComDate() { // Todos parâmetros criados no JPQL tem que ser especificado senão JPA solta EXCEPTION
        String jpql1 = "SELECT nf FROM NotaFiscal nf "
            + " WHERE nf.dataEmissao <= ?11 "; // O 11 depois do ? não tem relação com a ordem dos parâmetros, e sim para especificar a posição

        logger.info("Buscando NotaFiscal ...");
        TypedQuery<NotaFiscal> typedQuery1 = entityManager.createQuery(jpql1, NotaFiscal.class)
            .setParameter(11, new Date(), TemporalType.TIMESTAMP); // TemporalType tem que ser o mesmo do @Temporal

//      OBS: O Banco de dados está salvando os registros no TIMEZONE UTC, e a aplicação está no TIMEZONE 'America/Sao_Paulo'

        NotaFiscal nota = typedQuery1.getSingleResult();

        logger.info("Buscando NotaFiscal...");
        Assert.assertNotNull(nota);

        logger.info("NotaFiscal retornado...");
        logger.info("Id: " + nota.getId() + ", Data de Emissão: " + nota.getDataEmissao());
    }

}
