package com.especialista.jpa._6_mapeamentoAvancado.modelos;

import com.especialista.jpa.listeners.GenericoListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

// Especifica as classes de ouvinte de retorno de chamada a serem usadas para uma entidade ou superclasse mapeada.
@EntityListeners({GenericoListener.class})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_cliente", schema = "especialistajpadb")
public class Cliente {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY) //  Usa auto-incremento do banco
    private Integer id;

    private String nome;

    @Enumerated(EnumType.STRING) // Salva a String do enum no banco de dados
    private SexoCliente sexo;

//  por padrão usa o Fetch.LAZY
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY) // um cliente tem muitos pedidos (não owner)
    private List<Pedido> pedidos;
}
