
    alter table tb_categoria 
       drop 
       foreign key fk_categoria_categoriapai

    alter table tb_cliente_contato 
       drop 
       foreign key fk_cliente_contato_cliente

    alter table tb_cliente_detalhe 
       drop 
       foreign key fk_cliente_detalhe_cliente

    alter table tb_estoque 
       drop 
       foreign key fk_estoque_produto

    alter table tb_item_pedido 
       drop 
       foreign key fk_item_pedido_pedido

    alter table tb_item_pedido 
       drop 
       foreign key fk_item_pedido_produto

    alter table tb_nota_fiscal 
       drop 
       foreign key fk_nota_fiscal_pedido

    alter table tb_pagamento 
       drop 
       foreign key fk_pagamento_pedido

    alter table tb_pedido 
       drop 
       foreign key fk_pedido_cliente

    alter table tb_produto_atributo 
       drop 
       foreign key fk_produto_atributo_produto

    alter table tb_produto_categoria 
       drop 
       foreign key fk_produto_categoria_categoria

    alter table tb_produto_categoria 
       drop 
       foreign key fk_produto_categoria_produto

    alter table tb_produto_tag 
       drop 
       foreign key fk_produto_tag_produto

    drop table if exists cliente_iniciando_com_jpa

    drop table if exists produto_iniciando_com_jpa

    drop table if exists tb_categoria

    drop table if exists tb_cliente

    drop table if exists tb_cliente_contato

    drop table if exists tb_cliente_detalhe

    drop table if exists tb_estoque

    drop table if exists tb_item_pedido

    drop table if exists tb_nota_fiscal

    drop table if exists tb_pagamento

    drop table if exists tb_pedido

    drop table if exists tb_produto

    drop table if exists tb_produto_atributo

    drop table if exists tb_produto_categoria

    drop table if exists tb_produto_tag

    drop table if exists testando
