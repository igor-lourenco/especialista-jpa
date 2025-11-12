package com.especialista.junit._8_mapeandoEntidadesParaGeracaoDoDDL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

public class _4_anotacao_JoinColumn extends EntityManagerTest {

    @Test
    public void gerarDDL() {

        /****************** @JoinColumn *****************
         * name:
         *    - Nome da coluna na tabela que armazenará a chave estrangeira
         *
         * referencedColumnName:
         *    - Indica qual coluna da entidade referenciada será usada para a junção, por padrão, é a coluna da chave primária (id)
         *
         * nullable:
         *    - Define se a coluna pode ser nula no banco, padrão true
         *
         * unique:
         *    - Indica se a coluna deve ter valor único, útil em relacionamentos @OneToOne
         *
         * insertable e updatable
         *    - controlam se a coluna será incluída em inserts e updates, padrão true
         *
         * table:
         *    - Define a tabela onde a coluna está, caso seja diferente da tabela principal, usado em mapeamentos mais complexos
         *
         * foreignKey:
         *    - Permite configurar a restrição de chave estrangeira
         */

    }
}
