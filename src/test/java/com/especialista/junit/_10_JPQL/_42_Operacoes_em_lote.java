package com.especialista.junit._10_JPQL;

import com.especialista.jpa._2_iniciandoComJPA.modelo.ProdutoIniciandoComJPA;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class _42_Operacoes_em_lote extends EntityManagerTest {


    @Test
    public void inserirEmLote() throws IOException {

        final String resourcePath = "META-INF/banco-de-dados/registros_para_operacao_em_lote.txt";
        final int LIMITE_INSERCOES = 50;


        InputStream in = _42_Operacoes_em_lote.class.getClassLoader()
            .getResourceAsStream(resourcePath);


        if (in == null) throw new FileNotFoundException("Recurso não encontrado no classpath: " + resourcePath);

        entityManager.getTransaction().begin(); // Início da transação

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {

            int contador = 0;

            for (String linha : reader.lines().toList()) {

                if (linha.isBlank()) continue;

                String[] coluna = linha.split(",");
                ProdutoIniciandoComJPA produto = new ProdutoIniciandoComJPA();
                produto.setId(Integer.valueOf(coluna[0]));
                produto.setDescricao(coluna[1]);
                produto.setNome(coluna[2]);
                produto.setPreco(new BigDecimal(coluna[3]));

                logger.info("ID: " + produto.getId());
                entityManager.persist(produto); // Persistindo novo produto banco de dados...

                contador++;
                if (contador == LIMITE_INSERCOES) {
                    entityManager.flush();  // Sincronizando as alterações feitas na entidade com o banco de dados
                    entityManager.clear(); // Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...

                    contador = 0;
                    logger.info("==================================================");
                }

            }
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback(); // se der ruim dá rollback

            throw e;
        }

        entityManager.getTransaction().commit(); // JPA confirmando a transação, salvando as alterações no banco de dados...

    }
}
