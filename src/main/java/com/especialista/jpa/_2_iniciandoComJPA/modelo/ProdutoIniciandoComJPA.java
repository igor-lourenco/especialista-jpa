package com.especialista.jpa._2_iniciandoComJPA.modelo;

import com.especialista.jpa._2_iniciandoComJPA.converter.BooleanToSimNaoConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "produto_iniciando_com_jpa")
public class ProdutoIniciandoComJPA {

    @Id
    private Integer id;
    private String nome;
    private String descricao;
    private BigDecimal preco;

//  usando em ._13_Bean_Validation_Pool_de_conexoes_Entity_Graph_e_detalhes_avancados/_3_Conversor/_1_Criando_um_conversor_de_atributo
    @Convert(converter = BooleanToSimNaoConverter.class)
    @NotNull
    @Column(nullable = false, length = 3)
    private Boolean ativo = Boolean.FALSE;

    public ProdutoIniciandoComJPA() {}

    public ProdutoIniciandoComJPA(Integer id, String nome, String descricao, BigDecimal preco) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ProdutoIniciandoComJPA produto = (ProdutoIniciandoComJPA) object;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProdutoIniciandoComJPA{");
        sb.append("id=").append(id);
        sb.append(", nome='").append(nome).append('\'');
        sb.append(", descricao='").append(descricao).append('\'');
        sb.append(", preco=").append(preco);
        sb.append(", ativo=").append(ativo);
        sb.append('}');
        return sb.toString();
    }
}
