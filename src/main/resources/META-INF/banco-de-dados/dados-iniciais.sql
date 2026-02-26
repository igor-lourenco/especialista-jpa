
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
insert into tb_pagamento(pedido_id, status, numero_cartao, tipo_pagamento) values (2, 'PROCESSANDO', '123', 'PagamentoCartao');
insert into tb_pagamento(pedido_id, status, numero_cartao, tipo_pagamento) values (3, 'PROCESSANDO', '123', 'PagamentoCartao');
insert into tb_pagamento(pedido_id, status, numero_cartao, tipo_pagamento) values (4, 'RECEBIDO', '78912345', 'PagamentoBoleto');
insert into tb_pagamento(pedido_id, status, numero_cartao, tipo_pagamento) values (5, 'PROCESSANDO', '78954321', 'PagamentoBoleto');
insert into tb_pagamento(pedido_id, status, numero_cartao, tipo_pagamento) values (6, 'RECEBIDO', '123', 'PagamentoCartao');
insert into tb_pagamento(pedido_id, status, numero_cartao, tipo_pagamento) values (7, 'CANCELADO', '123', 'PagamentoCartao');

-- herança com TABLE_PER_CLASS
--insert into tb_pagamento_cartao(pedido_id, status, numero_cartao) values (2, 'PROCESSANDO', '123')

-- herança com JOINED
--insert into tb_pagamento(pedido_id, status, tipo_pagamento) values (2, 'PROCESSANDO', 'PagamentoCartao')
--insert into tb_pagamento_cartao(pedido_id, numero_cartao) values (2, '123')

insert into tb_categoria(id, nome) values (1, "Eletrodomésticos");

-- esta usando o timezone UTC
insert into tb_nota_fiscal (pedido_id, xml, data_emissao) values (2, '<xml />', sysdate());

insert into tb_categoria (nome) values ('Eletrodomésticos');
insert into tb_categoria (nome) values ('Livros');
insert into tb_categoria (nome) values ('Esportes');
insert into tb_categoria (nome) values ('Futebol');
insert into tb_categoria (nome) values ('Natação');
insert into tb_categoria (nome) values ('Notebooks');
insert into tb_categoria (nome) values ('Smartphones');
insert into tb_categoria (nome) values ('Eletroeletrônico');
insert into tb_categoria (nome) values ('Cama, mesa e banho');


-- ELETRODOMÉSTICOS
INSERT INTO tb_produto (data_criacao, data_ultima_atualizacao, descricao, nome, preco)
VALUES
(NOW(6), NULL, 'Geladeira Frost Free com 400 litros, classe A++', 'Geladeira Frost Free 400L', 3499.90),
(NOW(6), NULL, 'Fogão 5 bocas em inox com acendimento automático', 'Fogão 5 Bocas Inox', 2599.90),
(NOW(6), NULL, 'Micro-ondas 32L com menu rápido e função descongelar', 'Micro-ondas 32L', 699.90),
(NOW(6), NULL, 'Lavadora 12kg com turbo performance e ciclo rápido', 'Máquina de Lavar 12kg', 2199.90),
(NOW(6), NULL, 'Aspirador 2000W com filtro HEPA e cabo 5m', 'Aspirador de Pó 2000W', 499.90);

-- LIVROS
INSERT INTO tb_produto (data_criacao, data_ultima_atualizacao, descricao, nome, preco)
VALUES
(NOW(6), NULL, 'Robert C. Martin (PT-BR)', 'Livro Clean Code', 139.90),
(NOW(6), NULL, 'Eric Evans (PT-BR)', 'Livro Domain-Driven Design', 249.90),
(NOW(6), NULL, 'Robert C. Martin (PT-BR)', 'Livro Arquitetura Limpa', 159.90),
(NOW(6), NULL, 'Charles Duhigg (PT-BR)', 'Livro O Poder do Hábito', 89.90),
(NOW(6), NULL, 'Yuval Noah Harari (PT-BR)', 'Livro Sapiens', 99.90);

