package com.especialista.jpa._6_mapeamentoAvancado.modelos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // foi movido para a superclasse
@Entity
@Table(name = "tb_estoque", schema = "especialistajpadb")
public class Estoque extends EntidadeBaseInteger {

//    @Id // foi movido para a superclasse
//    @EqualsAndHashCode.Include
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
//    private Integer id;

    @OneToOne(fetch = FetchType.EAGER, optional = false) // por padrão usa o Fetch.EAGER
    @JoinColumn(name = "produto_id")// um estoque tem um produto (owner)
    private Produto produto;

    private Integer quantidade;

}
