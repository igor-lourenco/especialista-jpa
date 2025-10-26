package com.especialista.jpa._4_mapeamentoDeRelacionamento.modelos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_cliente", schema = "especialistajpadb")
public class Cliente {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
    private Integer id;

    private String nome;

    @Enumerated(EnumType.STRING) // Salva a String do enum no banco de dados
    private SexoCliente sexo;

    @OneToMany(mappedBy = "cliente") // um cliente tem muitos pedidos (não owner)
    private List<Pedido> pedidos;
}