-- ESPORTES
INSERT INTO tb_produto (data_criacao, data_ultima_atualizacao, descricao, nome, preco)
VALUES
(NOW(6), NULL, 'Bicicleta MTB aro 29 com 24 marchas', 'Bicicleta Aro 29', 2499.90),
(NOW(6), NULL, 'Par de halteres ajustáveis até 24kg', 'Halteres Ajustáveis 24kg', 1199.90),
(NOW(6), NULL, 'Tênis de corrida leve e respirável', 'Tênis de Corrida Performance', 699.90),
(NOW(6), NULL, 'Tapete de yoga 6mm antiderrapante', 'Tapete de Yoga Antiderrapante', 159.90),
(NOW(6), NULL, 'Bola tamanho 7 em couro sintético', 'Bola de Basquete Oficial', 199.90);

-- FUTEBOL
INSERT INTO tb_produto (data_criacao, data_ultima_atualizacao, descricao, nome, preco)
VALUES
(NOW(6), NULL, 'Chuteira society com cravos baixos', 'Chuteira Society', 399.90),
(NOW(6), NULL, 'Bola para campo costurada à máquina', 'Bola de Futebol Campo', 349.90),
(NOW(6), NULL, 'Camisa oficial temporada atual', 'Camisa de Time Oficial', 299.90),
(NOW(6), NULL, 'Caneleira com proteção anatômica', 'Caneleira Profissional', 89.90),
(NOW(6), NULL, 'Luva com palma látex aderente', 'Luva de Goleiro Profissional', 499.90);

-- NATAÇÃO
INSERT INTO tb_produto (data_criacao, data_ultima_atualizacao, descricao, nome, preco)
VALUES
(NOW(6), NULL, 'Óculos antiembaçante com proteção UV', 'Óculos de Natação', 99.90),
(NOW(6), NULL, 'Touca 100% silicone tamanho adulto', 'Touca de Natação Silicone', 49.90),
(NOW(6), NULL, 'Par de nadadeiras para treino', 'Nadadeira de Natação', 249.90),
(NOW(6), NULL, 'Maiô feminino para treinamento', 'Maiô Feminino Treino', 159.90),
(NOW(6), NULL, 'Sunga masculina para treinamento', 'Sunga Masculina Treino', 129.90);

-- NOTEBOOKS
INSERT INTO tb_produto (data_criacao, data_ultima_atualizacao, descricao, nome, preco)
VALUES
(NOW(6), NULL, '15.6\" Core i5, 16GB RAM, 512GB SSD', 'Notebook 15.6 i5 16GB 512GB SSD', 3999.90),
(NOW(6), NULL, '14\" Core i7, 16GB RAM, 1TB SSD', 'Ultrabook 14 i7 16GB 1TB SSD', 6999.90),
(NOW(6), NULL, '15\" Ryzen 7, RTX 4060, 16GB, 1TB', 'Notebook Gamer Ryzen 7 RTX 4060', 8999.90),
(NOW(6), NULL, '13\" 2 em 1 com tela touch', 'Notebook 2 em 1 13 Touch', 5499.90),
(NOW(6), NULL, '14\" ChromeOS 8GB RAM 128GB', 'Chromebook 14 8GB 128GB', 1899.90);

-- SMARTPHONES
INSERT INTO tb_produto (data_criacao, data_ultima_atualizacao, descricao, nome, preco)
VALUES
(NOW(6), NULL, 'Tela 6.5\", 128GB, 5G, câmera tripla', 'Smartphone 6.5 128GB 5G', 2199.90),
(NOW(6), NULL, 'Tela 6.1\", 256GB, câmera dupla', 'Smartphone 6.1 256GB', 3999.90),
(NOW(6), NULL, 'Tela 6.8\", 512GB, versão Pro', 'Smartphone 6.8 512GB Pro', 6999.90),
(NOW(6), NULL, 'Dobrável, 256GB, 5G', 'Smartphone Dobrável 256GB 5G', 7999.90),
(NOW(6), NULL, 'Tela 5.4\", 128GB, compacto', 'Smartphone Compact 5.4 128GB', 1899.90);

-- ELETROELETRÔNICO
INSERT INTO tb_produto (data_criacao, data_ultima_atualizacao, descricao, nome, preco)
VALUES
(NOW(6), NULL, 'Smart TV 55\" 4K com HDR10+', 'Smart TV 55 4K', 2799.90),
(NOW(6), NULL, 'Soundbar 300W com Dolby Audio', 'Soundbar 300W Dolby', 1299.90),
(NOW(6), NULL, 'Console 1TB com 1 controle', 'Console de Videogame 1TB', 3999.90),
(NOW(6), NULL, 'Fone Bluetooth c/ cancelamento ativo', 'Fone Bluetooth Noise Cancelling', 1299.90),
(NOW(6), NULL, 'Roteador Wi‑Fi 6 dual band', 'Roteador Wi‑Fi 6', 499.90);

