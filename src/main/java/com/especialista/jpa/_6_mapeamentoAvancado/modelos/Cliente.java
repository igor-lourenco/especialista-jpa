package com.especialista.jpa._6_mapeamentoAvancado.modelos;

import com.especialista.jpa.listeners.GenericoListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

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


    @ElementCollection // Indica que é uma coleção de elementos básicos ou objetos embutidos, JPA cria uma tabela separada para armazenar esses valores
    @CollectionTable(
        name = "tb_cliente_contato", // nome da tabela no banco.
        joinColumns = @JoinColumn(name = "cliente_id")) // Coluna que faz a ligação com a entidade Produto, usando sua chave primária.
    @MapKeyColumn(name = "tipo") // Especifica o mapeamento para a coluna chave do mapa cuja chave é um tipo básico.
    @Column(name = "descricao") // Nome da coluna que vai armazenar cada valor do mapa
    private Map<String, String> contatos;


    @Transient // JPA ignora essa propriedade e não será persistida
    private String primeiroNome;


    @PostLoad // Executa callback APÓS carregar cliente no banco de dados...
    public void configurarPrimeiroNome() {
        System.out.println(">>> Executando callback APÓS carregar cliente no banco de dados...");

        if (nome != null && !nome.isBlank()) {
            int index = nome.indexOf(" ");
            if (index > -1) {
                primeiroNome = nome.substring(0, index);
            }
        }
    }
}
