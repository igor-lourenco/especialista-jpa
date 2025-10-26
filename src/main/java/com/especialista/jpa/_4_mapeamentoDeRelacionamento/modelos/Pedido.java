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

    @Column(name = "nota_fiscal_id")
    private Integer notaFiscalId;

    private BigDecimal total;

    @Enumerated(EnumType.STRING) // Salva a String do enum no banco de dados
    private StatusPedido status;

    @Embedded // Indica que a classe marcada com @Embeddable deve ser incorporada a essa entidade
    private Endereco enderecoEntrega;


    @ManyToOne // muitos pedidos tem um cliente
    @JoinColumn(name = "cliente_id") // especifica uma coluna para unir as associações.
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido") // um pedido tem em muitos itens de pedido
    private List<ItemPedido> itensPedido;

    @OneToOne(mappedBy = "pedido") // um pedido tem um pamento cartão (não owner)
    private PagamentoCartao pagamento;
}
