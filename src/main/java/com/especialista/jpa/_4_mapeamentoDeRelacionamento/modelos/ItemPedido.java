package com.especialista.jpa._4_mapeamentoDeRelacionamento.modelos;

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

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
    private Integer id;

    @Column(name = "preco_produto")
    private BigDecimal precoProduto;

    private Integer quantidade;


    @ManyToOne // muitos itemPedido tem em um pedido
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne // muitos itemPedido tem um produto
    @JoinColumn(name = "produto_id")
    private Produto produto;

}
