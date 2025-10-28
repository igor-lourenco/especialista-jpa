package com.especialista.jpa._5_conhecendoEntityManager.modelos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

// Especifica que essa classe é embutível como parte intrínseca de uma entidade
// Cada uma das propriedades ou campos persistentes desse objeto é mapeada para a tabela do banco
@Embeddable
@Getter
@Setter
public class Endereco {

    @Column(name = "cep")
    private String cep;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "numero")
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado")
    private String estado;

}
