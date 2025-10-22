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

//  Usa uma tabela auxiliar para gerar IDs
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tabela")
    @TableGenerator(name = "tabela",
        table = "hibernate_sequences", // nome da tabela que armazenará os valores dos IDs
        pkColumnName = "sequence_name", // nome da coluna que identifica o tipo de entidade
        pkColumnValue = "tb_categoria", // valor que será usado na coluna pkColumnName para identificar a sequência específica dessa entidade.
        valueColumnName = "next_value", // nome da coluna que armazena o valor atual do ID.
        allocationSize = 100, // define quantos valores o JPA reserva por vez em cache. (1 = sem cache).
        initialValue = 0 // valor inicial
    )
    private Integer id;

    private String nome;

    @Column(name = "categoria_pai_id")
    private Integer categoriaPaiId;
}
