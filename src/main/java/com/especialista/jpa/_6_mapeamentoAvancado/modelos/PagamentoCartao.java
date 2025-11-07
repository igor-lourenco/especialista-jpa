package com.especialista.jpa._6_mapeamentoAvancado.modelos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//@Table(name = "tb_pagamento_cartao") // herança com SINGLE_TABLE ignora essa anotação e não cria as tabelas
@DiscriminatorValue("PagamentoCartao") // Especifica o valor da coluna discriminadora para essa entidade, apenas para herança com SINGLE_TABLE ou JOINED
//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // foi movido para a superclasse
@Getter
@Setter
@Entity
public class PagamentoCartao extends Pagamento {

//    >>> foi movido para a superclasse
//    @Id
//    @EqualsAndHashCode.Include
////    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
//    @Column(name = "pedido_id") // coluna deve ser o mesmo do atributo mapeado com @MapsId
//    private Integer id;

//    >>> foi movido para a superclasse
//    @Enumerated(EnumType.STRING) // Salva a String do enum no banco de dados
//    private StatusPagamento status;

//    >>> foi movido para a superclasse
//    //  O nome do atributo dentro da chave composta ao qual o atributo de relacionamento corresponde. Se não for fornecido, o relacionamento mapeia a chave primária da entidade.
//    @MapsId // Especifica ao JPA que um ou mais campos da PK vêm do identificador de uma associação (@ManyToOne ou @OneToOne), evitando duplicidade de colunas e mantendo tudo sincronizado.
//    @OneToOne(fetch = FetchType.EAGER, optional = false) // um pagamentoCartao tem um pedido, por padrão usa o Fetch.EAGER
//    @JoinColumn(name = "pedido_id") // especifica uma coluna para unir as associações. (owner)
//    private Pedido pedido;


    @Column(name = "numero_cartao")
    private String numeroCartao;
}
