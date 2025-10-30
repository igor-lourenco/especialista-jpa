package com.especialista.jpa._6_mapeamentoAvancado.modelos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_pagamento_cartao", schema = "especialistajpadb")
public class PagamentoCartao {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
    private Integer id;

    @Enumerated(EnumType.STRING) // Salva a String do enum no banco de dados
    private StatusPagamento status;

    private String numero;

    @OneToOne(fetch = FetchType.EAGER, optional = false) // um pagamentoCartao tem um pedido, por padrão usa o Fetch.EAGER
    @JoinColumn(name = "pedido_id") // especifica uma coluna para unir as associações. (owner)
    private Pedido pedido;
}
