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

//  Especifica que a implementação do JPA(Hibernate) vai escolher a forma(estratégia) mais apropriada que o id vai ser atribuído,
//  pode usar: IDENTITY, SEQUENCE ou TABLE
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nome;

    @Column(name = "categoria_pai_id")
    private Integer categoriaPaiId;
}
