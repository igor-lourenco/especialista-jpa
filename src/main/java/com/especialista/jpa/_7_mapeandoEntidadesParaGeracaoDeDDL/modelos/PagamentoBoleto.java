package com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

//@Table(name = "tb_pagamento_boleto")  // herança com single table ignora essa anotação e não cria as tabelas
@DiscriminatorValue("PagamentoBoleto") // Especifica o valor da coluna discriminadora para essa entidade, apenas para herança com SINGLE_TABLE ou JOINED
//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // foi movido para a superclasse
@Getter
@Setter
@Entity
@ToString
public class PagamentoBoleto extends Pagamento {

//    >>> foi movido para a superclasse
//    @Id
//    @EqualsAndHashCode.Include
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
//    private Integer id;

//    >>> foi movido para a superclasse
//    Usado para guardar a versão da entidade no banco de dados, usada pelo JPA para controle de concorrência otimista, serve
//    @Version  // para evitar que duas transações sobrescrevam dados uma da outra silenciosamente.
//    private Integer versao;

//    >>> foi movido para a superclasse
//    @Column(name = "pedido_id")
//    private Integer pedidoId;


//    >>> foi movido para a superclasse
//    @Enumerated(EnumType.STRING) // Salva a String do enum no banco de dados
//    private StatusPagamento status;

    @Column(name = "codigo_barras", length = 100)
    private String codigoBarras;
}