-- CAMA, MESA E BANHO
INSERT INTO tb_produto (data_criacao, data_ultima_atualizacao, descricao, nome, preco)
VALUES
(NOW(6), NULL, 'Jogo de cama queen 4 peças 400 fios', 'Jogo de Cama Queen 400 Fios', 499.90),
(NOW(6), NULL, 'Toalha 100% algodão 500g/m²', 'Toalha de Banho Gigante', 89.90),
(NOW(6), NULL, 'Travesseiro viscoelástico antiácaro', 'Travesseiro Viscoelástico', 149.90),
(NOW(6), NULL, 'Edredom queen dupla face microfibra', 'Edredom Queen Dupla Face', 359.90),
(NOW(6), NULL, 'Conjunto de 5 peças (banho/rosto/piso)', 'Jogo de Toalhas 5 Peças', 229.90);

INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
((SELECT id FROM tb_produto WHERE nome='Geladeira Frost Free 400L'), (SELECT id FROM tb_categoria WHERE nome='Eletrodomésticos')),
((SELECT id FROM tb_produto WHERE nome='Fogão 5 Bocas Inox'), (SELECT id FROM tb_categoria WHERE nome='Eletrodomésticos')),
((SELECT id FROM tb_produto WHERE nome='Micro-ondas 32L'), (SELECT id FROM tb_categoria WHERE nome='Eletrodomésticos')),
((SELECT id FROM tb_produto WHERE nome='Máquina de Lavar 12kg'), (SELECT id FROM tb_categoria WHERE nome='Eletrodomésticos')),
((SELECT id FROM tb_produto WHERE nome='Aspirador de Pó 2000W'), (SELECT id FROM tb_categoria WHERE nome='Eletrodomésticos'));

INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
((SELECT id FROM tb_produto WHERE nome='Livro Clean Code'), (SELECT id FROM tb_categoria WHERE nome='Livros')),
((SELECT id FROM tb_produto WHERE nome='Livro Domain-Driven Design'), (SELECT id FROM tb_categoria WHERE nome='Livros')),
((SELECT id FROM tb_produto WHERE nome='Livro Arquitetura Limpa'), (SELECT id FROM tb_categoria WHERE nome='Livros')),
((SELECT id FROM tb_produto WHERE nome='Livro O Poder do Hábito'), (SELECT id FROM tb_categoria WHERE nome='Livros')),
((SELECT id FROM tb_produto WHERE nome='Livro Sapiens'), (SELECT id FROM tb_categoria WHERE nome='Livros'));

INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
((SELECT id FROM tb_produto WHERE nome='Bicicleta Aro 29'), (SELECT id FROM tb_categoria WHERE nome='Esportes')),
((SELECT id FROM tb_produto WHERE nome='Halteres Ajustáveis 24kg'), (SELECT id FROM tb_categoria WHERE nome='Esportes')),
((SELECT id FROM tb_produto WHERE nome='Tênis de Corrida Performance'), (SELECT id FROM tb_categoria WHERE nome='Esportes')),
((SELECT id FROM tb_produto WHERE nome='Tapete de Yoga Antiderrapante'), (SELECT id FROM tb_categoria WHERE nome='Esportes')),
((SELECT id FROM tb_produto WHERE nome='Bola de Basquete Oficial'), (SELECT id FROM tb_categoria WHERE nome='Esportes'));

INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
((SELECT id FROM tb_produto WHERE nome='Chuteira Society'), (SELECT id FROM tb_categoria WHERE nome='Futebol')),
((SELECT id FROM tb_produto WHERE nome='Bola de Futebol Campo'), (SELECT id FROM tb_categoria WHERE nome='Futebol')),
((SELECT id FROM tb_produto WHERE nome='Camisa de Time Oficial'), (SELECT id FROM tb_categoria WHERE nome='Futebol')),
((SELECT id FROM tb_produto WHERE nome='Caneleira Profissional'), (SELECT id FROM tb_categoria WHERE nome='Futebol')),
((SELECT id FROM tb_produto WHERE nome='Luva de Goleiro Profissional'), (SELECT id FROM tb_categoria WHERE nome='Futebol'));

INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
((SELECT id FROM tb_produto WHERE nome='Óculos de Natação'), (SELECT id FROM tb_categoria WHERE nome='Natação')),
((SELECT id FROM tb_produto WHERE nome='Touca de Natação Silicone'), (SELECT id FROM tb_categoria WHERE nome='Natação')),
((SELECT id FROM tb_produto WHERE nome='Nadadeira de Natação'), (SELECT id FROM tb_categoria WHERE nome='Natação')),
((SELECT id FROM tb_produto WHERE nome='Maiô Feminino Treino'), (SELECT id FROM tb_categoria WHERE nome='Natação')),
((SELECT id FROM tb_produto WHERE nome='Sunga Masculina Treino'), (SELECT id FROM tb_categoria WHERE nome='Natação'));

INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
((SELECT id FROM tb_produto WHERE nome='Notebook 15.6 i5 16GB 512GB SSD'), (SELECT id FROM tb_categoria WHERE nome='Notebooks')),
((SELECT id FROM tb_produto WHERE nome='Ultrabook 14 i7 16GB 1TB SSD'), (SELECT id FROM tb_categoria WHERE nome='Notebooks')),
((SELECT id FROM tb_produto WHERE nome='Notebook Gamer Ryzen 7 RTX 4060'), (SELECT id FROM tb_categoria WHERE nome='Notebooks')),
((SELECT id FROM tb_produto WHERE nome='Notebook 2 em 1 13 Touch'), (SELECT id FROM tb_categoria WHERE nome='Notebooks')),
((SELECT id FROM tb_produto WHERE nome='Chromebook 14 8GB 128GB'), (SELECT id FROM tb_categoria WHERE nome='Notebooks'));

INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
((SELECT id FROM tb_produto WHERE nome='Smartphone 6.5 128GB 5G'), (SELECT id FROM tb_categoria WHERE nome='Smartphones')),
((SELECT id FROM tb_produto WHERE nome='Smartphone 6.1 256GB'), (SELECT id FROM tb_categoria WHERE nome='Smartphones')),
((SELECT id FROM tb_produto WHERE nome='Smartphone 6.8 512GB Pro'), (SELECT id FROM tb_categoria WHERE nome='Smartphones')),
((SELECT id FROM tb_produto WHERE nome='Smartphone Dobrável 256GB 5G'), (SELECT id FROM tb_categoria WHERE nome='Smartphones')),
((SELECT id FROM tb_produto WHERE nome='Smartphone Compact 5.4 128GB'), (SELECT id FROM tb_categoria WHERE nome='Smartphones'));

INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
((SELECT id FROM tb_produto WHERE nome='Smart TV 55 4K'), (SELECT id FROM tb_categoria WHERE nome='Eletroeletrônico')),
((SELECT id FROM tb_produto WHERE nome='Soundbar 300W Dolby'), (SELECT id FROM tb_categoria WHERE nome='Eletroeletrônico')),
((SELECT id FROM tb_produto WHERE nome='Console de Videogame 1TB'), (SELECT id FROM tb_categoria WHERE nome='Eletroeletrônico')),
((SELECT id FROM tb_produto WHERE nome='Fone Bluetooth Noise Cancelling'), (SELECT id FROM tb_categoria WHERE nome='Eletroeletrônico')),
((SELECT id FROM tb_produto WHERE nome='Roteador Wi‑Fi 6'), (SELECT id FROM tb_categoria WHERE nome='Eletroeletrônico'));

INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
((SELECT id FROM tb_produto WHERE nome='Jogo de Cama Queen 400 Fios'), (SELECT id FROM tb_categoria WHERE nome='Cama, mesa e banho')),
((SELECT id FROM tb_produto WHERE nome='Toalha de Banho Gigante'), (SELECT id FROM tb_categoria WHERE nome='Cama, mesa e banho')),
((SELECT id FROM tb_produto WHERE nome='Travesseiro Viscoelástico'), (SELECT id FROM tb_categoria WHERE nome='Cama, mesa e banho')),
((SELECT id FROM tb_produto WHERE nome='Edredom Queen Dupla Face'), (SELECT id FROM tb_categoria WHERE nome='Cama, mesa e banho')),
((SELECT id FROM tb_produto WHERE nome='Jogo de Toalhas 5 Peças'), (SELECT id FROM tb_categoria WHERE nome='Cama, mesa e banho'));
