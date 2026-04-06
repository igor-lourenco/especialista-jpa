package com.especialista.junit._14_Second_Level_Cache_cache_compartilhado;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Map;

public class _6_Controle_dinamico_do_cache {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    protected static EntityManagerFactory entityManagerFactory;

/*   - javax.persistence.cache.retrieveMode → Define se o JPA pode usar o cache de 2° nivel para leitura ou não, controla LEITURA do cache de 2º nível

        - CacheRetrieveMode.USE:
           - Permite buscar entidades no cache de 2° nível
           - Se encontrar no cache → não vai ao banco
           - Se não encontrar → vai ao banco normalmente

        - CacheRetrieveMode.BYPASS:
           - Ignora completamente o cache de 2° nível
           - Sempre busca no banco
           - Atualiza o cache depois (a menos que o storeMode impeça)


     - javax.persistence.cache.storeMode → Define se o JPA deve GRAVAR dados no cache de 2° nível após buscar do banco

         - CacheStoreMode.USE:
           - Grava normalmente no cache 2° nivel
           - Respeita a política/configuração do provider
           - Comportamento padrão do JPA

         - CacheStoreMode.BYPASS:
           - Não grava o resultado no cache 2° nível
           - A query ainda pode ir ao banco
           - O cache permanece inalterado

         - CacheStoreMode.REFRESH
           - Vai ao banco
           - Sobrescreve/atualiza o cache 2° nível
           - Ignora qualquer valor antigo no cache
*/

    @Test
    public void analisarOpcoesDeCache(){


        Cache cache = entityManagerFactory.getCache();

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        entityManager1.setProperty("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS); // todas as consultas não vão ser armazenadas no cache

        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        System.out.println();

        logger.info(">>>>> Buscando todos a partir da instância 1");
        entityManager1
            .createQuery("SELECT p FROM Pedido p",Pedido.class)
            // sobrepõe a configuração anterior do entityManager1 e vai no banco e sobrescreve/atualiza o cache 2° nível
            .setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH)
            .getResultList();


        logger.info(">>>>> Verificando se o Pedido 1 da instância 1 está no cache: "
            + cache.contains(Pedido.class, 1));

        logger.info(">>>>> Buscando Pedido do ID 2 partir da instância 2");

        Map<String, Object> propriedades = Map.of("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE); // permite buscar entidades no cache de 2° nível
        entityManager2.find(Pedido.class, 1, propriedades);

        logger.info(">>>>> Verificando se o Pedido 1 da instância 1 está no cache: "
            + cache.contains(Pedido.class, 1));

        entityManager1.close();
        entityManager2.close();
    }


    @BeforeClass
    public static void setUpBeforeClass() { // executa primeiro
        System.out.println(">>> Iniciando o EntityManagerFactory...");
        entityManagerFactory = Persistence.createEntityManagerFactory("EspecialistaJPADB-PU");

    }

    @AfterClass
    public static void tearDownAfterClass() { // executa por ultimo
        System.out.println(">>> Finalizando o EntityManagerFactory...");
        entityManagerFactory.close();
    }

}
