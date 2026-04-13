package com.especialista.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class ExecutarDDL {

    public static void main(String[] args) {
        Map<String, String> propriedades = new HashMap<>();


//        ==================================================================================================================================
//        =================                   Executa diretamente no banco         =========================================================
//        ======= Essas configurações sobrescrevem as que estão definidas no persistence.xml para a mesma unidade de persistência ==========
//        ==================================================================================================================================

//      >>>> Executa no banco: primeiro DROP do esquema existente, depois CREATE com base nas entidades.
//        propriedades.put("javax.persistence.schema-generation.database.action", "drop-and-create");

//      >>>> Usa metadata das entidades e depois aplica scripts adicionais (se existirem).
//        propriedades.put("javax.persistence.schema-generation.create-source", "metadata-then-script");
//        propriedades.put("javax.persistence.schema-generation.drop-source", "metadata-then-script");

//      >>>> Scripts customizados que serão aplicados junto com metadata.
//        propriedades.put("javax.persistence.schema-generation.create-script-source", "META-INF/banco-de-dados/script-criacao.sql");
//        propriedades.put("javax.persistence.schema-generation.drop-script-source", "META-INF/banco-de-dados/script-remocao.sql");

//      >>>> Script para inserir dados iniciais após a criação do esquema.
//        propriedades.put("javax.persistence.sql-load-script-source", "META-INF/banco-de-dados/dados-iniciais.sql");

//        ==================================================================================================================================
//        =================                   Executa diretamente no banco         =========================================================
//        =================    Configurações para criar um novo schema para usar no Multitenancy ===========================================
//        ==================================================================================================================================

        propriedades.put("javax.persistence.jdbc.url",
            "jdbc:mysql://192.168.0.54:3307/especialistajpadb_multitenancy?createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=UTC");

        propriedades.put("javax.persistence.schema-generation.database.action",
            "drop-and-create");

        propriedades.put("javax.persistence.schema-generation.create-source",
            "metadata-then-script");
        propriedades.put("javax.persistence.schema-generation.drop-source",
            "metadata-then-script");

        propriedades.put("javax.persistence.schema-generation.create-script-source",
            "META-INF/banco-de-dados/script-criacao.sql");
        propriedades.put("javax.persistence.schema-generation.drop-script-source",
            "META-INF/banco-de-dados/script-remocao.sql");

        propriedades.put("javax.persistence.sql-load-script-source",
            "META-INF/banco-de-dados/dados-iniciais.sql");

        EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("EspecialistaJPADB-PU", propriedades);

        entityManagerFactory.close();
    }
}
