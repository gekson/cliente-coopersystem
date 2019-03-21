INSERT INTO permissoes(nome) VALUES('PERMISSAO_ADMIN');
INSERT INTO permissoes(nome) VALUES('PERMISSAO_COMUM');

INSERT INTO usuarios(login, senha, CREATED_AT, UPDATED_AT) VALUES('admin', '$2a$10$1zhxT2mb6bnNsz9i1Vh5veKBAMAxBfjBhXYEYQjhDXzGWGWyIo70i', '2019-03-20', '2019-03-20');
INSERT INTO usuarios(login, senha, CREATED_AT, UPDATED_AT) VALUES('comum', '$2a$10$1zhxT2mb6bnNsz9i1Vh5veKBAMAxBfjBhXYEYQjhDXzGWGWyIo70i', '2019-03-20', '2019-03-20');

INSERT INTO usuario_permissoes(usuario_id, permissao_id) VALUES(1,1);
INSERT INTO usuario_permissoes(usuario_id, permissao_id) VALUES(2,2);

INSERT INTO clientes(nome,cpf,cep,logradouro,bairro,cidade,uf, CREATED_AT, UPDATED_AT, created_By) VALUES('Teste', '12345678911', '12345-78', 'logradouro', 'bairro', 'cidade', 'DF','2019-03-20', '2019-03-20', 1 );