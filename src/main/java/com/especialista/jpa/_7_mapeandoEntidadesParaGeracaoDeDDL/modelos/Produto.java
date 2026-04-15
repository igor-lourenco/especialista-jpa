package com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos;

import com.especialista.jpa.DTOs.ProdutoDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NamedNativeQueries({ // usando em: _12_consultas_nativas._6_Usando_NamedNativeQuery
    @NamedNativeQuery(
        name = "tb_produto.listarTodos",
        query = "SELECT id, nome, descricao, data_criacao, data_ultima_atualizacao, preco, foto FROM tb_produto ",
        resultClass = Produto.class
    ),
    @NamedNativeQuery(
        name = "tb_produto.listarTodosDTO",
        query = "SELECT * FROM tb_produto ",
        resultSetMapping = "tb_produto.ProdutoDTO" // o nome de uma SqlResultSetMapping para mapear o retorno
    ),
})

@SqlResultSetMapping(name = "tb_produto.ProdutoDTO" // não existe um padrão para nomear, usando em: _12_consultas_nativas._5_Usando_SqlResultSetMapping_com_ColumnResult_e_retornando_DTO
    , classes = {
        @ConstructorResult(targetClass = ProdutoDTO.class, columns = { // tem que ser na ordem do construtor do ProdutoDTO
            @ColumnResult(name = "id", type = Integer.class),
            @ColumnResult(name = "nome", type = String.class)
        })
})
@SqlResultSetMappings({  // usando em: _12_consultas_nativas._4_Mapeando_resultado_de_queries_com_SqlResultSetMapping
    @SqlResultSetMapping(name = "tb_produto.Produto" // não existe um padrão para nomear
        , entities = { @EntityResult(entityClass = Produto.class)}
    )
})
@NamedQueries({
    @NamedQuery(name = "Produto.listar", query = "SELECT p FROM Produto p"), // usando em: _10_JPQL._46_Configurando_uma_named_query
    @NamedQuery(name = "Produto.listarPorCategoria", query =
        "SELECT p FROM Produto p "
            + " WHERE EXISTS (SELECT 1 FROM Categoria c2 JOIN c2.produtos p2 WHERE p2 = p AND c2.id = :categoriaId)")
})
@Getter
@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // foi movido para a superclasse
@Entity
@Table(
    name = "tb_produto", /* catalog = "especialistajpadb", */
    uniqueConstraints = {@UniqueConstraint(name = "unq_tb_produto_nome", columnNames = {"nome"})}, // coluna no banco de dados que não pode se repetir
    indexes = {@Index(name = "idx_tb_produto_nome", columnList = "nome")} // para que o banco de dados organize os registros de determinada coluna de determinada tabela)
)
public class Produto extends EntidadeBaseInteger {

//    @Id // foi movido para a superclasse
//    @EqualsAndHashCode.Include
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
//    private Integer id;

//    >>> foi movido para a superclasse
//    Usado para guardar a versão da entidade no banco de dados, usada pelo JPA para controle de concorrência otimista, serve
//    @Version  // para evitar que duas transações sobrescrevam dados uma da outra silenciosamente.
//    private Integer versao;

    @Column(name = "nome", length = 100, nullable = false) // nome varchar(100) not null
    private String nome;


//  @Lob // Especifica que uma propriedade ou campo persistente deve ser persistido como um objeto grande em um tipo de objeto grande compatível com o banco de dados
//  @Lob comentado para o postgresql criar como text
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
        cascade = { CascadeType.PERSIST  // ao persistir produto, também irá salvar as categorias em cascata
            , CascadeType.MERGE} ) // ao atualizar produto, também irá salvar as categorias em cascata
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
    @Type(type = "org.hibernate.type.BinaryType") // para o postgresql
    private byte[] foto;
}
