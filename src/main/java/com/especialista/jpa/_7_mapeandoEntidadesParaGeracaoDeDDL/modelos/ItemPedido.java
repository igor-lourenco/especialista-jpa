package com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos;

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

//  O nome do atributo dentro da chave composta ao qual o atributo de relacionamento corresponde. Se não for fornecido, o relacionamento mapeia a chave primária da entidade.
    @MapsId("pedidoId") // Especifica ao JPA que um ou mais campos da PK vêm do identificador de uma associação (@ManyToOne ou @OneToOne), evitando duplicidade de colunas e mantendo tudo sincronizado.

    @ManyToOne(fetch = FetchType.EAGER, optional = false) // muitos itemPedido tem em um pedido, por padrão usa o Fetch.EAGER
    @JoinColumn(name = "pedido_id", insertable = false, updatable = false)  // especifica uma coluna para unir as associações. (owner)
    private Pedido pedido;

//  O nome do atributo dentro da chave composta ao qual o atributo de relacionamento corresponde. Se não for fornecido, o relacionamento mapeia a chave primária da entidade.
    @MapsId("produtoId") // Especifica ao JPA que um ou mais campos da PK vêm do identificador de uma associação (@ManyToOne ou @OneToOne), evitando duplicidade de colunas e mantendo tudo sincronizado.

    @ManyToOne(fetch = FetchType.EAGER, optional = false) // muitos itemPedido tem um produto, por padrão usa o Fetch.EAGER
    @JoinColumn(name = "produto_id", insertable = false, updatable = false)  // especifica uma coluna para unir as associações. (owner)
    private Produto produto;

}
