package com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

// Serve para compartilhar mapeamentos JPA entre várias entidades, sem virar uma tabela no banco, usado para centralizar campos comuns(por exemplo: id) e evitar código duplicado.
@MappedSuperclass
public class EntidadeBaseInteger {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
    private Integer id;


//  Usado para guardar a versão da entidade no banco de dados, usada pelo JPA para controle de concorrência otimista, serve
    @Version  // para evitar que duas transações sobrescrevam dados uma da outra silenciosamente.
    private Integer versao;
}
