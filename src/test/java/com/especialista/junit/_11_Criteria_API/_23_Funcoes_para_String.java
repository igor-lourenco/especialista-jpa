package com.especialista.junit._11_Criteria_API;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Cliente_;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class _23_Funcoes_para_String extends EntityManagerTest {


    @Test
    public void aplicando_Funcoes_String_com_WHERE_Usando_Tuple(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery(); // SELECT c

        Root<Cliente> root = criteriaQuery.from(Cliente.class); // FROM Cliente c


        criteriaQuery.select(criteriaBuilder.tuple(
            root.get(Cliente_.nome).alias("nomeDoCliente"),
            criteriaBuilder.concat("Nome do cliente -> ", root.get(Cliente_.nome)).alias("concat"),
            criteriaBuilder.length(root.get(Cliente_.nome)).alias("length"),
            criteriaBuilder.locate(root.get(Cliente_.nome), "a").alias("locate"),
            criteriaBuilder.substring(root.get(Cliente_.nome), 1, 2).alias("substring"),
            criteriaBuilder.lower(root.get(Cliente_.nome)).alias("lower"),
            criteriaBuilder.upper(root.get(Cliente_.nome)).alias("upper"),
            criteriaBuilder.trim(root.get(Cliente_.nome)).alias("trim")
        ));

        criteriaQuery.where( // WHERE SUBSTRING(c.nome, 1, 4) = 'JO'
            criteriaBuilder.equal(
                criteriaBuilder.substring(root.get(Cliente_.nome), 1, 2),
                "JO"
            )
        );


        TypedQuery<Tuple> typedQuery =
//            entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery);


        List<Tuple> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> {
            logger.info(c.get("nomeDoCliente")
            + ",\n concat: " + c.get("concat")
            + ",\n length: " + c.get("length")
            + ",\n locate 'a': " + c.get("locate")
            + ",\n substring 1° posição até 2°: " + c.get("substring")
            + ",\n lower: " + c.get("lower")
            + ",\n upper: " + c.get("upper")
            + ",\n trim: |" + c.get("trim") + "|"

            );
        });
    }


    @Test
    public void aplicando_Funcoes_String_com_WHERE(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // SELECT c

        Root<Cliente> root = criteriaQuery.from(Cliente.class); // FROM Cliente c


        criteriaQuery.multiselect(
            root.get(Cliente_.nome),
            criteriaBuilder.concat("Nome do cliente -> ", root.get(Cliente_.nome)),
            criteriaBuilder.length(root.get(Cliente_.nome)),
            criteriaBuilder.locate(root.get(Cliente_.nome), "a"),
            criteriaBuilder.substring(root.get(Cliente_.nome), 1, 2),
            criteriaBuilder.lower(root.get(Cliente_.nome)),
            criteriaBuilder.upper(root.get(Cliente_.nome)),
            criteriaBuilder.trim(root.get(Cliente_.nome))
        );

        criteriaQuery.where( // WHERE SUBSTRING(c.nome, 1, 4) = 'JO'
            criteriaBuilder.equal(criteriaBuilder.substring(root.get(Cliente_.nome), 1, 2), "JO"));


        TypedQuery<Object[]> typedQuery =
//            entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> {
            logger.info(c[0]
            + ",\n concat: " + c[1]
            + ",\n length: " + c[2]
            + ",\n locate 'a': " + c[3]
            + ",\n substring 1° posição até 2°: " + c[4]
            + ",\n lower: " + c[5]
            + ",\n upper: " + c[6]
            + ",\n trim: |" + c[7] + "|"

            );
        });
    }


    @Test
    public void aplicando_Funcoes_String(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); // SELECT c

        Root<Cliente> root = criteriaQuery.from(Cliente.class); // FROM Cliente c


        criteriaQuery.multiselect(
            root.get(Cliente_.nome),
            criteriaBuilder.concat("Nome do cliente -> ", root.get(Cliente_.nome)),
            criteriaBuilder.length(root.get(Cliente_.nome)),
            criteriaBuilder.locate(root.get(Cliente_.nome), "a"),
            criteriaBuilder.substring(root.get(Cliente_.nome), 1, 2),
            criteriaBuilder.lower(root.get(Cliente_.nome)),
            criteriaBuilder.upper(root.get(Cliente_.nome)),
            criteriaBuilder.trim(root.get(Cliente_.nome))
        );


        TypedQuery<Object[]> typedQuery =
//            entityManager.createQuery(jpql, Object[].class)
            entityManager.createQuery(criteriaQuery);


        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        logger.info("Resultado: " + lista.size());
        lista.forEach(c -> {
            logger.info(c[0]
            + ",\n concat: " + c[1]
            + ",\n length: " + c[2]
            + ",\n locate 'a': " + c[3]
            + ",\n substring 1° posição até 2°: " + c[4]
            + ",\n lower: " + c[5]
            + ",\n upper: " + c[6]
            + ",\n trim: |" + c[7] + "|"

            );
        });

    }


}
