
insert into produto_iniciando_com_jpa (id, nome, preco, descricao) values (1, 'Kindle', 499.0, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into produto_iniciando_com_jpa (id, nome, preco, descricao) values (3, 'Câmera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.');

insert into cliente_iniciando_com_jpa (id, nome) values (1, 'Fernando Medeiros');
insert into cliente_iniciando_com_jpa (id, nome) values (2, 'Marcos Mariano');

-- ###################################################################################

insert into tb_cliente(id, nome) values (1, 'Fernando Medeiros');
insert into tb_cliente(id, nome) values (2, 'Marcos Mariano');


-- date_sub(sysdate(), interval 1 day) -> para salvar com um dia a menos da data atual
insert into tb_produto(id, nome, preco, descricao, data_criacao) values (1, 'Kindle', 499.0, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.', date_sub(sysdate(), interval 1 day));
insert into tb_produto(id, nome, preco, descricao, data_criacao) values (3, 'Câmera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.', date_sub(sysdate(), interval 1 day));

insert into tb_pedido (id, cliente_id, data_criacao, total, status) values (1, 1, date_sub(sysdate(), interval 1 day), 998.0, 'AGUARDANDO');
insert into tb_pedido (id, cliente_id, data_criacao, total, status) values (2, 1, date_sub(sysdate(), interval 1 day), 499.0, 'AGUARDANDO');

insert into tb_item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (1, 1, 499.0, 2);
insert into tb_item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (2, 1, 499.0, 1);


insert into tb_pagamento(pedido_id, status, numero_cartao, tipo_pagamento) values (2, 'PROCESSANDO', '123', 'PagamentoCartao')


insert into tb_categoria(id, nome) values (1, "Eletrônicos");