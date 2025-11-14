package com.especialista.junit._8_mapeandoEntidadesParaGeracaoDoDDL;

import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

public class _2_anotacao_Column extends EntityManagerTest {

    @Test
    public void gerarDDL(){

    /***************** @Column *****************
    * name:
    *    - Define o nome da coluna no banco, se não informado, usa o nome do atributo
    *
    * nullable:
    *    - Indica se a coluna pode aceitar valores nulos, padrão true
    *
    * columnDefinition:
    *    - Permite definir o tipo exato da coluna no banco (DDL), útil para tipos específicos ou constraints customizadas
    *
    * unique:
    *    - Define se a coluna deve ter valores únicos, cria uma restrição UNIQUE no banco. Semelhante ao @Table(uniqueConstraints={@UniqueConstraint()})
    *
    * updatable:
    *    - Indica se o valor da coluna pode ser atualizado em UPDATE, padrão true
    *
    * insertable:
    *    - Indica se o valor da coluna deve ser incluído em instruções INSERT, padrão true
    *
    * table:
    *    - Define em qual tabela a coluna está (usado em herança ou mapeamentos complexos)
    *
    * length:
    *    - Define o tamanho máximo para colunas do tipo String, padrão 255
    *
    * precision:
    *    - Define a precisão para números decimais (quantidade total de dígitos), usado com BigDecimal ou Double
    *
    * scale:
    *    - Define a escala (quantidade de casas decimais), usado junto com precision
    *
    *  ******************************************************
    *  ************ Resumo de todos os atributos ************
    *
    *  Nome e tamanho → name, length
    *  Validação → nullable, unique
    *  Controle de persistência → insertable, updatable
    *  Tipo customizado → columnDefinition
    *  Localização → table
    *  Números decimais → precision, scale
    *  */

    }


}
