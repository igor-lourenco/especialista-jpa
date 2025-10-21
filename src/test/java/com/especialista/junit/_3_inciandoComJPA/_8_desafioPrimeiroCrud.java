package com.especialista.junit._3_inciandoComJPA;

import com.especialista.jpa._2_iniciandoComJPA.modelo.ClienteIniciandoComJPA;
import com.especialista.junit.utils.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

public class _8_desafioPrimeiroCrud extends EntityManagerTest {


    @Test
    public void buscandoClientePeloId() {
        System.out.println(">>> 1. Fazendo a consulta do cliente no banco de dados...");
        ClienteIniciandoComJPA cliente = entityManager.find(ClienteIniciandoComJPA.class, 1);

        Assert.assertNotNull(cliente);
    }

    @Test
    public void inserindoCliente() {
        ClienteIniciandoComJPA novoCliente = new ClienteIniciandoComJPA();
        novoCliente.setId(3);
        novoCliente.setNome("João Green");

        entityManager.getTransaction().begin(); // Início da transação

//      Coloca o objeto no estado "managed" a partir desse momento, o EntityManager começa a gerenciar essa instância.
        entityManager.persist(novoCliente);

        System.out.println(">>> 1. Fazendo a inserção do novo cliente no banco de dados...");
        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)


//      Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.
        entityManager.clear();

        System.out.println(">>> 2. Fazendo a consulta do cliente no banco de dados...");
        ClienteIniciandoComJPA clienteCriado = entityManager.find(ClienteIniciandoComJPA.class, novoCliente.getId());
        Assert.assertNotNull(clienteCriado);
    }

    @Test
    public void atualizandoObjeto() {
        System.out.println(">>> 1. Instanciando o cliente...");

        ClienteIniciandoComJPA cliente = new ClienteIniciandoComJPA(); // os atributos que não estiver preenchido o JPA irá salvar como null
        cliente.setId(1);
        cliente.setNome("Fernando Medeiros atualizado");

        entityManager.getTransaction().begin(); // Início da transação

//      Faz consulta no banco para verificar se esse objeto existe ou não
//      E cria um novo objeto, copia os valores, retorna esse novo objeto e o adiciona na memória para ser gerenciada pelo EntityManager
        entityManager.merge(cliente);

        System.out.println(">>> 2. Fazendo a atualização do cliente no banco de dados...");
        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

//      Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.
        entityManager.clear();

        System.out.println(">>> 3. Fazendo a consulta no banco de dados para verificar se o cliente foi atualizado...");
        ClienteIniciandoComJPA clienteVerificado = entityManager.find(ClienteIniciandoComJPA.class, cliente.getId());
        Assert.assertNotNull(clienteVerificado);
        Assert.assertEquals("Fernando Medeiros atualizado", clienteVerificado.getNome());
    }


    @Test
    public void removendoObjeto() {
        System.out.println(">>> 1. Fazendo a consulta do cliente no banco de dados...");
        ClienteIniciandoComJPA cliente = entityManager.find(ClienteIniciandoComJPA.class, 2);

        entityManager.getTransaction().begin(); // Início da transação

//      Remova a instância da entidade, ou seja, da tabela.
        entityManager.remove(cliente);

        System.out.println(">>> 2. Fazendo a remoção do cliente no banco de dados...");
        entityManager.getTransaction().commit(); // Fim da transação (confirma a transação)

//      Limpa o contexto de persistência, fazendo com que todas as entidades gerenciadas sejam desanexadas.
//      entityManager.clear(); // Não é necessário para operação de remoção

        System.out.println(">>> 3. Fazendo a consulta no banco de dados para verificar se o cliente foi removido...");
        ClienteIniciandoComJPA clienteVerificado = entityManager.find(ClienteIniciandoComJPA.class, cliente.getId());
        Assert.assertNull(clienteVerificado);
    }
}
