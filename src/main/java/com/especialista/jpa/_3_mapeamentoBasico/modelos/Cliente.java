package com.especialista.jpa._3_mapeamentoBasico.modelos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_cliente", schema = "especialistajpadb")
public class Cliente {

    @Id
    @EqualsAndHashCode.Include
    private Integer id;

    private String nome;

    private SexoCliente sexo;
}
