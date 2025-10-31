package com.especialista.jpa._6_mapeamentoAvancado.modelos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_item_pedido", schema = "especialistajpadb")
public class ItemPedido {

    @EmbeddedId // chave primária composta como um objeto de valor embutido dentro dessa entidade.
    private ItemPedidoId id;

    @Column(name = "preco_produto")
    private BigDecimal precoProduto;

    private Integer quantidade;


    @ManyToOne(fetch = FetchType.EAGER, optional = false) // muitos itemPedido tem em um pedido, por padrão usa o Fetch.EAGER
    @JoinColumn(name = "pedido_id", insertable = false, updatable = false)  // especifica uma coluna para unir as associações. (owner)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.EAGER, optional = false) // muitos itemPedido tem um produto, por padrão usa o Fetch.EAGER
    @JoinColumn(name = "produto_id", insertable = false, updatable = false)  // especifica uma coluna para unir as associações. (owner)
    private Produto produto;

}
