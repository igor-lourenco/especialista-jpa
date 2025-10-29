package com.especialista.jpa.listeners;

import com.especialista.jpa._5_conhecendoEntityManager.modelos.Pedido;
import com.especialista.jpa.services.NotaFiscalService;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class GerarNotaFiscalListener {

    private NotaFiscalService notaFiscalService = new NotaFiscalService();


    @PrePersist // Executa ANTES de persistir no banco de dados
    @PreUpdate // Executa ANTES de atualizar no banco de dados
    public void gerar(Pedido pedido){

        if(pedido.isPago() && pedido.getNotaFiscal() == null){
            notaFiscalService.gerar(pedido);
        }
    }
}
