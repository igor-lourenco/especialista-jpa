package com.especialista.jpa.DTOs;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pagamento;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class PedidoComPagamentoDTO {

    private Pedido pedido;
    private Pagamento pagamento;

    public PedidoComPagamentoDTO(Pedido pedido, Pagamento pagamento) {
        this.pedido = pedido;
        this.pagamento = pagamento;
    }
}
