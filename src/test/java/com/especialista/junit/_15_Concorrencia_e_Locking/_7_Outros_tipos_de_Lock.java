package com.especialista.junit._15_Concorrencia_e_Locking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;

public class _7_Outros_tipos_de_Lock {
    protected static final Logger logger = LoggerFactory.getLogger(_7_Outros_tipos_de_Lock.class.getSimpleName());

    protected static EntityManagerFactory entityManagerFactory;

/*
    NONE                  -> sem proteção
    OPTIMISTIC            -> detecta conflito no commit
    OPTIMISTIC_FORCE_INC  -> detecta + incrementa versão
    PESSIMISTIC_READ      -> bloqueia escrita
    PESSIMISTIC_WRITE     -> bloqueia tudo
    PESSIMISTIC_FORCE_INC -> bloqueia + incrementa versão

-------------------------                              | -------------------------
---------- NONE ---------                              | ----- OPTIMISTIC_FORCE_INCREMENT -----
- Sem lock explícito                                   | - Igual ao OPTIMISTIC
- Comportamento p                                      | - Força incremento da versão, mesmo sem alteração
- Sem garantia extra de concorrência                   | - Serve para sinalizar que o dado foi “tocado”
- Se não houver @Version, pode haver lost update       | - Uso típico: marcar acesso relevante ou sincronizar concorrência lógica.
- Leituras simples, sem preocupação com concorrência.  |
                                                       |
                                                       |
-------------------------                              | -----------------------------
----- READ (legado) -----                              | ----- PESSIMISTIC_READ -----
- Lock lógico de leitura                               | - Lock pessimista compartilhado
- Equivalente ao OPTIMISTIC em JPA moderno             | - Bloqueia UPDATE e DELETE
- Depreciado (não use em código novo)                  | - Permite vários leitores
                                                       | - Lock físico (FOR SHARE)
                                                       | - Usado quando precisa ler garantindo que ninguém altere.
                                                       |
                                                       |
--------------------------                             | -----------------------------
----- WRITE (legado) -----                             | ----- PESSIMISTIC_WRITE -----
- Lock lógico de escrita                               | - Lock pessimista exclusivo
- Equivalente ao OPTIMISTIC_FORCE_INCREMENT            | - Bloqueia leitura com lock, escrita e delete
- Incrementa versão                                    | - Baseado em SELECT FOR UPDATE
- Depreciado                                           | - Uma transação por vez
- Substituído pelos modos otimistas modernos.          | - Usado quando vai alterar e precisa de exclusividade total.
                                                       |
                                                       |
-------------------------                              | ---------------------------------------
------- OPTIMISTIC ------                              | ----- PESSIMISTIC_FORCE_INCREMENT -----
- Lock otimista padrão                                 | - Igual ao PESSIMISTIC_WRITE
- Usa @Version                                         | - Força incremento da versão
- Não bloqueia o banco                                 | - Lock físico + versionamento
- Conflito detectado no commit                         | - Casos raros e críticos, com controle rígido de concorrência.
- Ideal para sistemas web e APIs (padrão recomendado). |
- Lança OptimisticLockException se houver conflito     |
*/


}
