package com.especialista.junit._15_Concorrencia_e_Locking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class _3_Diferenca_entre_Lock_Otimista_e_Lock_Pessimista {
    protected static final Logger logger = LoggerFactory.getLogger(_3_Diferenca_entre_Lock_Otimista_e_Lock_Pessimista.class.getSimpleName());


/*
        — > Lock otimista: É uma regra da aplicação
            - Vou trabalhar normalmente. Se alguém tiver alterado o dado antes de mim, eu detecto no final
            - Ou seja, conflitos são raros

        — > Lock Pessimista: Usa recursos extras do banco de dados
           - Vou travar o dado agora, porque alguém pode mexer
           - Ou seja, conflitos vão acontecer
*/
}
