


-- Obs: Tem que Alterar/remover: sysdate, date_sub, xml da nota fiscal -> para o postgresql

insert into produto_iniciando_com_jpa (id, nome, preco, descricao, ativo) values (1, 'Kindle', 499.0, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.', 'NAO');
insert into produto_iniciando_com_jpa (id, nome, preco, descricao, ativo) values (3, 'Câmera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.', 'NAO');

insert into cliente_iniciando_com_jpa (id, nome) values (1, 'Fernando Medeiros');
insert into cliente_iniciando_com_jpa (id, nome) values (2, 'Marcos Mariano');

-- ###################################################################################

insert into tb_cliente(id, versao, nome, cpf) values (1, 0, 'Fernando Medeiros', '12345678901');
insert into tb_cliente(id, versao, nome, cpf) values (2, 0, 'Marcos Mariano', '90876543212');
insert into tb_cliente(id, versao, nome, cpf) values (3, 0, 'Ana Beatriz Oliveira', '23456789012');
insert into tb_cliente(id, versao, nome, cpf) values (4, 0, 'Ricardo Santos Lima', '34567890123');
insert into tb_cliente(id, versao, nome, cpf) values (5, 0, 'Carla Mendes Rocha', '45678901234');
insert into tb_cliente(id, versao, nome, cpf) values (6, 0, 'Juliana Ferreira Paz', '56789012345');
insert into tb_cliente(id, versao, nome, cpf) values (7, 0, 'Bruno Henrique Souza', '67890123456');


-- sysdate() para mysql e now() para postgresql
-- date_sub para mysql

insert into tb_cliente_detalhe(cliente_id, sexo, data_nascimento) values (1, 'MASCULINO', (now() - interval '27 year'));
insert into tb_cliente_detalhe(cliente_id, sexo, data_nascimento) values (2, 'MASCULINO', (now() - interval '30 year'));


-- date_sub(sysdate(), interval 1 day) -> para salvar com um dia a menos da data atual
insert into tb_produto(id, versao, nome, preco, descricao, data_criacao) values (1, 0, 'Kindle', 499.0, 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.', (now() - interval '1 day'));
insert into tb_produto(id, versao, nome, preco, descricao, data_criacao) values (2, 0, 'Câmera GoPro Hero 7', 1400.0, 'Desempenho 2x melhor.', (now() - interval '1 day'));

insert into tb_pedido (id, versao, cliente_id, data_criacao, total, status) values (1, 0, 1, (now() - interval '1 day'), 998.0, 'AGUARDANDO');
insert into tb_pedido (id, versao, cliente_id, data_criacao, total, status) values (2, 0, 1, (now() - interval '1 day'), 499.0, 'AGUARDANDO');

insert into tb_item_pedido (versao, pedido_id, produto_id, preco_produto, quantidade) values (0, 1, 1, 499.0, 2);
insert into tb_item_pedido (versao, pedido_id, produto_id, preco_produto, quantidade) values (0, 2, 1, 499.0, 1);


-- esta usando o timezone UTC
insert into tb_nota_fiscal (versao, pedido_id, xml, data_emissao) values (0, 2, '<xml />'::bytea, now());

insert into tb_categoria (id, versao, nome) values (1, 0, 'Eletrodomésticos');
insert into tb_categoria (id, versao, nome) values (2, 0, 'Livros');
insert into tb_categoria (id, versao, nome) values (3, 0, 'Esportes');
insert into tb_categoria (id, versao, nome) values (4, 0, 'Futebol');
insert into tb_categoria (id, versao, nome) values (5, 0, 'Natação');
insert into tb_categoria (id, versao, nome) values (6, 0, 'Notebooks');
insert into tb_categoria (id, versao, nome) values (7, 0, 'Smartphones');
insert into tb_categoria (id, versao, nome) values (8, 0, 'Eletroeletrônico');
insert into tb_categoria (id, versao, nome) values (9, 0, 'Cama, mesa e banho');


-- ELETRODOMÉSTICOS
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (3, 0, now(), NULL, 'Geladeira Frost Free com 400 litros, classe A++', 'Geladeira Frost Free 400L', 3499.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (4, 0, now(), NULL, 'Fogão 5 bocas em inox com acendimento automático', 'Fogão 5 Bocas Inox', 2599.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (5, 0, now(), NULL, 'Micro-ondas 32L com menu rápido e função descongelar', 'Micro-ondas 32L', 699.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (6, 0, now(), NULL, 'Lavadora 12kg com turbo performance e ciclo rápido', 'Máquina de Lavar 12kg', 2199.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (7, 0, now(), NULL, 'Aspirador 2000W com filtro HEPA e cabo 5m', 'Aspirador de Pó 2000W', 499.90);


-- LIVROS
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (8, 0, now(), NULL, 'Robert C. Martin (PT-BR)', 'Livro Clean Code', 139.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (9, 0, now(), NULL, 'Eric Evans (PT-BR)', 'Livro Domain-Driven Design', 249.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (10, 0, now(), NULL, 'Robert C. Martin (PT-BR)', 'Livro Arquitetura Limpa', 159.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (11, 0, now(), NULL, 'Charles Duhigg (PT-BR)', 'Livro O Poder do Hábito', 89.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (12, 0, now(), NULL, 'Yuval Noah Harari (PT-BR)', 'Livro Sapiens', 99.90);


-- ESPORTES
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (13, 0, now(), NULL, 'Bicicleta MTB aro 29 com 24 marchas', 'Bicicleta Aro 29', 2499.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (14, 0, now(), NULL, 'Par de halteres ajustáveis até 24kg', 'Halteres Ajustáveis 24kg', 1199.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (15, 0, now(), NULL, 'Tênis de corrida leve e respirável', 'Tênis de Corrida Performance', 699.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (16, 0, now(), NULL, 'Tapete de yoga 6mm antiderrapante', 'Tapete de Yoga Antiderrapante', 159.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (17, 0, now(), NULL, 'Bola tamanho 7 em couro sintético', 'Bola de Basquete Oficial', 199.90);


-- FUTEBOL
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (18, 0, now(), NULL, 'Chuteira society com cravos baixos', 'Chuteira Society', 399.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (19, 0, now(), NULL, 'Bola para campo costurada à máquina', 'Bola de Futebol Campo', 349.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (20, 0, now(), NULL, 'Camisa oficial temporada atual', 'Camisa de Time Oficial', 299.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (21, 0, now(), NULL, 'Caneleira com proteção anatômica', 'Caneleira Profissional', 89.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (22, 0, now(), NULL, 'Luva com palma látex aderente', 'Luva de Goleiro Profissional', 499.90);


-- NATAÇÃO
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (23, 0, now(), NULL, 'Óculos antiembaçante com proteção UV', 'Óculos de Natação', 99.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (24, 0, now(), NULL, 'Touca 100% silicone tamanho adulto', 'Touca de Natação Silicone', 49.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (25, 0, now(), NULL, 'Par de nadadeiras para treino', 'Nadadeira de Natação', 249.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (26, 0, now(), NULL, 'Maiô feminino para treinamento', 'Maiô Feminino Treino', 159.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (27, 0, now(), NULL, 'Sunga masculina para treinamento', 'Sunga Masculina Treino', 129.90);


-- NOTEBOOKS
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (28, 0, now(), NULL, '15.6 pol. Core i5, 16GB RAM, 512GB SSD', 'Notebook 15.6 i5 16GB 512GB SSD', 3999.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (29, 0, now(), NULL, '14 pol. Core i7, 16GB RAM, 1TB SSD', 'Ultrabook 14 i7 16GB 1TB SSD', 6999.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (30, 0, now(), NULL, '15 pol. Ryzen 7, RTX 4060, 16GB, 1TB', 'Notebook Gamer Ryzen 7 RTX 4060', 8999.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (31, 0, now(), NULL, '13 pol. 2 em 1 com tela touch', 'Notebook 2 em 1 13 Touch', 5499.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (32, 0, now(), NULL, '14 pol. ChromeOS 8GB RAM 128GB', 'Chromebook 14 8GB 128GB', 1899.90);


-- SMARTPHONES
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (33, 0, now(), NULL, 'Tela 6.5 pol, 128GB, 5G, câmera tripla', 'Smartphone 6.5 128GB 5G', 2199.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (34, 0, now(), NULL, 'Tela 6.1 pol, 256GB, câmera dupla', 'Smartphone 6.1 256GB', 3999.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (35, 0, now(), NULL, 'Tela 6.8 pol, 512GB, versão Pro', 'Smartphone 6.8 512GB Pro', 6999.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (36, 0, now(), NULL, 'Dobrável, 256GB, 5G', 'Smartphone Dobrável 256GB 5G', 7999.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (37, 0, now(), NULL, 'Tela 5.4 pol, 128GB, compacto', 'Smartphone Compact 5.4 128GB', 1899.90);


-- ELETROELETRÔNICO
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (38, 0, now(), NULL, 'Smart TV 55 pol. 4K com HDR10+', 'Smart TV 55 4K', 2799.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (39, 0, now(), NULL, 'Soundbar 300W com Dolby Audio', 'Soundbar 300W Dolby', 1299.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (40, 0, now(), NULL, 'Console 1TB com 1 controle', 'Console de Videogame 1TB', 3999.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (41, 0, now(), NULL, 'Fone Bluetooth c/ cancelamento ativo', 'Fone Bluetooth Noise Cancelling', 1299.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (42, 0, now(), NULL, 'Roteador Wi-Fi 6 dual band', 'Roteador Wi-Fi 6', 499.90);


-- CAMA, MESA E BANHO
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (43, 0, now(), NULL, 'Jogo de cama queen 4 peças 400 fios', 'Jogo de Cama Queen 400 Fios', 499.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (44, 0, now(), NULL, 'Toalha 100% algodao 500g/m2', 'Toalha de Banho Gigante', 89.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (45, 0, now(), NULL, 'Travesseiro viscoelastico antiacaro', 'Travesseiro Viscoelástico', 149.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (46, 0, now(), NULL, 'Edredom queen dupla face microfibra', 'Edredom Queen Dupla Face', 359.90);
INSERT INTO tb_produto (id, versao, data_criacao, data_ultima_atualizacao, descricao, nome, preco) VALUES (47, 0, now(), NULL, 'Conjunto de 5 pecas (banho/rosto/piso)', 'Jogo de Toalhas 5 Peças', 229.90);


INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Geladeira Frost Free 400L'), (SELECT id FROM tb_categoria WHERE nome='Eletrodomésticos'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Fogão 5 Bocas Inox'), (SELECT id FROM tb_categoria WHERE nome='Eletrodomésticos'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Micro-ondas 32L'), (SELECT id FROM tb_categoria WHERE nome='Eletrodomésticos'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Máquina de Lavar 12kg'), (SELECT id FROM tb_categoria WHERE nome='Eletrodomésticos'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Aspirador de Pó 2000W'), (SELECT id FROM tb_categoria WHERE nome='Eletrodomésticos'));


INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Livro Clean Code'), (SELECT id FROM tb_categoria WHERE nome='Livros'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Livro Domain-Driven Design'), (SELECT id FROM tb_categoria WHERE nome='Livros'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Livro Arquitetura Limpa'), (SELECT id FROM tb_categoria WHERE nome='Livros'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Livro O Poder do Hábito'), (SELECT id FROM tb_categoria WHERE nome='Livros'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Livro Sapiens'), (SELECT id FROM tb_categoria WHERE nome='Livros'));


INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Bicicleta Aro 29'), (SELECT id FROM tb_categoria WHERE nome='Esportes'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Halteres Ajustáveis 24kg'), (SELECT id FROM tb_categoria WHERE nome='Esportes'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Tênis de Corrida Performance'), (SELECT id FROM tb_categoria WHERE nome='Esportes'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Tapete de Yoga Antiderrapante'), (SELECT id FROM tb_categoria WHERE nome='Esportes'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Bola de Basquete Oficial'), (SELECT id FROM tb_categoria WHERE nome='Esportes'));


INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Chuteira Society'), (SELECT id FROM tb_categoria WHERE nome='Futebol'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Bola de Futebol Campo'), (SELECT id FROM tb_categoria WHERE nome='Futebol'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Camisa de Time Oficial'), (SELECT id FROM tb_categoria WHERE nome='Futebol'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Caneleira Profissional'), (SELECT id FROM tb_categoria WHERE nome='Futebol'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Luva de Goleiro Profissional'), (SELECT id FROM tb_categoria WHERE nome='Futebol'));


INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Óculos de Natação'), (SELECT id FROM tb_categoria WHERE nome='Natação'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Touca de Natação Silicone'), (SELECT id FROM tb_categoria WHERE nome='Natação'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Nadadeira de Natação'), (SELECT id FROM tb_categoria WHERE nome='Natação'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Maiô Feminino Treino'), (SELECT id FROM tb_categoria WHERE nome='Natação'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Sunga Masculina Treino'), (SELECT id FROM tb_categoria WHERE nome='Natação'));


INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Notebook 15.6 i5 16GB 512GB SSD'), (SELECT id FROM tb_categoria WHERE nome='Notebooks'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Ultrabook 14 i7 16GB 1TB SSD'), (SELECT id FROM tb_categoria WHERE nome='Notebooks'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Notebook Gamer Ryzen 7 RTX 4060'), (SELECT id FROM tb_categoria WHERE nome='Notebooks'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Notebook 2 em 1 13 Touch'), (SELECT id FROM tb_categoria WHERE nome='Notebooks'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Chromebook 14 8GB 128GB'), (SELECT id FROM tb_categoria WHERE nome='Notebooks'));


INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Smartphone 6.5 128GB 5G'), (SELECT id FROM tb_categoria WHERE nome='Smartphones'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Smartphone 6.1 256GB'), (SELECT id FROM tb_categoria WHERE nome='Smartphones'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Smartphone 6.8 512GB Pro'), (SELECT id FROM tb_categoria WHERE nome='Smartphones'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Smartphone Dobrável 256GB 5G'), (SELECT id FROM tb_categoria WHERE nome='Smartphones'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Smartphone Compact 5.4 128GB'), (SELECT id FROM tb_categoria WHERE nome='Smartphones'));


INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Smart TV 55 4K'), (SELECT id FROM tb_categoria WHERE nome='Eletroeletrônico'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Soundbar 300W Dolby'), (SELECT id FROM tb_categoria WHERE nome='Eletroeletrônico'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Console de Videogame 1TB'), (SELECT id FROM tb_categoria WHERE nome='Eletroeletrônico'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Fone Bluetooth Noise Cancelling'), (SELECT id FROM tb_categoria WHERE nome='Eletroeletrônico'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Roteador Wi-Fi 6'), (SELECT id FROM tb_categoria WHERE nome='Eletroeletrônico'));


INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Jogo de Cama Queen 400 Fios'), (SELECT id FROM tb_categoria WHERE nome='Cama, mesa e banho'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Toalha de Banho Gigante'), (SELECT id FROM tb_categoria WHERE nome='Cama, mesa e banho'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Travesseiro Viscoelástico'), (SELECT id FROM tb_categoria WHERE nome='Cama, mesa e banho'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Edredom Queen Dupla Face'), (SELECT id FROM tb_categoria WHERE nome='Cama, mesa e banho'));
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES ((SELECT id FROM tb_produto WHERE nome='Jogo de Toalhas 5 Peças'), (SELECT id FROM tb_categoria WHERE nome='Cama, mesa e banho'));


-- ========= 1) Pedido: Livro Domain-Driven Design (id_produto = 10) - cliente 3 =========
INSERT INTO tb_pedido (id, versao, data_conclusao, data_criacao, data_ultima_atualizacao, bairro, cep, cidade, complemento, estado, logradouro, numero, status, total, cliente_id) VALUES (3, 0, NULL, '2026-03-01 10:05:00', NULL, 'Centro', '06401-000', 'Barueri', 'Apto 12', 'SP', 'Avenida 26 de Março', '100','AGUARDANDO', 249.90, 3);

INSERT INTO tb_item_pedido (versao, pedido_id, produto_id, preco_produto, quantidade) SELECT 0, p.id, 10, 249.90, 1 FROM tb_pedido p WHERE p.data_criacao = '2026-03-01 10:05:00' AND p.cliente_id = 3 AND p.total = 249.90 AND p.status = 'AGUARDANDO' AND p.bairro = 'Centro' AND p.cep = '06401-000' AND p.cidade = 'Barueri' AND p.complemento = 'Apto 12' AND p.estado = 'SP' AND p.logradouro = 'Avenida 26 de Março' AND p.numero = '100' ORDER BY p.id DESC LIMIT 1;


-- ========= 2) Pedido: Caneleira Profissional (id_produto = 22) - cliente 2 =========
INSERT INTO tb_pedido (id, versao, data_conclusao, data_criacao, data_ultima_atualizacao, bairro, cep, cidade, complemento, estado, logradouro, numero, status, total, cliente_id) VALUES (4, 0, NULL, '2026-03-01 10:10:00', NULL,'Vila Olímpia', '04538-132', 'São Paulo', 'Conj. 503', 'SP', 'Rua Olimpíadas', '350','AGUARDANDO', 89.90, 2);

INSERT INTO tb_item_pedido (versao, pedido_id, produto_id, preco_produto, quantidade) SELECT 0, p.id, 22, 89.90, 1 FROM tb_pedido p WHERE p.data_criacao = '2026-03-01 10:10:00' AND p.cliente_id = 2 AND p.total = 89.90 AND p.status = 'AGUARDANDO' AND p.bairro = 'Vila Olímpia' AND p.cep = '04538-132' AND p.cidade = 'São Paulo' AND p.complemento = 'Conj. 503' AND p.estado = 'SP' AND p.logradouro = 'Rua Olimpíadas' AND p.numero = '350' ORDER BY p.id DESC LIMIT 1;


-- ========= 3) Pedido: Toalha de Banho Gigante (id_produto = 45) - cliente 5 =========
INSERT INTO tb_pedido (id, versao, data_conclusao, data_criacao, data_ultima_atualizacao, bairro, cep, cidade, complemento, estado, logradouro, numero, status, total, cliente_id) VALUES (5, 0, NULL, '2026-03-01 10:15:00', NULL,'Centro', '06020-010', 'Osasco', 'Sala 201', 'SP', 'Avenida dos Autonomistas', '1450', 'AGUARDANDO', 89.90, 5);

INSERT INTO tb_item_pedido (versao, pedido_id, produto_id, preco_produto, quantidade) SELECT 0, p.id, 45, 89.90, 1 FROM tb_pedido p WHERE p.data_criacao = '2026-03-01 10:15:00' AND p.cliente_id = 5 AND p.total = 89.90 AND p.status = 'AGUARDANDO' AND p.bairro = 'Centro' AND p.cep = '06020-010' AND p.cidade = 'Osasco' AND p.complemento = 'Sala 201' AND p.estado = 'SP' AND p.logradouro = 'Avenida dos Autonomistas' AND p.numero = '1450' ORDER BY p.id DESC LIMIT 1;



-- ========= 4) Pedido: Notebook 2 em 1 13 Touch (id_produto = 32) - cliente 1 =========
INSERT INTO tb_pedido (id, versao, data_conclusao, data_criacao, data_ultima_atualizacao, bairro, cep, cidade, complemento, estado, logradouro, numero, status, total, cliente_id) VALUES (6, 0, NULL, '2026-03-01 10:20:00', NULL,'Bela Vista', '01310-000', 'São Paulo', 'Apto 84', 'SP', 'Avenida Paulista', '1578', 'AGUARDANDO', 5499.90, 1);

INSERT INTO tb_item_pedido (versao, pedido_id, produto_id, preco_produto, quantidade) SELECT 0, p.id, 32, 5499.90, 1 FROM tb_pedido p WHERE p.data_criacao = '2026-03-01 10:20:00' AND p.cliente_id = 1 AND p.total = 5499.90 AND p.status = 'AGUARDANDO' AND p.bairro = 'Bela Vista' AND p.cep = '01310-000' AND p.cidade = 'São Paulo' AND p.complemento = 'Apto 84' AND p.estado = 'SP' AND p.logradouro = 'Avenida Paulista' AND p.numero = '1578' ORDER BY p.id DESC LIMIT 1;


-- ========= 5) Pedido: Micro-ondas 32L (id_produto = 6) - cliente 6 =========
INSERT INTO tb_pedido (id, versao, data_conclusao, data_criacao, data_ultima_atualizacao,bairro, cep, cidade, complemento, estado, logradouro, numero,status, total, cliente_id) VALUES (7, 0, NULL, '2026-03-01 10:25:00', NULL, 'Moema', '04094-050', 'São Paulo', 'Casa 2', 'SP', 'Alameda Jauaperi', '220','AGUARDANDO', 699.90, 6);

INSERT INTO tb_item_pedido (versao, pedido_id, produto_id, preco_produto, quantidade) SELECT 0, p.id, 6, 699.90, 1 FROM tb_pedido p WHERE p.data_criacao = '2026-03-01 10:25:00' AND p.cliente_id = 6 AND p.total = 699.90 AND p.status = 'AGUARDANDO' AND p.bairro = 'Moema' AND p.cep = '04094-050' AND p.cidade = 'São Paulo' AND p.complemento = 'Casa 2' AND p.estado = 'SP' AND p.logradouro = 'Alameda Jauaperi' AND p.numero = '220' ORDER BY p.id DESC LIMIT 1;


-- herança com SINGLE_TABLE
insert into tb_pagamento(versao, pedido_id, status, numero_cartao, tipo_pagamento) values (0, 2, 'PROCESSANDO', '123', 'PagamentoCartao');
insert into tb_pagamento(versao, pedido_id, status, numero_cartao, tipo_pagamento) values (0, 3, 'PROCESSANDO', '123', 'PagamentoCartao');
insert into tb_pagamento(versao, pedido_id, status, numero_cartao, tipo_pagamento) values (0, 4, 'RECEBIDO', '78912345', 'PagamentoBoleto');
insert into tb_pagamento(versao, pedido_id, status, numero_cartao, tipo_pagamento) values (0, 5, 'PROCESSANDO', '78954321', 'PagamentoBoleto');
insert into tb_pagamento(versao, pedido_id, status, numero_cartao, tipo_pagamento) values (0, 6, 'RECEBIDO', '123', 'PagamentoCartao');
insert into tb_pagamento(versao, pedido_id, status, numero_cartao, tipo_pagamento) values (0, 7, 'CANCELADO', '123', 'PagamentoCartao');

-- herança com TABLE_PER_CLASS
--insert into tb_pagamento_cartao(versao, pedido_id, status, numero_cartao) values (0, 2, 'PROCESSANDO', '123')

-- herança com JOINED
--insert into tb_pagamento(versao, pedido_id, status, tipo_pagamento) values (0, 2, 'PROCESSANDO', 'PagamentoCartao')
--insert into tb_pagamento_cartao(versao, pedido_id, numero_cartao) values (0, 2, '123')