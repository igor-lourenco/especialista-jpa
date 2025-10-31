package com.especialista.jpa._6_mapeamentoAvancado.modelos;

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
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
    @Column(name = "pedido_id") // coluna deve ser o mesmo do atributo mapeado com @MapsId
    private Integer id;

    private String xml;

    @Column(name = "data_emissao")
    private Date dataEmissao;


    @MapsId // Especifica ao JPA que um ou mais campos da PK vêm do identificador de uma associação (@ManyToOne ou @OneToOne), evitando duplicidade de colunas e mantendo tudo sincronizado.
    @OneToOne(fetch = FetchType.EAGER, optional = false) // uma notaFiscal tem um pedido, por padrão usa o Fetch.EAGER
    @JoinColumn(name = "pedido_id") // Especifica uma coluna para unir as associações. (owner)
    private Pedido pedido;

/*  Exemplo usando JoinTable com relacionamento OneToOne com notaFiscal e pedido
    @JoinTable(name = "tb_nota_fiscal_pedido",
        joinColumns = @JoinColumn(name = "nota_fiscal_id", unique = true),// coluna que referencia o id dessa entidade NotaFiscal (owner)
        inverseJoinColumns = @JoinColumn(name = "pedido_id", unique = true)) // coluna que referencia o id da entidade Pedido (não owner) */
}
