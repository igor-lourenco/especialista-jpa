package com.especialista.jpa._3_mapeamentoBasico.modelos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_categoria", schema = "especialistajpadb")
public class Categoria {

    @Id
    @EqualsAndHashCode.Include

//  Usa uma sequência nativa do banco de dados para gerar os valores da chave primária.
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq",
        sequenceName = "tb_categoria_sequencias_chave_primaria", // nome da sequência no banco
        allocationSize = 100, // define quantos valores o JPA reserva por vez em cache. (1 = sem cache).
        initialValue = 0 // valor inicial
    )
    private Integer id;

    private String nome;

    @Column(name = "categoria_pai_id")
    private Integer categoriaPaiId;
}
