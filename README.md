## Mapeamento Objeto Relacional

Mapeamento Objeto Relacional é a representação de uma tabela de um banco de dados relacional através de classes Java.

É também conhecido como ORM ou Object Relational Mapping.

![Mapeamento_objeto_relacional.png](imagens%2FMapeamento_objeto_relacional.png)


## Estratégias paa geração de chave primária

![Estrategias_para_geracao_da_chave_primaria.png](imagens%2FEstrategias_para_geracao_da_chave_primaria.png)


## Conceito de Owner e Non-owner

No JPA os conceitos de "owner" e "non-owner" de uma relação entre entidades são fundamentais 
para entender como o mapeamento entre objetos Java e tabelas do banco de dados funciona, 
especialmente em relacionamentos bidirecionais.

- **Owner da relação:** <br/>
    A entidade **owner**(ou 'dona' da relação) é aquela que **controla o relacionamento** no 
  banco de dados. Em termos práticos, ela é responsável por manter a **chave estrangeira** que representa a associação.
   
    - O lado "owner" é o lado que **possui o mapeamento da associação** com a anotação @JoinColumn 
  (em relacionamentos @OneToOne ou @ManyToOne) ou @JoinTable (em relacionamentos @ManyToMany).
    
    - É o lado que o JPA considera para persistir ou atualizar o relacionamento no banco.

- **Non-owner da relação:** <br/>
    A entidade **non-owner** (ou "inversa") é o lado que **não controla** o relacionamento. Ela apenas
  **reflete** o relacionamento, geralmente com a anotação mappedBy.
  
    - O mappedBy indica que esse lado da relação é mapeado pelo outro lado (o owner).
  
    - Alterações feitas apenas no lado "non-owner" **não afetam** o banco de dados, a menos que o lado "owner" também seja atualizado.

## Estados de uma entidade

Uma entidade pode assumir alguns estados com relação ao EntityManager. Os estados podem ser:

- Novo (new ou transient)
- Gerenciado (managed)
- Removido (removed)
- Desanexado (detached)

![Estados_de_uma_entidade.png](imagens%2FEstados_de_uma_entidade.png)


- O estado **Transient** é o mais natural. É simplesmente quando construímos um objeto qualquer usando o operador new.
- Para estar no estado **Managed**, podemos chamar os métodos persist(), merge() ou buscar a entidade usando o **EntityManager**.
- O estado **Removed** é alcançado quando chamamos o método remove().
- O estado **Detached** quando é passada para o método detach().
- Importante notar que entidades **Detached** podem voltar a ser gerenciadas com a chamada do método merge().


## Relacionamento das Entidades da aplicação

![Entidade.png](imagens%2FEntidades.png)


## Cache de primeiro nível (também chamado de first-level cache)

O cache de 1º nível é o conjunto de entidades **gerenciadas(Managed)** dentro do
Persistence Context (o contexto de persistência do EntityManager) e existe sempre, independente de configuração.

- **No JPA, o EntityManager mantém um Persistence Context interno:**</br>
  - Chave: (Classe da entidade + ID)
  - Valor: instância da entidade já carregada/gerenciada

Na prática, é um Identity Map dentro do mesmo contexto, para um mesmo ID existe uma única instância. Isso serve pra quando fazer uma busca
de uma mesma entidade duas vezes no mesmo EntityManager retornar a mesma instância.

- **O cache de 1º nível vive dentro do EntityManager:**</br>
  - Em Hibernate, isso corresponde ao Session (que implementa o EntityManager).
  
  - Ele é **per-contexto**, não é global.
  
  - Não é compartilhado entre threads e nem entre EntityManagers diferentes.


- **Ele é preenchido quando:**</br>
  - Carrega uma entidade:
    - **find()**
    - **getReference()** (proxy)
    - Consultas JPQL/Criteria/Query que retornem entidades
  - Persiste uma entidade:
    - **persist(entity)** coloca a entidade no contexto como Managed.
  - Faz merge:
    - **merge()** pode criar/retornar uma instância Managed e colocá-la no contexto.

Demonstração prática (mesmo objeto):

![Cache_1_nivel.png](imagens%2FCache_1_nivel.png)

Isso acontece porque a segunda chamada não precisa ir ao banco: o EntityManager encontra a entidade no cache de 1º nível.

- **Melhora performance:**</br>
  - **Evita round-trips ao banco:**</br>
    - **find()** repetido não executa SQL novamente (se a entidade já estiver no contexto)
  - **Evita duplicidade de instâncias**</br>
    - Garante consistência de identidade: a == b para o mesmo ID no mesmo contexto
  - **Permite dirty checking**
    - Altera o objeto, e o provedor calcula o que precisa atualizar