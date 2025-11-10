package com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos;

import com.especialista.jpa.listeners.GenericoListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

// Especifica as classes de ouvinte de retorno de chamada a serem usadas para uma entidade ou superclasse mapeada.
@EntityListeners({GenericoListener.class})

// mapeia uma única entidade para duas (ou mais) tabelas no banco de dados, nesse caso cria uma tabela secundária "tb_cliente_detalhe" com relacionamento OneToOne
@SecondaryTable(name = "tb_cliente_detalhe", pkJoinColumns = @PrimaryKeyJoinColumn(name = "cliente_id"))

@Getter
@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // foi movido para a superclasse
@Entity
@Table(
    name = "tb_cliente",
//    schema = "especialistajpadb", // Representa o esquema dentro do banco
//    catalog = "especialistajpadb", // Representa o catálogo, que geralmente é o próprio banco de dados ou um agrupamento de esquemas.
    uniqueConstraints = {@UniqueConstraint(name = "unq_cpf", columnNames = {"cpf"})}, // coluna no banco de dados que não pode se repetir
    indexes = {@Index(name = "idx_nome", columnList = "nome")} // para que o banco de dados organize os registros de determinada coluna de determinada tabela
)
public class Cliente extends EntidadeBaseInteger {

//    @Id // Foi movido para a superclasse
//    @EqualsAndHashCode.Include
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
//    private Integer id;

    private String nome;

    private String cpf;


//  por padrão usa o Fetch.LAZY
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY) // um cliente tem muitos pedidos (não owner)
    private List<Pedido> pedidos;


    @ElementCollection // Indica que é uma coleção de elementos básicos ou objetos embutidos, JPA cria uma tabela separada para armazenar esses valores
    @CollectionTable(
        name = "tb_cliente_contato", // nome da tabela no banco.
        joinColumns = @JoinColumn(name = "cliente_id")) // Coluna que faz a ligação com a entidade Produto, usando sua chave primária.
    @MapKeyColumn(name = "tipo") // Especifica o mapeamento para a coluna chave do mapa cuja chave é um tipo básico.
    @Column(name = "descricao") // Nome da coluna que vai armazenar cada valor do mapa
    private Map<String, String> contatos;


    @Column(table = "tb_cliente_detalhe") // Salva essa coluna na tabela secundária "tb_cliente_detalhe"
    @Enumerated(EnumType.STRING) // Salva a String do enum no banco de dados
    private SexoCliente sexo;


    @Column(name = "data_nascimento", table = "tb_cliente_detalhe") // Salva esa coluna na tabela secundária "tb_cliente_detalhe"
    private LocalDate dataNascimento;


    @Transient // JPA ignora essa propriedade e não será persistida
    private String primeiroNome;


    @PostLoad // Executa callback APÓS carregar cliente no banco de dados...
    public void configurarPrimeiroNome() {
        System.out.println(">>> Executando callback APÓS carregar cliente no banco de dados...");

        if (nome != null && !nome.isBlank()) {
            int index = nome.indexOf(" ");
            if (index > -1) {
                primeiroNome = nome.substring(0, index);
            }
        }
    }
}
