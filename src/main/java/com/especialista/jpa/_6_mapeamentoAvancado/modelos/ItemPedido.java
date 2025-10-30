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
@IdClass(ItemPedidoId.class) // Especifica uma classe de chave primária composta
@Table(name = "tb_item_pedido", schema = "especialistajpadb")
public class ItemPedido {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "pedido_id")
    private Integer pedidoId; // mesmo campo da classe de chave primária composta

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "produto_id")
    private Integer produtoId; // mesmo campo da classe de chave primária composta

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
