


-- Comentado porque esta usando a propriedade: javax.persistence.schema-generation.create-source no arquivo persistence.xml
-- para gerar o esquema do banco

--==========================================================================================================================================
--=================================---------- MYSQL ---------===============================================================================
--==========================================================================================================================================

-- tabela apenas para testar se esse script esta sendo executado pelo JPA
--create table testando (id integer not null auto_increment, primary key (id)) engine=InnoDB;

--create table cliente_iniciando_com_jpa (id integer not null, nome varchar(255), primary key (id)) engine=InnoDB;
--create table produto_iniciando_com_jpa (id integer not null, descricao varchar(255), nome varchar(255), preco decimal(19,2), primary key (id)) engine=InnoDB;
--create table tb_categoria (id integer not null auto_increment, nome varchar(100) not null, categoria_pai_id integer, primary key (id)) engine=InnoDB;
--create table tb_cliente (id integer not null auto_increment, cpf varchar(14) not null, nome varchar(100) not null, primary key (id)) engine=InnoDB;
--create table tb_cliente_contato (cliente_id integer not null, descricao varchar(255), tipo varchar(255) not null, primary key (cliente_id, tipo)) engine=InnoDB;
--create table tb_cliente_detalhe (data_nascimento date, sexo varchar(30) not null, cliente_id integer not null, primary key (cliente_id)) engine=InnoDB;
--create table tb_estoque (id integer not null auto_increment, quantidade integer, produto_id integer not null, primary key (id)) engine=InnoDB;
--create table tb_item_pedido (pedido_id integer not null, produto_id integer not null, preco_produto decimal(19,2) not null, quantidade integer not null, primary key (pedido_id, produto_id)) engine=InnoDB;
--create table tb_nota_fiscal (pedido_id integer not null, data_emissao datetime(6) not null, xml longblob not null, primary key (pedido_id)) engine=InnoDB;
--create table tb_pagamento (tipo_pagamento varchar(31) not null, pedido_id integer not null, status varchar(30) not null, codigo_barras varchar(100), numero_cartao varchar(50), primary key (pedido_id)) engine=InnoDB;
--create table tb_pedido (id integer not null auto_increment, data_conclusao datetime(6), data_criacao datetime(6) not null, data_ultima_atualizacao datetime(6), bairro varchar(50), cep varchar(9), cidade varchar(50), complemento varchar(50), estado varchar(2), logradouro varchar(100), numero varchar(10), status varchar(30) not null, total decimal(19,2) not null, cliente_id integer not null, primary key (id)) engine=InnoDB;
--create table tb_produto (id integer not null auto_increment, data_criacao datetime(6) not null, data_ultima_atualizacao datetime(6), descricao longtext, foto longblob, nome varchar(100) not null, preco decimal(10,2), primary key (id)) engine=InnoDB;
--create table tb_produto_atributo (produto_id integer not null, nome varchar(100) not null, valor varchar(255)) engine=InnoDB;
--create table tb_produto_categoria (produto_id integer not null, categoria_id integer not null) engine=InnoDB;
--create table tb_produto_tag (produto_id integer not null, tag varchar(50) not null) engine=InnoDB;
--
--alter table tb_categoria add constraint unq_nome unique (nome);
--create index idx_nome on tb_cliente (nome);
--alter table tb_cliente add constraint unq_cpf unique (cpf);
--alter table tb_estoque add constraint unq_produto_id unique (produto_id);
--create index idx_nome on tb_produto (nome);
--alter table tb_produto add constraint unq_nome unique (nome);
--alter table tb_categoria add constraint fk_categoria_categoriapai foreign key (categoria_pai_id) references tb_categoria (id);
--alter table tb_cliente_contato add constraint fk_cliente_contato_cliente foreign key (cliente_id) references tb_cliente (id);
--alter table tb_cliente_detalhe add constraint fk_cliente_detalhe_cliente foreign key (cliente_id) references tb_cliente (id);
--alter table tb_estoque add constraint fk_estoque_produto foreign key (produto_id) references tb_produto (id);
--alter table tb_item_pedido add constraint fk_item_pedido_pedido foreign key (pedido_id) references tb_pedido (id);
--alter table tb_item_pedido add constraint fk_item_pedido_produto foreign key (produto_id) references tb_produto (id);
--alter table tb_nota_fiscal add constraint fk_nota_fiscal_pedido foreign key (pedido_id) references tb_pedido (id);
--alter table tb_pagamento add constraint fk_pagamento_pedido foreign key (pedido_id) references tb_pedido (id);
--alter table tb_pedido add constraint fk_pedido_cliente foreign key (cliente_id) references tb_cliente (id);
--alter table tb_produto_atributo add constraint fk_produto_atributo_produto foreign key (produto_id) references tb_produto (id);
--alter table tb_produto_categoria add constraint fk_produto_categoria_categoria foreign key (categoria_id) references tb_categoria (id);
--alter table tb_produto_categoria add constraint fk_produto_categoria_produto foreign key (produto_id) references tb_produto (id);
--alter table tb_produto_tag add constraint fk_produto_tag_produto foreign key (produto_id) references tb_produto (id);
--ALTER TABLE produto_iniciando_com_jpa ADD COLUMN ativo VARCHAR(3) NOT NULL DEFAULT 'NAO';




-- create function acima_media_faturamento(valor double) returns boolean reads sql data return valor > (select avg(total) from tb_pedido);

-- CREATE PROCEDURE buscar_nome_produto(
--	IN produto_id int, -- IN = parametro de entrada
--	OUT produto_nome varchar(255) -- OUT = retorno
--) BEGIN
--		SELECT nome INTO produto_nome
--		FROM tb_produto
--		WHERE id = produto_id;
--	END

