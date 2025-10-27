package com.especialista.jpa._4_mapeamentoDeRelacionamento.modelos;

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

    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;

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
}
