
insert into produto_iniciando_com_jpa (id, nome, preco, descricao) values (1, 'Kindle', 499.0, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into produto_iniciando_com_jpa (id, nome, preco, descricao) values (3, 'Câmera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.');

insert into cliente_iniciando_com_jpa (id, nome) values (1, 'Fernando Medeiros');
insert into cliente_iniciando_com_jpa (id, nome) values (2, 'Marcos Mariano');

-- ###################################################################################

insert into tb_cliente(id, nome, cpf) values (1, 'Fernando Medeiros', '12345678901');
insert into tb_cliente(id, nome, cpf) values (2, 'Marcos Mariano', '90876543212');

insert into tb_cliente_detalhe(cliente_id, sexo, data_nascimento) values (1, 'MASCULINO', date_sub(sysdate(), interval 27 year));
insert into tb_cliente_detalhe(cliente_id, sexo, data_nascimento) values (2, 'MASCULINO', date_sub(sysdate(), interval 30 year));


-- date_sub(sysdate(), interval 1 day) -> para salvar com um dia a menos da data atual
insert into tb_produto(nome, preco, descricao, data_criacao) values ('Kindle', 499.0, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.', date_sub(sysdate(), interval 1 day));
insert into tb_produto(nome, preco, descricao, data_criacao) values ('Câmera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.', date_sub(sysdate(), interval 1 day));

insert into tb_pedido (cliente_id, data_criacao, total, status) values (1, date_sub(sysdate(), interval 1 day), 998.0, 'AGUARDANDO');
insert into tb_pedido (cliente_id, data_criacao, total, status) values (1, date_sub(sysdate(), interval 1 day), 499.0, 'AGUARDANDO');

insert into tb_item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (1, 1, 499.0, 2);
insert into tb_item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (2, 1, 499.0, 1);


-- herança com SINGLE_TABLE
insert into tb_pagamento(pedido_id, status, numero_cartao, tipo_pagamento) values (2, 'PROCESSANDO', '123', 'PagamentoCartao')

-- herança com TABLE_PER_CLASS
--insert into tb_pagamento_cartao(pedido_id, status, numero_cartao) values (2, 'PROCESSANDO', '123')

-- herança com JOINED
--insert into tb_pagamento(pedido_id, status, tipo_pagamento) values (2, 'PROCESSANDO', 'PagamentoCartao')
--insert into tb_pagamento_cartao(pedido_id, numero_cartao) values (2, '123')

insert into tb_categoria(id, nome) values (1, "Eletrodomésticos");