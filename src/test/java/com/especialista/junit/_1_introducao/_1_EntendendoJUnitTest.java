package com.especialista.junit._1_introducao;

import org.junit.*;

public class _1_EntendendoJUnitTest {


    @BeforeClass
    public static void iniciarTestes(){
        System.out.println(">>> Executando antes ...");
    }


    @AfterClass
    public static void encerrarTestes(){
        System.out.println(">>> Executando depois ...");
    }


    @Before
    public void inciarTeste(){
        System.out.println(">>> Executando antes de cada teste ...");
    }

    @After
    public void encerrarTeste(){
        System.out.println(">>> Executando depois de cada teste ...");
    }

    @Test
    public void testandoalgo1(){
        System.out.println(">>> Testando 1 ...");
    }


    @Test
    public void testandoalgo2(){
        System.out.println(">>> Testando 2 ...");
    }

    @Test
    public void testandoalgo3(){
        String nome = "Alexandre";
        Assert.assertEquals("Alexandre", nome);
        System.out.println(">>> Testando 3 ...");
    }
}
