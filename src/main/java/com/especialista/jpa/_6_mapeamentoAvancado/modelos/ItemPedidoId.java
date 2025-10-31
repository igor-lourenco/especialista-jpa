package com.especialista.jpa._6_mapeamentoAvancado.modelos;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

// Especifica que essa classe é embutível como parte intrínseca de uma entidade
// Cada uma das propriedades ou campos persistentes desse objeto é mapeada para a tabela do banco
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedidoId implements Serializable {

    @EqualsAndHashCode.Include
    @Column(name = "pedido_id")
    private Integer pedidoId; // mesmo campo da entidade

    @EqualsAndHashCode.Include
    @Column(name = "produto_id")
    private Integer produtoId; // mesmo campo da entidade
}
