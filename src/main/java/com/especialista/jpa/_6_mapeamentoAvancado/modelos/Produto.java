package com.especialista.jpa._6_mapeamentoAvancado.modelos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_produto", schema = "especialistajpadb")
public class Produto {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
    private Integer id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    @Column(name = "data_criacao", updatable = false) // para não atualizar no banco de dados após criado
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_atualizacao", insertable = false) // para não ser criado no banco de dados, ou seja, salvar como null
    private LocalDateTime dataUltimaAtualizacao;


//   por padrão usa o Fetch.EAGER
    @OneToOne(mappedBy = "produto", fetch = FetchType.EAGER) // um produto em um estoque (não owner)
    private Estoque estoque;


    @ManyToMany(fetch = FetchType.LAZY) // por padrão usa o Fetch.LAZY
    @JoinTable(name = "tb_produto_categoria",
        joinColumns = @JoinColumn(name = "produto_id"), // coluna que referencia o id dessa entidade Produto (owner)
        inverseJoinColumns = @JoinColumn(name = "categoria_id") // coluna que referencia o id da entidade Categoria (não owner)
    )
    private List<Categoria> categorias;


    @ElementCollection // Indica que é uma coleção de elementos básicos ou objetos embutidos, JPA cria uma tabela separada para armazenar esses valores
    @CollectionTable(
        name = "tb_produto_tag", // nome da tabela no banco.
        joinColumns = @JoinColumn(name = "produto_id")) // Coluna que faz a ligação com a entidade Produto, usando sua chave primária.
    @Column(name = "tag") // Nome da coluna que vai armazenar cada valor da lista
    private List<String> tags;


    @ElementCollection // Indica que é uma coleção de elementos básicos ou objetos embutidos, JPA cria uma tabela separada para armazenar esses valores
    @CollectionTable(
        name = "tb_produto_atributo", // nome da tabela no banco.
        joinColumns = @JoinColumn(name = "produto_id")) // Coluna que faz a ligação com a entidade Produto, usando sua chave primária.
    private List<Atributo> atributos;

}
