package com.especialista.jpa.services;

import com.especialista.jpa._6_mapeamentoAvancado.modelos.Pedido;

public class NotaFiscalService {

    private NotaFiscalService notaFiscalService;


    public void gerar(Pedido pedido){
        System.out.println(">>> Gerando nota fiscal para pedido: " + pedido.getId());
    }
}
