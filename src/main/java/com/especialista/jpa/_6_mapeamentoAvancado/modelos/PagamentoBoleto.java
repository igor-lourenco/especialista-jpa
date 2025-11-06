package com.especialista.jpa._6_mapeamentoAvancado.modelos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // foi movido para a superclasse
@Entity
@Table(name = "tb_pagamento_boleto", schema = "especialistajpadb")
public class PagamentoBoleto extends Pagamento {

//     >>> foi movido para a superclasse
//    @Id
//    @EqualsAndHashCode.Include
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
//    private Integer id;


//     >>> foi movido para a superclasse
//    @Column(name = "pedido_id")
//    private Integer pedidoId;


//     >>> foi movido para a superclasse
//    @Enumerated(EnumType.STRING) // Salva a String do enum no banco de dados
//    private StatusPagamento status;

    @Column(name = "codigo_barras")
    private String codigoBarras;
}
