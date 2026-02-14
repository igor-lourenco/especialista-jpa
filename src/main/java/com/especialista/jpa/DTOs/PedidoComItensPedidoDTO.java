package com.especialista.jpa.DTOs;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.ItemPedido;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoComItensPedidoDTO {

    private Pedido pedido;
    private ItemPedido itemPedido;
}
