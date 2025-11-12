package com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // foi movido para a superclasse
@Entity
@Table(name = "tb_estoque", /* catalog = "especialistajpadb"*/
    uniqueConstraints = {@UniqueConstraint(name = "unq_produto_id", columnNames = {"produto_id"})}
)
public class Estoque extends EntidadeBaseInteger {

//    @Id // foi movido para a superclasse
//    @EqualsAndHashCode.Include
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
//    private Integer id;

//    @MapsId // Especifica ao JPA que um ou mais campos da PK vêm do identificador de uma associação (@ManyToOne ou @OneToOne), evitando duplicidade de colunas e mantendo tudo sincronizado.
    @OneToOne(fetch = FetchType.EAGER, optional = false) // por padrão usa o Fetch.EAGER
    @JoinColumn(name = "produto_id", // um estoque tem um produto (owner)
        nullable = false, // define se a coluna pode ser nula no banco
        foreignKey = @ForeignKey(name = "fk_estoque_produto") // nome da constraint de chave estrangeira
    )
    private Produto produto;


    private Integer quantidade;

}
