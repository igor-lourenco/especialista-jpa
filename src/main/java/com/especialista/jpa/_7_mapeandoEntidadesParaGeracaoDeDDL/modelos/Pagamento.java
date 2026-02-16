package com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/* Diferença em usa entidade abstrata ao invés do @MappedSuperclass
*  - A entidade abstrata pode ser utilizada nas consultas(Query)
*  - Pode fazer relacionamento com a entidade abstrata
*  - Altera a estrutura das tabelas, não ficando muito intuitivo.
* */
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) //  Significa que cada classe concreta da hierarquia é mapeada para sua própria tabela
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //  Significa que todas as classes da hierarquia serão armazenadas na mesma tabela 'tb_pagamento'
//@Inheritance(strategy = InheritanceType.JOINED) //  Significa que todas as classes da hierarquia serão armazenadas na mesma tabela 'tb_pagamento'
@DiscriminatorColumn(name = "tipo_pagamento", discriminatorType = DiscriminatorType.STRING) // Cria uma coluna especial na tabela (chamada tipo_pagamento) que indica qual tipo de classe foi persistido, apenas para herança com SINGLE_TABLE e JOINED
@Table(name = "tb_pagamento") // herança com TABLE_PER_CLASS ignora essa anotação e não cria a tabela
//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // foi movido para a superclasse
@Getter
@Setter
@Entity
@ToString
public abstract class Pagamento extends EntidadeBaseInteger {

//    @Id // O Id da entidade foi movido para a coluna que está usando o @MapsId abaixo
//    @EqualsAndHashCode.Include
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
//    private Integer id;


//  O nome do atributo dentro da chave composta ao qual o atributo de relacionamento corresponde. Se não for fornecido, o relacionamento mapeia a chave primária da entidade.
    @MapsId // Especifica ao JPA que um ou mais campos da PK vêm do identificador de uma associação (@ManyToOne ou @OneToOne), evitando duplicidade de colunas e mantendo tudo sincronizado.
    @OneToOne(fetch = FetchType.EAGER, optional = false) // um pagamentoCartao tem um pedido, por padrão usa o Fetch.EAGER
    @JoinColumn(name = "pedido_id", // Especifica uma coluna para unir as associações. (owner)
        nullable = false, // define se a coluna pode ser nula no banco
        foreignKey = @ForeignKey(name = "fk_pagamento_pedido")) //  nome da constraint de chave estrangeira
    private Pedido pedido;


    @Column(name = "status", length = 30, nullable = false)
    @Enumerated(EnumType.STRING) // Salva a String do enum no banco de dados
    private StatusPagamento status;
}
