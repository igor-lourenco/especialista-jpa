package com.especialista.junit._8_mapeandoEntidadesParaGeracaoDoDDL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

public class _1_anotacao_Table extends EntityManagerTest {

    @Test
    public void gerarDDL(){

    /****************** @Table *****************
    * schema:
    *    - Representa o esquema dentro do banco
    *    - É muito usado em bancos como PostgreSQL, Oracle, SQL Server, onde você pode ter vários esquemas dentro do mesmo banco
    *
    * catalog:
    *    - Representa o catálogo, que geralmente é o próprio banco de dados ou um agrupamento de esquemas
    *    - Mais comum em bancos como MySQL ou SQL Server, onde o catálogo pode ser o nome do banco
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
    * */

    }


}
