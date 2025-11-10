package com.especialista.junit._7_mapeamentoAvancado;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.NotaFiscal;
import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.Pedido;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class _11_salvandoArquivos extends EntityManagerTest {


    @Test
    public void salvarXmlNota(){
        System.out.println("\n>>> 1. Buscando Pedido no banco de dados...");
        Pedido pedido = entityManager.find(Pedido.class, 1);

        System.out.println("\n>>> 2. Instânciando uma nova NotaFiscal...");
        NotaFiscal notaFiscal = new NotaFiscal();
        notaFiscal.setPedido(pedido);
        notaFiscal.setDataEmissao(new Date());
        notaFiscal.setXml(carregarNotaFiscal());

        System.out.println("\n>>> 3. Iniciando uma transação...");
        entityManager.getTransaction().begin();// Início da transação

        System.out.println("\n>>> 4. Colocando uma nova NotaFiscal no contexto de persistência usando o persist()...");
        entityManager.persist(notaFiscal);

        System.out.println("\n>>> 5. JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();// Fim da transação (confirma a transação)

        System.out.println("\n>>> 6. Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        System.out.println("\n>>> 7. Buscando NotaFiscal criado no banco de dados...");
        NotaFiscal notaFiscalVerificado = entityManager.find(NotaFiscal.class, notaFiscal.getId());
        Assert.assertNotNull(notaFiscalVerificado);
        Assert.assertTrue(notaFiscalVerificado.getXml().length > 0);

        try{
            File file = Files.createFile(Paths.get(System.getProperty("user.dir") + "/src/test/resources/xml.xml")).toFile();
            OutputStream outputStream = new FileOutputStream(file);

            outputStream.write(notaFiscalVerificado.getXml());

        }catch (Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }

        String userDir = System.getProperty("user.dir");
        System.out.println(">>>>>>>>>>>>>>>>>>" + userDir + "/src/test/resources");

    }


    private static byte[] carregarNotaFiscal(){
        try{
            return _11_salvandoArquivos.class.getResourceAsStream("/nota-fiscal.xml").readAllBytes();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
