package com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // foi movido para a superclasse
@Getter
@Setter
@Entity
@Table(name = "tb_nota_fiscal" /*,catalog = "especialistajpadb" */ )
public class NotaFiscal extends EntidadeBaseInteger {

//    @Id // foi movido para a superclasse
//    @EqualsAndHashCode.Include
////    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
//    @Column(name = "pedido_id") // coluna deve ser o mesmo do atributo mapeado com @MapsId
//    private Integer id;

//    >>> foi movido para a superclasse
//    Usado para guardar a versão da entidade no banco de dados, usada pelo JPA para controle de concorrência otimista, serve
//    @Version  // para evitar que duas transações sobrescrevam dados uma da outra silenciosamente.
//    private Integer versao;

    @Lob // Especifica que uma propriedade ou campo persistente deve ser persistido como um objeto grande em um tipo de objeto grande compatível com o banco de dados
    @Column(nullable = false) // define se a coluna pode ser nula no banco
    @Type(type = "org.hibernate.type.BinaryType") // para o postgresql
    private byte[] xml;


//    @Temporal(TemporalType.DATE) // yyyy-MM-dd
    @Temporal(TemporalType.TIMESTAMP) // yyyy-MM-dd HH:mm:ss
//    @Temporal(TemporalType.TIME) // HH:mm:ss
    @Column(name = "data_emissao", length = 6,
        nullable = false // define se a coluna pode ser nula no banco
    )
    private Date dataEmissao;


    @MapsId // Especifica ao JPA que um ou mais campos da PK vêm do identificador de uma associação (@ManyToOne ou @OneToOne), evitando duplicidade de colunas e mantendo tudo sincronizado.
    @OneToOne(fetch = FetchType.EAGER, optional = false) // uma notaFiscal tem um pedido, por padrão usa o Fetch.EAGER
    @JoinColumn(name = "pedido_id", // Especifica uma coluna para unir as associações. (owner)
        nullable = false, // define se a coluna pode ser nula no banco
        foreignKey = @ForeignKey(name = "fk_nota_fiscal_pedido") // nome da constraint de chave estrangeira
    )
    private Pedido pedido;

/*  Exemplo usando JoinTable com relacionamento OneToOne com notaFiscal e pedido
    @JoinTable(name = "tb_nota_fiscal_pedido",
        joinColumns = @JoinColumn(name = "nota_fiscal_id", unique = true),// coluna que referencia o id dessa entidade NotaFiscal (owner)
        inverseJoinColumns = @JoinColumn(name = "pedido_id", unique = true)) // coluna que referencia o id da entidade Pedido (não owner) */
}
