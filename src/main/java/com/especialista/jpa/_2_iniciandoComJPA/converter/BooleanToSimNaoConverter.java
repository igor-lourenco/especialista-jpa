package com.especialista.jpa._2_iniciandoComJPA.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

// usado na entidade ProdutoIniciandoComJPA e na classe de teste _13_Bean_Validation_Pool_de_conexoes_Entity_Graph_e_detalhes_avancados/_3_Conversor/_1_Criando_um_conversor_de_atributo.java
// 1° param do generic = Tipo do atributo que está na entidade
// 2° param do generic = Tipo do atributo que sera armazenado no banco de dados
@Converter
public class BooleanToSimNaoConverter implements AttributeConverter<Boolean, String> {


    @Override // converte o atributo da entidade (Boolean) e converte para string para ser armazenado no banco de dados (String)
    public String convertToDatabaseColumn(Boolean attribute) {
        return Boolean.TRUE.equals(attribute) ? "SIM" : "NAO";
    }

    @Override // converte o atributo do banco de dados (String) para o atributo da entidade (Boolean)
    public Boolean convertToEntityAttribute(String dbData) {
        return "SIM".equals(dbData) ? Boolean.TRUE : Boolean.FALSE;
    }
}
