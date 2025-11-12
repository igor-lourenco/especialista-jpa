##
### O que é o Maven
Ferramenta de automação e gerenciamento de projetos, para simplificar o processo de compilação, empacotamento, teste e distribuição de aplicações, além de gerenciar dependências externas.

##
### O que é o JPA
O JPA(Java Persistence API) é uma especificação da plataforma Java para mapeamento objeto-relacional(ORM). Define um conjunto de regras e interfaces para trabalhar com dados armazenados em bancos relacionais usando objetos Java, sem precisar escrever SQL manualmente para operações comuns.

##
### O que é o Hibernate
Framework de persistência para Java que implementa o JPA, mas também oferece recursos próprios além do padrão.

##
### Como funciona o `@Entity` e o `@Id` do JPA
- `@Entity` É uma anotação do JPA que indica que a classe Java é uma entidade persistente, ou seja, será mapeada para uma tabela no banco de dados.

- `@Id` Indica qual atributo da entidade é a chave primária no banco.

##
### Para que serve o persistence.xml
Arquivo de configuração para aplicações que usam JPA (Java Persistence API). Ele define como a aplicação vai se conectar ao banco de dados e quais entidades serão gerenciadas pelo provedor JPA.

##
### O que é o EntityManager
Interface responsável por gerenciar as operações de persistência das entidades, ou seja, é através dele que interage com o banco de dados usando objetos Java.

##
### O que é o EntityManagerFactory
É a fábrica de EntityManager no JPA, responsável por criar instâncias de EntityManager, que são usadas para interagir com o banco de dados.

##
### O que é o Persistence
Classe utilitária fornecida pelo JPA que serve para criar a fábrica de gerenciadores de entidades (EntityManagerFactory) com base na configuração definida no arquivo persistence.xml. Sem ele não consegue criar o EntityManager para interagir com o banco.

##
| **Componente**        | **O que é**          | **Responsabilidade**          | **Ciclo de Vida**        |
|-----------------------|----------------------| ------------------------------| -------------------------|
| **`Persistence`**          | Classe utilitária do JPA (`javax.persistence.Persistence`)                 | Cria a **`EntityManagerFactory`** lendo o `persistence.xml`                         | Usado apenas na inicialização             |
| **`EntityManagerFactory`** | Fábrica de `EntityManager`                                                 | Configura a unidade de persistência, gerencia cache e cria `EntityManager`              | Pesado → **Singleton** na aplicação       |
| **`EntityManager`**        | Interface para operações no banco                                          | Executa CRUD, consultas JPQL, gerencia ciclo de vida das entidades                    | Leve → Criado por transação ou requisição |


Relação entre eles:

Persistence → cria EntityManagerFactory.
EntityManagerFactory → cria EntityManager.
EntityManager → faz as operações no banco.
