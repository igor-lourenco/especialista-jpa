package com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // foi movido para a superclasse
@Entity
@Table(name = "tb_categoria", /*catalog = "especialistajpadb", */
    uniqueConstraints = {@UniqueConstraint(name = "unq_nome", columnNames = {"nome"})} // coluna no banco de dados que não pode se repetir
)
public class Categoria extends EntidadeBaseInteger {

//    @Id // foi movido para a superclasse
//    @EqualsAndHashCode.Include
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
//    private Integer id;

    @Column(length = 100, nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.EAGER) // muitas categorias filha tem uma categoria pai, por padrão usa o Fetch.EAGER
    @JoinColumn(name = "categoria_pai_id", // especifica uma coluna para unir as associações. (owner)
        foreignKey = @ForeignKey(name = "fk_categoria_pai_categoria") // nome da constraint de chave estrangeira
    )
    private Categoria categoriaPai;


//   por padrão usa o Fetch.LAZY
    @OneToMany(mappedBy = "categoriaPai", fetch = FetchType.LAZY) // uma categoria pai tem muitas categorias filhas (não owner)
    private List<Categoria> categorias;


//   por padrão usa o Fetch.LAZY
    @ManyToMany(mappedBy = "categorias", fetch = FetchType.LAZY) // uma categoria tem muitos produtos (não owner)
    private List<Produto> produtos;
}
