package com.especialista.jpa._6_mapeamentoAvancado.modelos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/* Diferença em usa entidade abstrata ao invés do @MappedSuperclass
*  - A entidade abstrata pode ser utilizada nas consultas(Query)
*  - Pode fazer relacionamento com a entidade abstrata
*  - Altera a estrutura das tabelas, não ficando muito intuitivo.
* */
@DiscriminatorColumn(name = "tipo_pagamento", discriminatorType = DiscriminatorType.STRING) // Cria uma coluna especial na tabela (chamada tipo_pagamento) que indica qual tipo de classe foi persistido.
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //  Significa que todas as classes da hierarquia serão armazenadas na mesma tabela 'tb_pagamento'.
@Table(name = "tb_pagamento", schema = "especialistajpadb")
//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // foi movido para a superclasse
@Getter
@Setter
@Entity
public abstract class Pagamento extends EntidadeBaseInteger {

//    @Id // foi movido para a superclasse
//    @EqualsAndHashCode.Include
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
//    private Integer id;

    @Enumerated(EnumType.STRING) // Salva a String do enum no banco de dados
    private StatusPagamento status;


//  O nome do atributo dentro da chave composta ao qual o atributo de relacionamento corresponde. Se não for fornecido, o relacionamento mapeia a chave primária da entidade.
    @MapsId // Especifica ao JPA que um ou mais campos da PK vêm do identificador de uma associação (@ManyToOne ou @OneToOne), evitando duplicidade de colunas e mantendo tudo sincronizado.
    @OneToOne(fetch = FetchType.EAGER, optional = false) // um pagamentoCartao tem um pedido, por padrão usa o Fetch.EAGER
    @JoinColumn(name = "pedido_id") // Especifica uma coluna para unir as associações. (owner)
    private Pedido pedido;
}
