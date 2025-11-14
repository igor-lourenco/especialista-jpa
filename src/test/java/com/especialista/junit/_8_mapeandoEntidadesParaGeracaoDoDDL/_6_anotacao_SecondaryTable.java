package com.especialista.junit._8_mapeandoEntidadesParaGeracaoDoDDL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

public class _6_anotacao_SecondaryTable extends EntityManagerTest {

    @Test
    public void gerarDDL() {

        /****************** @SecondaryTable *****************
         * name:
         *    - Define o nome da tabela secundária no banco de dados
         *
         * catalog:
         *    - Representa o catálogo da tabela secundária, que geralmente é o próprio banco de dados ou um agrupamento de esquemas
         *    - Mais comum em bancos como MySQL ou SQL Server, onde o catálogo pode ser o nome do banco
         *
         * schema:
         *    - Representa o esquema da tabela secundária dentro do banco
         *    - É muito usado em bancos como PostgreSQL, Oracle, SQL Server, onde você pode ter vários esquemas dentro do mesmo banco
         *
         * pkJoinColumns:
         *    - Define as colunas que fazem o join entre a tabela principal e a tabela secundária, usando a chave primária da entidade.
         *
         * uniqueConstraints:
         *    - Serve para definir restrições de unicidade diretamente no nível da tabela, garantindo que uma ou mais
         *    colunas não tenham valores duplicados.
         *    - Semelhante ao @Column(unique = true)
         *
         * indexes:
         *    - Cria índices no banco de dados quando o JPA gera o DDL
         *    - Ajuda a melhorar a performance de consultas, especialmente em colunas que são frequentemente usadas em filtros (WHERE),
         *   ordenações (ORDER BY) ou junções (JOIN).
         *   - Índice serve para acelerar a busca e recuperação de dados em uma tabela. Ele funciona de forma semelhante ao
         *   índice de um livro: em vez de percorrer todas as páginas (linhas da tabela), você consulta o índice para ir direto ao ponto.
         *
         * foreignKey:
         *    - Permite configurar a restrição de chave estrangeira para o relacionamento entre a tabela principal e a secundária
         *
         *  ******************************************************
         *  ************ Resumo de todos os atributos ************
         *
         *  name → Nome da tabela secundária.
         *  catalog → Catálogo da tabela.
         *  schema → Esquema da tabela.
         *  pkJoinColumns → Colunas para o join com a tabela principal.
         *  foreignKey → Define a chave estrangeira.
         *  indexes → Define índices na tabela secundária.
         *  uniqueConstraints → Define restrições de unicidade.
         */

    }
}
