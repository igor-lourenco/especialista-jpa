package com.especialista.jpa._5_conhecendoEntityManager.modelos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_categoria", schema = "especialistajpadb")
public class Categoria {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
    private Integer id;

    private String nome;

    @ManyToOne(fetch = FetchType.EAGER) // muitas categorias filha tem uma categoria pai, por padrão usa o Fetch.EAGER
    @JoinColumn(name = "categoria_pai_id")  // especifica uma coluna para unir as associações. (owner)
    private Categoria categoriaPai;


//   por padrão usa o Fetch.LAZY
    @OneToMany(mappedBy = "categoriaPai", fetch = FetchType.LAZY) // uma categoria pai tem muitas categorias filhas (não owner)
    private List<Categoria> categorias;


//   por padrão usa o Fetch.LAZY
    @ManyToMany(mappedBy = "categorias", fetch = FetchType.LAZY) // uma categoria tem muitos produtos (não owner)
    private List<Produto> produtos;
}
