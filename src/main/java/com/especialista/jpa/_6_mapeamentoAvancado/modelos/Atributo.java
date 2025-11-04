package com.especialista.jpa._6_mapeamentoAvancado.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

// Especifica que essa classe é embutível como parte intrínseca de uma entidade
// Cada uma das propriedades ou campos persistentes desse objeto é mapeada para a tabela do banco
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Atributo {

    private String nome;
    private String valor;
}
