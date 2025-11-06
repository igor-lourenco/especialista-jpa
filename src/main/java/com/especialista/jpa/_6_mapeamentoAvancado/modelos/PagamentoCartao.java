package com.especialista.jpa._6_mapeamentoAvancado.modelos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // foi movido para a superclasse
@Entity
@Table(name = "tb_pagamento_cartao", schema = "especialistajpadb")
public class PagamentoCartao extends EntidadeBaseInteger {

//    @Id // foi movido para a superclasse
//    @EqualsAndHashCode.Include
////    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
//    @Column(name = "pedido_id") // coluna deve ser o mesmo do atributo mapeado com @MapsId
//    private Integer id;

    @Enumerated(EnumType.STRING) // Salva a String do enum no banco de dados
    private StatusPagamento status;

    private String numero;

    //  O nome do atributo dentro da chave composta ao qual o atributo de relacionamento corresponde. Se não for fornecido, o relacionamento mapeia a chave primária da entidade.
    @MapsId // Especifica ao JPA que um ou mais campos da PK vêm do identificador de uma associação (@ManyToOne ou @OneToOne), evitando duplicidade de colunas e mantendo tudo sincronizado.
    @OneToOne(fetch = FetchType.EAGER, optional = false) // um pagamentoCartao tem um pedido, por padrão usa o Fetch.EAGER
    @JoinColumn(name = "pedido_id") // especifica uma coluna para unir as associações. (owner)
    private Pedido pedido;
}
