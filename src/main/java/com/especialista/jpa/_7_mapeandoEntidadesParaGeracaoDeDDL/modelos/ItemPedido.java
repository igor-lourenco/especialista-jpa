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
@Table(name = "tb_item_pedido" /*, catalog = "especialistajpadb"*/)
public class ItemPedido {

    @EmbeddedId // chave primária composta como um objeto de valor embutido dentro dessa entidade.
    private ItemPedidoId id;

    @Column(name = "preco_produto", precision = 19, scale = 2, nullable = false)
    private BigDecimal precoProduto;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

//  O nome do atributo dentro da chave composta ao qual o atributo de relacionamento corresponde. Se não for fornecido, o relacionamento mapeia a chave primária da entidade.
    @MapsId("pedidoId") // Especifica ao JPA que um ou mais campos da PK vêm do identificador de uma associação (@ManyToOne ou @OneToOne), evitando duplicidade de colunas e mantendo tudo sincronizado.

    @ManyToOne(fetch = FetchType.EAGER, optional = false) // muitos itemPedido tem em um pedido, por padrão usa o Fetch.EAGER
    @JoinColumn(name = "pedido_id", // especifica uma coluna para unir as associações. (owner)
        insertable = false, updatable = false, // controla se a coluna será incluída em inserts e updates
        nullable = false, // define se a coluna pode ser nula no banco
        foreignKey = @ForeignKey(name = "fk_item_pedido_pedido") // nome da constraint de chave estrangeira
    )
    private Pedido pedido;

//  O nome do atributo dentro da chave composta ao qual o atributo de relacionamento corresponde. Se não for fornecido, o relacionamento mapeia a chave primária da entidade.
    @MapsId("produtoId") // Especifica ao JPA que um ou mais campos da PK vêm do identificador de uma associação (@ManyToOne ou @OneToOne), evitando duplicidade de colunas e mantendo tudo sincronizado.

    @ManyToOne(fetch = FetchType.EAGER, optional = false) // muitos itemPedido tem um produto, por padrão usa o Fetch.EAGER
    @JoinColumn(name = "produto_id", // especifica uma coluna para unir as associações. (owner)
        insertable = false, updatable = false, // controla se a coluna será incluída em inserts e updates
        nullable = false, // define se a coluna pode ser nula no banco
        foreignKey = @ForeignKey(name = "fk_item_pedido_produto") // nome da constraint de chave estrangeira
    )
    private Produto produto;

}
