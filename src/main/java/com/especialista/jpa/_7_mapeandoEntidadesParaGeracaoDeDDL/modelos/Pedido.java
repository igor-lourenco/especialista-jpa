package com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos;

import com.especialista.jpa.listeners.GenericoListener;
import com.especialista.jpa.listeners.GerarNotaFiscalListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@NamedEntityGraphs({ // usando em _13_Bean_Validation_Pool_de_conexoes_Entity_Graph_e_detalhes_avancados/_5_EntityGraph/_4_Configurando_Entity_Graph_com_Anotacao
    @NamedEntityGraph(
        name = "Pedido.dadosEssenciais", // nome do EntityGraph para ser referenciado
        attributeNodes = {
            @NamedAttributeNode("dataCriacao"),
            @NamedAttributeNode("status"),
            @NamedAttributeNode("total"),
            @NamedAttributeNode(
                value = "cliente",
                subgraph = "Cliente.dadosEssenciais"),
        },
        subgraphs = {
            @NamedSubgraph(
                name = "Cliente.dadosEssenciais", // nome do SubEntityGraph para ser referenciado
                attributeNodes = {
                    @NamedAttributeNode("nome"),
                    @NamedAttributeNode("cpf"),
                })
        }
    )
})

// Especifica as classes de ouvinte de retorno de chamada a serem usadas para uma entidade ou superclasse mapeada.
@EntityListeners({GerarNotaFiscalListener.class, GenericoListener.class})

//@EqualsAndHashCode(onlyExplicitlyIncluded = true) // foi movido para a superclasse

// Especifica que a entidade deve ser armazenada em cache caso o cache esteja habilitado, quando o valor do elemento de cache do arquivo persistence.xml for ENABLE_SELECTIVE ou DISABLE_SELECTIVE.
@Cacheable

@Getter
@Setter
@Entity
@Table(name = "tb_pedido" /*,catalog = "especialistajpadb" */)
public class Pedido extends EntidadeBaseInteger
    implements PersistentAttributeInterceptable //  essa interface interna do Hibernate serve para marcar essa entidade que pode ter seus atributos interceptados em tempo de execução
{

//    @Id // foi movido para a superclasse
//    @EqualsAndHashCode.Include
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
//    private Integer id;


//    >>> foi movido para a superclasse
//    Usado para guardar a versão da entidade no banco de dados, usada pelo JPA para controle de concorrência otimista, serve
//    @Version  // para evitar que duas transações sobrescrevam dados uma da outra silenciosamente.
//    private Integer versao;

    @Column(name = "data_criacao", length = 6,
        nullable = false, // define se a coluna pode ser nula no banco
        updatable = false // para não atualizar no banco de dados após criado
    )
    private LocalDateTime dataCriacao;


    @Column(name = "data_ultima_atualizacao",
        insertable = false // para não ser criado no banco de dados, ou seja, salvar como null
    )
    private LocalDateTime dataUltimaAtualizacao;


    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;


    @Column(precision = 19, scale = 2, nullable = false) // total decimal(19, 2) not null
    private BigDecimal total;


    @Enumerated(EnumType.STRING) // Salva a String do enum no banco de dados
    @Column(length = 30, nullable = false) // status varchar(30) not null
    private StatusPedido status;


    @Embedded // Indica que a classe marcada com @Embeddable deve ser incorporada a essa entidade
    private Endereco enderecoEntrega;


    @ManyToOne(fetch = FetchType.LAZY, optional = false  // muitos pedidos tem um cliente, por padrão usa o Fetch.EAGER
        //,cascade = CascadeType.PERSIST // ao persistir pedido, também irá salvar o cliente em cascata, comentado porque está usando o persist do entityManager
    )
    @JoinColumn(name = "cliente_id", // especifica uma coluna para unir as associações. (owner)
        nullable = false, // define se a coluna pode ser nula no banco
        foreignKey = @ForeignKey(name = "fk_pedido_cliente") // nome da constraint de chave estrangeira
    )
    private Cliente cliente;


    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY // um pedido tem em muitos itens de pedido (não owner), por padrão usa o Fetch.LAZY
//        , cascade = {CascadeType.PERSIST // ao persistir pedido, também irá salvar o temPedido em cascata
//        , CascadeType.MERGE  // ao atualizar pedido, também irá salvar o itemPedido em cascata
//        , CascadeType.REMOVE  // ao remover pedido, também irá remover o itemPedido em cascata
//        }
//        , orphanRemoval = true // exclua automaticamente do banco de dados a entidade “filha” que perde a associação com seu “pai”, obs: tem que usar com o CascadeType.PERSIST
    )
    private List<ItemPedido> itensPedido;


    @OneToOne(mappedBy = "pedido", fetch = FetchType.EAGER) // um pedido tem um pagamento cartão (não owner)
    private Pagamento pagamento;


    @LazyToOne(LazyToOneOption.NO_PROXY) // JPA não define bem o lazy para @OneToOne, então essa anotação diz ao Hibernate para
