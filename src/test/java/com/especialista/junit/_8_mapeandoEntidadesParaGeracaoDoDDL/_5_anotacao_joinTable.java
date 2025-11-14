package com.especialista.junit._8_mapeandoEntidadesParaGeracaoDoDDL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

public class _5_anotacao_joinTable extends EntityManagerTest {

    @Test
    public void gerarDDL() {

        /****************** @JoinTable *****************
         * name:
         *    - Nome da tabela de junção no banco de dados
         *
         * catalog:
         *    - Define o catálogo do banco onde a tabela de junção está, geralmente usado em bancos que suportam múltiplos catálogos
         *
         * schema:
         *    - Define o schema do banco onde a tabela de junção está
         *
         * joinColumns:
         *    - Lista de colunas que representam a chave estrangeira da entidade dona do relacionamento na tabela de junção
         *
         * inverseJoinColumns:
         *    - Lista de colunas que representam a chave estrangeira da outra entidade (não owner) na tabela de junção
         *
         * foreignKey:
         *    - Define a restrição de chave estrangeira para as colunas especificadas em joinColumns (ou seja, para a entidade owner do relacionamento).
         *
         * inverseForeignKey:
         *    - Define a restrição de chave estrangeira para as colunas especificadas em inverseJoinColumns (a outra entidade).
         *
         * uniqueConstraints:
         *    - Define restrições de unicidade na tabela de junção
         *
         * indexes:
         *    - Permite criar índices na tabela de junção para melhorar performance em consultas.
         *
         *  ******************************************************
         *  ************ Resumo de todos os atributos ************
         *
         *  name → Nome da tabela de junção no banco de dados.
         *  schema → Schema onde a tabela será criada.
         *  catalog → Catálogo do banco (pouco usado).
         *  joinColumns → Colunas que representam a FK da entidade dona do relacionamento.
         *  inverseJoinColumns → Colunas que representam a FK da outra entidade.
         *  foreignKey → Define a constraint da FK para joinColumns.
         *  inverseForeignKey → Define a constraint da FK para inverseJoinColumns.
         *  uniqueConstraints → Define restrições de unicidade na tabela de junção.
         *  indexes → Cria índices para melhorar performance nas consultas.

         */

    }
}
