package com.especialista.junit._7_mapeamentoAvancado;

import com.especialista.jpa._6_mapeamentoAvancado.modelos.Atributo;
import com.especialista.jpa._6_mapeamentoAvancado.modelos.Produto;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class _12_exercicioSalvarFotoProduto extends EntityManagerTest {


    @Test
    public void salvarFotoProduto(){
        System.out.println("\n>>> 1. Buscando Produto no banco de dados...");
        Produto produto = entityManager.find(Produto.class, 1);

        System.out.println("\n>>> 1. Adicionando lista de tags ao produto...");
        produto.setTags(Arrays.asList("ebook", "livro-digital"));

        System.out.println("\n>>> 2. Adicionando uma lista de Atributo ao produto...");
        produto.setAtributos(Arrays.asList(new Atributo("tela", "320x600")));

        System.out.println("\n>>> 3. Adicionando foto ao produto...");
        produto.setFoto(carregarFoto());

        System.out.println("\n>>> 4. Iniciando uma transação...");
        entityManager.getTransaction().begin();// Início da transação


        System.out.println("\n>>> 5. JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();// Fim da transação (confirma a transação)

        System.out.println("\n>>> 6. Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        System.out.println("\n>>> 7. Buscando Produto criado no banco de dados...");
        Produto produtoVerificado = entityManager.find(Produto.class, produto.getId());
        Assert.assertEquals(2, produtoVerificado.getTags().size());
        Assert.assertNotNull(produtoVerificado);
        Assert.assertTrue(produtoVerificado.getFoto().length > 0);

        try{
            File file = Files.createFile(Paths.get(System.getProperty("user.dir") + "/src/test/resources/kindle-2.jpg")).toFile();
            OutputStream outputStream = new FileOutputStream(file);

            outputStream.write(produtoVerificado.getFoto());

        }catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }

        String userDir = System.getProperty("user.dir");
        System.out.println(">>>>>>>>>>>>>>>>>>" + userDir + "/src/test/resources");

    }

    private byte[] carregarFoto() {
        try{
            return _12_exercicioSalvarFotoProduto.class.getResourceAsStream("/kindle.jpg").readAllBytes();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    private static byte[] carregarNotaFiscal(){
        try{
            return _12_exercicioSalvarFotoProduto.class.getResourceAsStream("/nota-fiscal.xml").readAllBytes();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
