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


## Cache de segundo nível (também chamado de cache compartilhado - shared cache)

O cache de 2º nível é um **cache compartilhado entre múltiplos EntityManager/transações**, geralmente no escopo do **EntityManagerFactory (aplicação)**, usado para evitar hits repetidos ao banco ao carregar entidades por ID (e, dependendo do provedor, coleções/associações e resultados de query).

- **Cache de 1º nível x 2º nível**</br>
  - **1º nível (L1):** Sempre existe, está dentro do EntityManager (um Persistence Context). Não é compartilhado.
  - **2º nível (L2):** Opcional, está **acima** do EntityManager, geralmente ligado ao **EntityManagerFactory** e pode ser compartilhado.
 
O fluxo típico é:

  1. Você chama em.find(Cliente.class, 10)
  2. JPA procura no L1 (contexto atual).
  3. Se não achar, procura no L2 (se habilitado).
  4. Se não achar, vai ao banco, traz o registro, popula L1 (e possivelmente L2).


- **O que exatamente o cache de 2º nível armazena:**</br>

  Na maioria dos provedores, o L2 armazena estado de entidade por ID (um snapshot serializável do estado). Ele não é um cache genérico de SQL ou ResultSet. É cache de entidades.</br>  Dependendo do provedor e configurações, pode armazenar também:
 
   - **Coleções/relacionamentos:** por exemplo @OneToMany, em regiões próprias (muito comum no Hibernate).
   - **Query cache (cache de resultados de consultas):** Normalmente é separado do L2 e precisa ser ligado explicitamente. Importante: **“Cache de query” ≠ “Cache de entidade”**.


- **Como a JPA “enxerga” o cache compartilhado:**

  A JPA define o conceito de **shared cache** e dá alguns controles:

  - **Habilitar/selecionar o que pode ser cacheado:**
    - @Cacheable: na entidade
    - SharedCacheMode:  **<shared-cache-mode>** no persistence.xml, modos comuns (shared-cache-mode):
      - ALL: todas as entidades podem ser cacheadas
      - NONE: desliga cache compartilhado
      - ENABLE_SELECTIVE: só cacheia se a entidade estiver @Cacheable(true)
      - DISABLE_SELECTIVE: cacheia tudo exceto @Cacheable(false)
      - UNSPECIFIED: deixa para o provedor

  - **Controlar leitura/gravação no cache via hints:**
    - JPA também define hints para controlar como a operação interage com o cache:
      - jakarta.persistence.cache.retrieveMode **(usar o cache ou não)**:
        - USE (usa cache se houver)
        - BYPASS (ignora cache ao ler)
      - jakarta.persistence.cache.storeMode **(armazena o cache ou não)**:
        - USE (pode armazenar no cache)
        - BYPASS (não armazena no cache)
        - REFRESH (atualiza o cache com o que veio do banco)


- **O cache compatilhada ajuda quando:**
  - Entidades são lidas repetidamente por ID (catálogo, parâmetros, tabelas de domínio)
  - Muitos requests pedem os mesmos objetos (ex.: Produto, Categoria, Pais, Perfil)
  - Quer reduzir latência e carga no banco.



## Concorrência e locking

Quando duas requisições (threads) tentam alterar o mesmo registro ao mesmo tempo, você pode ter:

- **Lost update:** A salva por último e “apaga” a alteração de B sem perceber.
- **Dirty read / non-repeatable read / phantom:** leituras inconsistentes dependendo do isolamento.

JPA/Hibernate operam sempre dentro de:

- Transação: @Transactional no Spring, por exemplo
- Persistence Context: O **cache de 1º nível** do EntityManager/Session

Dentro de uma mesma transação, se você carrega a entidade 2x, normalmente vem a mesma instância (do cache de 1º nível), Mas entre
transações diferentes, cada uma tem seu próprio contexto, então o Hibernate não sabe automaticamente que outra transação alterou o dado


