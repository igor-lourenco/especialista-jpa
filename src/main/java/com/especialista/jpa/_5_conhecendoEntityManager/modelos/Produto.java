package com.especialista.jpa._5_conhecendoEntityManager.modelos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_produto", schema = "especialistajpadb")
public class Produto {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
    private Integer id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

//   por padrão usa o Fetch.EAGER
    @OneToOne(mappedBy = "produto", fetch = FetchType.EAGER) // um produto em um estoque (não owner)
    private Estoque estoque;


    @ManyToMany(fetch = FetchType.LAZY) // por padrão usa o Fetch.LAZY
    @JoinTable(name = "tb_produto_categoria",
        joinColumns = @JoinColumn(name = "produto_id"), // coluna que referencia o id dessa entidade Produto (owner)
        inverseJoinColumns = @JoinColumn(name = "categoria_id") // coluna que referencia o id da entidade Categoria (não owner)
    )
    private List<Categoria> categorias;

}
