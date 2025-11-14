package com.especialista.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class ExportarDDL {

    public static void main(String[] args) {
        Map<String, String> propriedades = new HashMap<>();


//        ==================================================================================================================================
//        =================          Apenas gera scripts, não mexe no banco        =========================================================
//        ======= Essas configurações sobrescrevem as que estão definidas no persistence.xml para a mesma unidade de persistência ==========
//        ==================================================================================================================================


//      >>>> Gera scripts de DROP e CREATE (não necessariamente executa no banco, apenas gera).
//        propriedades.put("javax.persistence.schema-generation.scripts.action", "drop-and-create");

//      >>>> Define onde serão salvo os scripts gerados
//        propriedades.put("javax.persistence.schema-generation.scripts.create-target", "C:/tmp/sql/script-criacao-exportado.sql");
//        propriedades.put("javax.persistence.schema-generation.scripts.drop-target", "C:/tmp/sql/script-remocao-exportado.sql");

//      >>>> Usa metadata das entidades e depois aplica scripts adicionais (se existirem).
//        propriedades.put("javax.persistence.schema-generation.create-source", "metadata-then-script");
//        propriedades.put("javax.persistence.schema-generation.drop-source", "metadata-then-script");

//      >>>> Scripts customizados que serão usados junto com metadata
//        propriedades.put("javax.persistence.schema-generation.create-script-source", "META-INF/banco-de-dados/script-criacao.sql");
//        propriedades.put("javax.persistence.schema-generation.drop-script-source", "META-INF/banco-de-dados/script-remocao.sql");

//      >>>> Script para inserir dados iniciais
//        propriedades.put("javax.persistence.sql-load-script-source", "META-INF/banco-de-dados/dados-iniciais.sql");

        EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("EspecialistaJPADB-PU", propriedades);

        entityManagerFactory.close();
    }
}