//  carregar essa associação como LAZY, mas NÃO use um proxy para representar o objeto relacionado
    @OneToOne(mappedBy = "pedido", fetch = FetchType.LAZY) // um pedido tem uma nota fiscal (não owner)
    private NotaFiscal notaFiscal;

    @Getter(AccessLevel.NONE) // para não gerar o getter
    @Setter(AccessLevel.NONE) // para não gerar o setter
    @Transient                // para não ser persistidono banco de dados
    private PersistentAttributeInterceptor persistentAttributeInterceptor; // criado para interceptar os atributos em tempo de execução

    public boolean isPago(){
        return StatusPedido.PAGO.equals(this.status);
    }


//  =====================================  USANDO CALLBACK DO JPA  ======================================================
//  Obs: Só pode marcar o método com essas anotações apenas em um, não pode ter mais de um método usando a mesma anotação.
//  =====================================================================================================================

//  @PrePersist
//  @PreUpdate
    public void calcularValorTotal(){
        System.out.println(">>> Calculando valor total...");
        if(this.itensPedido != null){
            this.total = itensPedido.stream()
                .map(i -> new BigDecimal(i.getQuantidade()).multiply(i.getPrecoProduto())) // multiplica o valor do produto pela quantidade
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        }else {
            this.total = BigDecimal.ZERO;
        }
    }

    @PrePersist
    public void aoPersistir(){
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
        System.out.println(">>> Executando callback ANTES de persistir no banco de dados...");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
        this.dataCriacao = LocalDateTime.now();

        calcularValorTotal();
    }

    @PostPersist
    public void aposPersistir(){
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
        System.out.println(">>> Executando callback DEPOIS de persistir no banco de dados...");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;

    }
    @PreUpdate
    public void aoAtualizar(){
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
        System.out.println(">>> Executando callback ANTES de atualizar no banco de dados...");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
        this.dataUltimaAtualizacao = LocalDateTime.now();

        calcularValorTotal();
    }

    @PostUpdate
    public void aposAtualizar(){
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
        System.out.println(">>> Executando callback DEPOIS de atualizar no banco de dados...");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
    }

    @PreRemove
    public void aoRemover(){
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
        System.out.println(">>> Executando callback ANTES de remover no banco de dados...");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
    }

    @PostRemove
    public void aposRemover(){
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
        System.out.println(">>> Executando callback DEPOIS de remover no banco de dados...");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
    }

    @PostLoad
    public void aoCarregar(){
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
        System.out.println(">>> Executando callback APÓS carregar pedido no banco de dados...");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pedido{");
        sb.append("id=").append(getId());
        sb.append(", dataCriacao=").append(dataCriacao);
        sb.append(", dataUltimaAtualizacao=").append(dataUltimaAtualizacao);
        sb.append(", dataConclusao=").append(dataConclusao);
        sb.append(", total=").append(total);
        sb.append(", status=").append(status);
        sb.append(", enderecoEntrega=").append(enderecoEntrega);
        sb.append(", clienteId=").append(cliente.getId());
        sb.append(", itensPedido=").append(itensPedido == null ? "null": itensPedido.size());
        sb.append(", pagamentoId=").append(pagamento == null ? "null" : pagamento.getId());
        sb.append(", notaFiscalId=").append(notaFiscal == null ? "null" : notaFiscal.getId());
        sb.append('}');
        return sb.toString();
    }


//  ===============================  PROBLEMA DO ONE TO ONE COMO LAZY NO HIBERNATE ======================================
//  Obs: Como tá usando uma classe do Hibernate, uma opção de resolver esse problema seria analisar e ver se realmente é
//  necessário ou não ter esse atributo e o mapeamento na entidade, caso não for realmente necessário uma opção melhor seria
//  remover os atributos e o mapeamento da entidade.
//  Aí se realmente é necessário ter esse mapeamento na entidade, essa implementação é uma alternativa.

//  Essa implementação foi feita apenas no atributo -> notaFiscal
//  =====================================================================================================================


    public NotaFiscal getNotaFiscal() {
        if (this.persistentAttributeInterceptor != null) {
            return (NotaFiscal) persistentAttributeInterceptor.readObject(
                this,             // a própria entidade
                "notaFiscal",        // o nome do atributo
                this.notaFiscal      // a instância
            );
        }

        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        if (this.persistentAttributeInterceptor != null) {
            this.notaFiscal = (NotaFiscal) persistentAttributeInterceptor.writeObject(
                this,             // a própria entidade
                "notaFiscal",        // o nome do atributo
                this.notaFiscal,     // a instância
                notaFiscal           // o parâmetro
            );

        } else {
            this.notaFiscal = notaFiscal;

        }

    }

    @Override
    public PersistentAttributeInterceptor $$_hibernate_getInterceptor() {
        return this.persistentAttributeInterceptor;
    }

    @Override
    public void $$_hibernate_setInterceptor(PersistentAttributeInterceptor persistentAttributeInterceptor) {
        this.persistentAttributeInterceptor = persistentAttributeInterceptor;
    }
}
