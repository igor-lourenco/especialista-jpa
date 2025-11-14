
    create table cliente_iniciando_com_jpa (
       id integer not null,
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB

    create table produto_iniciando_com_jpa (
       id integer not null,
        descricao varchar(255),
        nome varchar(255),
        preco decimal(19,2),
        primary key (id)
    ) engine=InnoDB

    create table tb_categoria (
       id integer not null auto_increment,
        nome varchar(100) not null,
        categoria_pai_id integer,
        primary key (id)
    ) engine=InnoDB

    create table tb_cliente (
       id integer not null auto_increment,
        cpf varchar(14) not null,
        nome varchar(100) not null,
        primary key (id)
    ) engine=InnoDB

    create table tb_cliente_contato (
       cliente_id integer not null,
        descricao varchar(255),
        tipo varchar(255) not null,
        primary key (cliente_id, tipo)
    ) engine=InnoDB

    create table tb_cliente_detalhe (
       data_nascimento date,
        sexo varchar(30) not null,
        cliente_id integer not null,
        primary key (cliente_id)
    ) engine=InnoDB

    create table tb_estoque (
       id integer not null auto_increment,
        quantidade integer,
        produto_id integer not null,
        primary key (id)
    ) engine=InnoDB

    create table tb_item_pedido (
       pedido_id integer not null,
        produto_id integer not null,
        preco_produto decimal(19,2) not null,
        quantidade integer not null,
        primary key (pedido_id, produto_id)
    ) engine=InnoDB

    create table tb_nota_fiscal (
       pedido_id integer not null,
        data_emissao datetime(6) not null,
        xml longblob not null,
        primary key (pedido_id)
    ) engine=InnoDB

    create table tb_pagamento (
       tipo_pagamento varchar(31) not null,
        pedido_id integer not null,
        status varchar(30) not null,
        codigo_barras varchar(100),
        numero_cartao varchar(50),
        primary key (pedido_id)
    ) engine=InnoDB

    create table tb_pedido (
       id integer not null auto_increment,
        data_conclusao datetime(6),
        data_criacao datetime(6) not null,
        data_ultima_atualizacao datetime(6),
        bairro varchar(50),
        cep varchar(9),
        cidade varchar(50),
        complemento varchar(50),
        estado varchar(2),
        logradouro varchar(100),
        numero varchar(10),
        status varchar(30) not null,
        total decimal(19,2) not null,
        cliente_id integer not null,
        primary key (id)
    ) engine=InnoDB

    create table tb_produto (
       id integer not null auto_increment,
        data_criacao datetime(6) not null,
        data_ultima_atualizacao datetime(6),
        descricao longtext,
        foto longblob,
        nome varchar(100) not null,
        preco decimal(10,2),
        primary key (id)
    ) engine=InnoDB

    create table tb_produto_atributo (
       produto_id integer not null,
        nome varchar(100) not null,
        valor varchar(255)
    ) engine=InnoDB

    create table tb_produto_categoria (
       produto_id integer not null,
        categoria_id integer not null
    ) engine=InnoDB

    create table tb_produto_tag (
       produto_id integer not null,
        tag varchar(50) not null
    ) engine=InnoDB

    alter table tb_categoria 
       add constraint unq_nome unique (nome)
create index idx_nome on tb_cliente (nome)

    alter table tb_cliente 
       add constraint unq_cpf unique (cpf)

    alter table tb_estoque 
       add constraint unq_produto_id unique (produto_id)
create index idx_nome on tb_produto (nome)

    alter table tb_produto 
       add constraint unq_nome unique (nome)

    alter table tb_categoria 
       add constraint fk_categoria_categoriapai 
       foreign key (categoria_pai_id) 
       references tb_categoria (id)

    alter table tb_cliente_contato 
       add constraint fk_cliente_contato_cliente 
       foreign key (cliente_id) 
       references tb_cliente (id)

    alter table tb_cliente_detalhe 
       add constraint fk_cliente_detalhe_cliente 
       foreign key (cliente_id) 
       references tb_cliente (id)

    alter table tb_estoque 
       add constraint fk_estoque_produto 
       foreign key (produto_id) 
       references tb_produto (id)

    alter table tb_item_pedido 
       add constraint fk_item_pedido_pedido 
       foreign key (pedido_id) 
       references tb_pedido (id)

    alter table tb_item_pedido 
       add constraint fk_item_pedido_produto 
       foreign key (produto_id) 
       references tb_produto (id)

    alter table tb_nota_fiscal 
       add constraint fk_nota_fiscal_pedido 
       foreign key (pedido_id) 
       references tb_pedido (id)

    alter table tb_pagamento 
       add constraint fk_pagamento_pedido 
       foreign key (pedido_id) 
       references tb_pedido (id)

    alter table tb_pedido 
       add constraint fk_pedido_cliente 
       foreign key (cliente_id) 
       references tb_cliente (id)

    alter table tb_produto_atributo 
       add constraint fk_produto_atributo_produto 
       foreign key (produto_id) 
       references tb_produto (id)

    alter table tb_produto_categoria 
       add constraint fk_produto_categoria_categoria 
       foreign key (categoria_id) 
       references tb_categoria (id)

    alter table tb_produto_categoria 
       add constraint fk_produto_categoria_produto 
       foreign key (produto_id) 
       references tb_produto (id)

    alter table tb_produto_tag 
       add constraint fk_produto_tag_produto 
       foreign key (produto_id) 
       references tb_produto (id)

    create table testando (
       id integer not null auto_increment,
        primary key (id)
    ) engine=InnoDB
insert into produto_iniciando_com_jpa (id, nome, preco, descricao) values (1, 'Kindle', 499.0, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.')
insert into produto_iniciando_com_jpa (id, nome, preco, descricao) values (3, 'Câmera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.')
insert into cliente_iniciando_com_jpa (id, nome) values (1, 'Fernando Medeiros')
insert into cliente_iniciando_com_jpa (id, nome) values (2, 'Marcos Mariano')
insert into tb_cliente(id, nome, cpf) values (1, 'Fernando Medeiros', '12345678901')
insert into tb_cliente(id, nome, cpf) values (2, 'Marcos Mariano', '90876543212')
insert into tb_cliente_detalhe(cliente_id, sexo, data_nascimento) values (1, 'MASCULINO', date_sub(sysdate(), interval 27 year))
insert into tb_cliente_detalhe(cliente_id, sexo, data_nascimento) values (2, 'MASCULINO', date_sub(sysdate(), interval 30 year))
insert into tb_produto(nome, preco, descricao, data_criacao) values ('Kindle', 499.0, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.', date_sub(sysdate(), interval 1 day))
insert into tb_produto(nome, preco, descricao, data_criacao) values ('Câmera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.', date_sub(sysdate(), interval 1 day))
insert into tb_pedido (cliente_id, data_criacao, total, status) values (1, date_sub(sysdate(), interval 1 day), 998.0, 'AGUARDANDO')
insert into tb_pedido (cliente_id, data_criacao, total, status) values (1, date_sub(sysdate(), interval 1 day), 499.0, 'AGUARDANDO')
insert into tb_item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (1, 1, 499.0, 2)
insert into tb_item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (2, 1, 499.0, 1)
insert into tb_pagamento(pedido_id, status, numero_cartao, tipo_pagamento) values (2, 'PROCESSANDO', '123', 'PagamentoCartao')
insert into tb_categoria(id, nome) values (1, "Eletrodomésticos")
