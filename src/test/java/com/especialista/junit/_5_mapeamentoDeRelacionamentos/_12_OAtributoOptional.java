package com.especialista.junit._5_mapeamentoDeRelacionamentos;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Test;

public class _12_OAtributoOptional extends EntityManagerTest {

    @Test
    public void verificarRelacionamento_OneToOne_Com_JoinTable(){

/*
        optional = false -> usa o inner join (mais performático)
        optional = true -> usa o left outer join por padrão
*/
        System.out.println("\n>>> 1. Buscando pedido no banco de dados...");
        Pedido pedido = entityManager.find(Pedido.class, 1);


    }

}
