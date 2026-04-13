package com.especialista.junit._16_Multitenancy;

import com.especialista.junit.utils.EntityManagerFactoryTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class _1_Criando_um_novo_schema extends EntityManagerFactoryTest {
    protected static final Logger logger = LoggerFactory.getLogger(_1_Criando_um_novo_schema.class);

    @Test
    public void criandoUmNovoSchema(){

        logger.warn("Criando um novo schema...");
        logger.warn("Foi configurado na classe ExecutarDDL para ser criado um novo schema...");

    }
}
