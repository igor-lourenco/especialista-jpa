package com.especialista.jpa._5_conhecendoEntityManager.modelos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_nota_fiscal", schema = "especialistajpadb")
public class NotaFiscal {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
    private Integer id;

    private String xml;

    @Column(name = "data_emissao")
    private Date dataEmissao;


    @OneToOne(fetch = FetchType.EAGER, optional = false) // uma notaFiscal tem um pedido, por padrão usa o Fetch.EAGER
    @JoinColumn(name = "pedido_id") // especifica uma coluna para unir as associações. (owner)
    private Pedido pedido;

/*  Exemplo usando JoinTable com relacionamento OneToOne com notaFiscal e pedido
    @JoinTable(name = "tb_nota_fiscal_pedido",
        joinColumns = @JoinColumn(name = "nota_fiscal_id", unique = true),// coluna que referencia o id dessa entidade NotaFiscal (owner)
        inverseJoinColumns = @JoinColumn(name = "pedido_id", unique = true)) // coluna que referencia o id da entidade Pedido (não owner) */
}
