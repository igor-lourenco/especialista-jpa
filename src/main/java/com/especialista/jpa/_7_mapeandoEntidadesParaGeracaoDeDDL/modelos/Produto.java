package com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // foi movido para a superclasse
@Entity
@Table(
    name = "tb_produto", /* catalog = "especialistajpadb", */
    uniqueConstraints = {@UniqueConstraint(name = "unq_nome", columnNames = {"nome"})}, // coluna no banco de dados que não pode se repetir
    indexes = {@Index(name = "idx_nome", columnList = "nome")} // para que o banco de dados organize os registros de determinada coluna de determinada tabela)
)
public class Produto extends EntidadeBaseInteger {

//    @Id // foi movido para a superclasse
//    @EqualsAndHashCode.Include
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
//    private Integer id;

    @Column(name = "nome", length = 100, nullable = false) // nome varchar(100) not null
    private String nome;


    @Lob // Especifica que uma propriedade ou campo persistente deve ser persistido como um objeto grande em um tipo de objeto grande compatível com o banco de dados
    private String descricao;


    @Column(precision = 10, scale = 2) // preco decimal(10, 2)
    private BigDecimal preco;


    @Column(name = "data_criacao", length = 6, nullable = false, updatable = false) // para não atualizar no banco de dados após criado
    private LocalDateTime dataCriacao;


    @Column(name = "data_ultima_atualizacao", insertable = false) // para não ser criado no banco de dados, ou seja, salvar como null
    private LocalDateTime dataUltimaAtualizacao;


//   por padrão usa o Fetch.EAGER
    @OneToOne(mappedBy = "produto", fetch = FetchType.EAGER) // um produto em um estoque (não owner)
    private Estoque estoque;


    @ManyToMany(fetch = FetchType.LAZY, // por padrão usa o Fetch.LAZY
        cascade = CascadeType.PERSIST) // ao persistir produto, também irá salvar as categorias em cascata
    @JoinTable(name = "tb_produto_categoria",
//        foreignKey = @ForeignKey(name = "fk_produto_categoria_produto"), // exemplo de configurar o nome da contraint diretamente no @JoinTable em vez do @JoinColumn
//        inverseForeignKey = @ForeignKey(name = "fk_produto_categoria_categoria") // exemplo de configurar o nome da contraint diretamente no @JoinTable em vez do @JoinColumn

        joinColumns = @JoinColumn(name = "produto_id", // coluna que referencia o id dessa entidade Produto (owner)
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_produto_categoria_produto") // nome da constraint de chave estrangeira
        ),
        inverseJoinColumns = @JoinColumn(name = "categoria_id", // coluna que referencia o id da entidade Categoria (não owner)
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_produto_categoria_categoria") // nome da constraint de chave estrangeira
        )
    )
    private List<Categoria> categorias;


    @ElementCollection // Indica que é uma coleção de elementos básicos ou objetos embutidos, JPA cria uma tabela separada para armazenar esses valores
    @CollectionTable(
        name = "tb_produto_tag", // nome da tabela no banco.
        joinColumns = @JoinColumn(name = "produto_id"), // Coluna que faz a ligação com a entidade Produto, usando sua chave primária.
        foreignKey = @ForeignKey(name = "fk_produto_tag_produto") // nome da constraint de chave estrangeira
    )
    @Column(name = "tag", length = 50, nullable = false) // Nome da coluna que vai armazenar cada valor da lista
    private List<String> tags;


    @ElementCollection // Indica que é uma coleção de elementos básicos ou objetos embutidos, JPA cria uma tabela separada para armazenar esses valores
    @CollectionTable(
        name = "tb_produto_atributo", // nome da tabela no banco.
        joinColumns = @JoinColumn(name = "produto_id"), // Coluna que faz a ligação com a entidade Produto, usando sua chave primária.
        foreignKey = @ForeignKey(name = "fk_produto_atributo_produto") // nome da constraint de chave estrangeira
    )
    private List<Atributo> atributos;


    @Lob // Especifica que uma propriedade ou campo persistente deve ser persistido como um objeto grande em um tipo de objeto grande compatível com o banco de dados
    private byte[] foto;
}
