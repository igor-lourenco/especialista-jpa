package com.especialista.jpa._3_mapeamentoBasico.modelos;

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
    private Integer id;

    @Column(name = "pedido_id")
    private Integer pedidoId;

    @Enumerated(EnumType.STRING) // Salva a String do enum no banco de dados
    private StatusPagamento status;

    private String numero;

}
