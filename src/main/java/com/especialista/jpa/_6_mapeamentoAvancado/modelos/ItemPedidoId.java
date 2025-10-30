package com.especialista.jpa._6_mapeamentoAvancado.modelos;

import lombok.*;

import java.io.Serializable;

/* Classe de chave primária composta que é mapeada para vários campos ou propriedades da entidade.
   Os nomes dos campos ou propriedades da classe de chave primária e os campos ou propriedades da chave primária
   da entidade devem corresponder e seus tipos devem ser os mesmos.
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedidoId implements Serializable {

    @EqualsAndHashCode.Include
    private Integer pedidoId; // mesmo campo da entidade mapeada

    @EqualsAndHashCode.Include
    private Integer produtoId; // mesmo campo da entidade mapeada
}