--CREATE PROCEDURE compraram_acima_media(
--	IN ano integer
--) BEGIN
--	SELECT cli.*, clid.*
--	FROM tb_cliente cli
--		JOIN tb_cliente_detalhe clid ON clid.cliente_id = cli.id
--		JOIN tb_pedido ped ON ped.cliente_id = cli.id
--	WHERE ped.status = 'PAGO'
--		AND YEAR(ped.data_criacao) = ano
--	GROUP BY ped.cliente_id
--	having SUM(ped.total) >= (
--		SELECT AVG(total_por_cliente.sum_total)
--		FROM (
--			SELECT SUM(ped2.total) sum_total
--			FROM tb_pedido ped2
--			WHERE ped2.status = 'PAGO'
--				AND YEAR(ped2.data_criacao) = ano
--			GROUP BY ped2.cliente_id
--		) AS total_por_cliente
--	);
--END

--CREATE PROCEDURE ajustar_preco_produto(
--	 IN produto_id int,                -- Identifica qual produto será ajustado.
--	 IN percentual_ajuste double,      -- Percentual aplicado ao preço atual.
--	 OUT preco_ajustado double         -- Valor final do produto após o ajuste (retornado pela procedure).
--) BEGIN DECLARE produto_preco double; -- Declara uma variável local
--	 SELECT preco INTO produto_preco   --  Lê o preço atual do produto com o ID informado e guarda na variável produto_preco.
--	     FROM produto_iniciando_com_jpa
--	     WHERE id = produto_id;
--	 SET preco_ajustado = produto_preco + (produto_preco * percentual_ajuste); -- Calcula o novo preco, novo prec​o = prec​o atual + (prec​o atual x percentual)
--         UPDATE produto_iniciando_com_jpa SET preco = preco_ajustado -- Atualiza o novo preco calculado na tabela produto.
--	     WHERE id = produto_id;
--END

--CREATE VIEW view_clientes_acima_media AS
--	SELECT cli.*, clid.*
--	FROM tb_cliente cli
--	JOIN tb_cliente_detalhe clid
--		ON clid.cliente_id = cli.id
--	JOIN tb_pedido ped
--		ON ped.cliente_id = cli.id
--	WHERE ped.status = 'PAGO'
--	AND year(ped.data_criacao) = year(current_date)
--	GROUP BY ped.cliente_id
--	HAVING SUM(ped.total) >= (
--		SELECT avg(total_por_cliente.sum_total)
--		FROM (
--			SELECT sum(ped2.total) sum_total
--			FROM tb_pedido ped2
--			WHERE ped2.status = 'PAGO'
--			AND year(ped2.data_criacao) = year(current_date)
--			GROUP BY ped2.cliente_id)
--		AS total_por_cliente
--);

--==========================================================================================================================================
--=================================---------- POSTGRESQL ---------==========================================================================
--==========================================================================================================================================

-- =======================================
-- Obs: Tem que -- Alterar/remover: auto_increment, longtext, datetime, longblob, double, procedure, year, para o postgresql
-- =======================================

-- tabela apenas para testar se esse script esta sendo executado pelo JPA
create table testando (id integer not null, primary key (id));

--create or replace function acima_media_faturamento(valor double precision) returns integer as $$ begin return (case (valor > (select avg(total) from pedido)) when true then 1 else 0 end); end; $$ language plpgsql;
--
--create or replace function buscar_nome_produto(in produto_id int, out produto_nome varchar(255)) language plpgsql as $$ begin select nome into produto_nome from produto where id = produto_id; end; $$
--
--create or replace function compraram_acima_media(in ano integer) returns table(id int, versao int, nome varchar, cpf varchar, sexo varchar, data_nascimento timestamp) as $$ begin return query select cli.id, cli.versao, cli.nome, cli.cpf, clid.sexo, clid.data_nascimento::timestamp without time zone from cliente cli join cliente_detalhe clid on clid.cliente_id = cli.id join pedido ped on ped.cliente_id = cli.id where ped.status = 'PAGO' and extract(year from ped.data_criacao) = ano group by ped.cliente_id, cli.id, clid.cliente_id having sum(ped.total) >= (select avg(total_por_cliente.sum_total) from (select sum(ped2.total) sum_total from pedido ped2 where ped2.status = 'PAGO' and extract(year from ped2.data_criacao) = ano group by ped2.cliente_id) as total_por_cliente); end; $$ language plpgsql;
--
--create or replace function ajustar_preco_produto(in produto_id int, in percentual_ajuste double precision) returns numeric as $$ declare produto_preco numeric; preco_ajustado numeric; begin select preco from produto where id = produto_id into produto_preco; preco_ajustado := (produto_preco + (produto_preco * percentual_ajuste)); update produto set preco = preco_ajustado where id = produto_id; return preco_ajustado; end; $$ language plpgsql;
--
--create view view_clientes_acima_media as select cli.*, clid.* from cliente cli join cliente_detalhe clid on clid.cliente_id = cli.id join pedido ped on ped.cliente_id = cli.id where ped.status = 'PAGO' and extract(year from ped.data_criacao) = extract(year from current_date) group by ped.cliente_id, cli.id, clid.cliente_id having sum(ped.total) >= (select avg(total_por_cliente.sum_total) from (select sum(ped2.total) sum_total from pedido ped2 where ped2.status = 'PAGO' and extract(year from ped2.data_criacao) = extract(year from current_date) group by ped2.cliente_id) as total_por_cliente);

alter sequence hibernate_sequence restart with 50;