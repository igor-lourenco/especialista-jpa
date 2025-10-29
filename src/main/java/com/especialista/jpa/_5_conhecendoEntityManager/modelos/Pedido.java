package com.especialista.jpa._5_conhecendoEntityManager.modelos;

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
@Table(name = "tb_pedido", schema = "especialistajpadb")
public class Pedido {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
    private Integer id;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_atualizacao")
    private LocalDateTime dataUltimaAtualizacao;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;


    private BigDecimal total;

    @Enumerated(EnumType.STRING) // Salva a String do enum no banco de dados
    private StatusPedido status;

    @Embedded // Indica que a classe marcada com @Embeddable deve ser incorporada a essa entidade
    private Endereco enderecoEntrega;


    @ManyToOne(fetch = FetchType.EAGER, optional = false) // muitos pedidos tem um cliente, por padrão usa o Fetch.EAGER
    @JoinColumn(name = "cliente_id") // especifica uma coluna para unir as associações. (owner)
    private Cliente cliente;


    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY) // um pedido tem em muitos itens de pedido (não owner), por padrão usa o Fetch.LAZY
    private List<ItemPedido> itensPedido;


    @OneToOne(mappedBy = "pedido", fetch = FetchType.EAGER) // um pedido tem um pamento cartão (não owner)
    private PagamentoCartao pagamento;


    @OneToOne(mappedBy = "pedido", fetch = FetchType.EAGER) // um pedido tem uma nota fiscal (não owner)
    private NotaFiscal notaFiscal;



//  ====================  USANDO CALLBACK DO JPA  ====================
//  Obs: Só pode marcar o método com essas anotações apenas em um, não pode ter mais de um método usando a mesma anotação.

//    @PrePersist
//    @PreUpdate
    public void calcularValorTotal(){
        System.out.println(">>> Calculando valor total...");
        if(this.itensPedido != null){
            this.total = itensPedido.stream()
                .map(ItemPedido::getPrecoProduto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    @PrePersist
    public void aoPersistir(){
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
        System.out.println(">>> Executando callback ANTES de persistir no banco de dados...");
        this.dataCriacao = LocalDateTime.now();

        calcularValorTotal();
    }

    @PostPersist
    public void aposPersistir(){
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
        System.out.println(">>> Executando callback DEPOIS de persistir no banco de dados...");

    }
    @PreUpdate
    public void aoAtualizar(){
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
        System.out.println(">>> Executando callback ANTES de atualizar no banco de dados...");
        this.dataUltimaAtualizacao = LocalDateTime.now();

        calcularValorTotal();
    }

    @PostUpdate
    public void aposAtualizar(){
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
        System.out.println(">>> Executando callback DEPOIS de atualizar no banco de dados...");
    }

    @PreRemove
    public void aoRemover(){
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
        System.out.println(">>> Executando callback ANTES de remover no banco de dados...");
    }

    @PostRemove
    public void aposRemover(){
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
        System.out.println(">>> Executando callback DEPOIS de remover no banco de dados...");
    }

    @PostLoad
    public void aoCarregar(){
        System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");;
        System.out.println(">>> Executando callback APÓS carregar pedido no banco de dados...");
    }
}
