package com.especialista.jpa._3_mapeamentoBasico.modelos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_produto", schema = "especialistajpadb")
public class Produto {

    @Id
    @EqualsAndHashCode.Include
    private Integer id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

}