### Dois modelos de controle de concorrência

#### Optimistic Locking (bloqueio otimista)

**Não bloqueia** ninguém enquanto lê/edita. Na hora de salvar, verifica se alguém alterou antes. Se alterou, lança Exception.

- Melhor quando:
  - Muito mais leitura do que escrita
  - Conflitos raros
  - Alta escala (evita lock no banco)
  - Se houver conflito, precisa tratar (retry, mensagem ao usuário etc.)

- Como funciona:
1. Adiciona um campo @Version na entidade
2. Hibernate inclui a versão no UPDATE/DELETE com um WHERE version = ?
3. Se 0 linhas forem atualizadas → alguém mudou a versão → conflito(lança exception).

- Também tem como pedir explicitamente que o Hibernate faça validações de versão (Lock modes otimistas):
  - **LockModeType.OPTIMISTIC**
    - Garante que haverá checagem de versão durante o flush/commit.
  - **LockModeType.OPTIMISTIC_FORCE_INCREMENT**
    - Além de checar, incrementa a versão mesmo sem mudar campos (útil para “reservar” uma entidade e invalidar caches/concorrência)

#### Pessimistic Locking (bloqueio pessimista)

**Bloqueia no banco** enquanto trabalha. Em geral vira um SELECT ... FOR UPDATE (ou variações), impedindo outros de atualizarem (e às vezes até lerem, dependendo do lock).

- Melhor quando:
  - Conflito é frequente
  - A operação não pode falhar e você prefere “esperar”
  - Precisa de forte serialização em um trecho crítico

- Custos:
  - Reduz paralelismo
  - Risco de deadlock
  - Transações longas = gargalo

- Lock modes pessimistas
  - **LockModeType.PESSIMISTIC_WRITE**
    - Trava para escrever (o mais comum)
  - **LockModeType.PESSIMISTIC_READ**
    - Trava para leitura “consistente” (depende do banco, em vários casos se comporta parecido com write lock ou lock compartilhado)
  - **LockModeType.PESSIMISTIC_FORCE_INCREMENT**
    - Trava e ainda incrementa versão (mistura conceitos)



## Multitenancy

Multitenancy no contexto de **JPA + Hibernate** é o conjunto de técnicas para fazer uma única instância da aplicação atender múltiplos “clientes/tenants” (empresas, organizações, etc.) mantendo isolamento de dados entre eles.

A JPA não define um **modo multitenant** universal, quem fornece o mecanismo é o provedor ORM (no caso, o Hibernate) via configuração e integrações específicas

### Modelos de multitenancy suportados pelo Hibernate

#### Database per tenant (DATABASE)

Cada tenant tem um banco físico separado. Isso maximiza isolamento, facilita backup por tenant, mas aumenta custo operacional (mais bancos, mais pools, migrações por tenant)

- Como funciona:
  - O provider devolve uma conexão apontando para o banco do tenant (ou escolhe o pool do tenant)

#### Schema per tenant (SCHEMA)

Um banco físico, mas um schema por tenant (ex: tenant_a.*, tenant_b.*). Isso dá bom isolamento e costuma ser um meio-termo entre custo e segurança

- Como funciona:
  - Pode ter:
    - Pool por tenant (conexão já **nasce** no schema correto)
    - Ou um pool único e, ao pegar a conexão, executar SET SCHEMA ... (ou equivalente do banco) antes de usar

####  Shared schema / row-level / discriminator (DISCRIMINATOR / “Partitioned data”)

Tudo fica no mesmo schema e mesmas tabelas, e cada linha pertence a um tenant por meio de uma coluna discriminadora (ex: tenant_id).

- Como funciona:
  - O ORM precisa garantir que toda query inclua a regra de tenant (ex: WHERE tenant_id = ?).

- Observação importante:
  - Queries nativas (native SQL) não recebem esse filtro automático, precisa filtrar manualmente