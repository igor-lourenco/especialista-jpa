package com.especialista.junit._7_mapeamentoAvancado;

import com.especialista.jpa._7_mapeandoEntidadesParaGeracaoDeDDL.modelos.*;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class _16_herancaCom_SingleTable extends EntityManagerTest {


    @Test
    public void salvarCliente(){
        System.out.println("\n>>> 1. Instânciando novo Cliente...");
        Cliente cliente = new Cliente();
        cliente.setNome("Carlos Finotti");
        cliente.setSexo(SexoCliente.MASCULINO);
        cliente.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));
        cliente.setCpf("99988877766");

        System.out.println("\n>>> 2. Iniciando uma transação...");
        entityManager.getTransaction().begin();// Início da transação

        System.out.println("\n>>> 3. Colocando uma novo Cliente no contexto de persistência usando o persist()...");
        entityManager.persist(cliente);

        System.out.println("\n>>> 4. JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();// Fim da transação (confirma a transação)

        System.out.println("\n>>> 5. Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();


        System.out.println("\n>>> 6. Buscando Cliente criado no banco de dados...");
        Cliente clienteVerificado = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertNotNull(clienteVerificado.getSexo());
        Assert.assertNotNull(clienteVerificado.getId());

    }

    @Test
    public void buscarPagamentos(){
        System.out.println("\n>>> 1. Buscando lista de Pagamentos...");
        List<Pagamento> pagamentos = entityManager.createQuery("SELECT p FROM Pagamento p").getResultList();

        Assert.assertFalse(pagamentos.isEmpty());

        pagamentos.forEach(p -> System.out.println(">>> " + p.getClass().getSimpleName()));
    }


    @Test
    public void incluirPagamentoPedido(){
        System.out.println("\n>>> 1. Buscando Pedido no banco de dados...");
        Pedido pedido = entityManager.find(Pedido.class, 1);

        PagamentoCartao pagamentoCartao = new PagamentoCartao();
        pagamentoCartao.setPedido(pedido);
        pagamentoCartao.setStatus(StatusPagamento.PROCESSANDO);
        pagamentoCartao.setNumeroCartao("123");

        System.out.println("\n>>> 2. Iniciando uma transação...");
        entityManager.getTransaction().begin();// Início da transação

        System.out.println("\n>>> 3. Colocando uma novo PagamentoCartao no contexto de persistência usando o persist()...");
        entityManager.persist(pagamentoCartao);

        System.out.println("\n>>> 4. JPA confirmando a transação, salvando as alterações no banco de dados...");
        entityManager.getTransaction().commit();// Fim da transação (confirma a transação)

        System.out.println("\n>>> 5. Limpando o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas...");
        entityManager.clear();

        System.out.println("\n>>> 6. Buscando Pedido criado no banco de dados...");
        Pedido pedidoVerificado = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificado.getPagamento());

    }

}
