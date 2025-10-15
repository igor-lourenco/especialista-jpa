package com.especialista.junit._3_inciandoComJpa;

import com.especialista.jpa._2_iniciandoComJPA.modelo.Produto;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class _1_ConsultandoRegistroTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeClass
    public static void setUpBeforeClass(){ // executa primeiro
        System.out.println(">>> Iniciando o EntityManagerFactory...");
        entityManagerFactory = Persistence.createEntityManagerFactory("EspecialistaJPADB-PU");

    }

    @AfterClass
    public static void tearDownAfterClass(){ // executa por ultimo
        System.out.println(">>> Finalizando o EntityManagerFactory...");
        entityManagerFactory.close();
    }


    @Before
    public void setUp(){ // executa antes de cada teste
        System.out.println(">>> Iniciando o EntityManager...");
        entityManager = entityManagerFactory.createEntityManager();
    }


    public void tearDown(){ // executa depois de cada teste
        System.out.println(">>> Finalizando o EntityManager...");
        entityManager.close();
    }


    @Test
    public void buscarPorId(){
        System.out.println("1. Buscando produto pelo id...");

        Produto produto = entityManager.find(Produto.class, 1);
        System.out.println(">>> Já fez a consulta!!!");

        Assert.assertNotNull("Produto não pode ser nulo", produto);
        Assert.assertEquals("Kindle", produto.getNome());
    }

    @Test
    public void buscarPorReferencia(){
        System.out.println("2. Buscando produto pelo id usando referência...");

        Produto produto = entityManager.getReference(Produto.class, 1);
        System.out.println(">>> Ainda não fez na consulta!!!");

        Assert.assertNotNull("Produto não pode ser nulo", produto);
        Assert.assertEquals("Kindle", produto.getNome());
    }


    @Test
    public void atualizarAReferencia(){
        System.out.println("3. Atualizando a referência...");

        Produto produto = entityManager.find(Produto.class, 1);
        produto.setNome("Microfone Samson"); // dessa salva não será salvo no banco

        System.out.println(">>> Atualizando o estado da instância do banco de dados, sobrescrevendo as alterações feitas na entidade, se houver");
        entityManager.refresh(produto);

        Assert.assertEquals("Kindle", produto.getNome());
    }
}
