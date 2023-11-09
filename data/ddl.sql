create TABLE IF NOT EXISTS ENDERECO  (
  id UUID PRIMARY KEY,
  numero varchar(5),
  cep varchar(10),
  logradouro varchar(200),
  complemento varchar(300),
  bairro varchar(300),
  uf char(2)
);

create TABLE IF NOT EXISTS PLANO(
    id int PRIMARY KEY,
    tipo varchar(45),
    valor decimal(10,2)
);

create TABLE IF NOT EXISTS EMPRESA  (
  id uuid PRIMARY KEY,
  razao_social varchar(300) NOT NULL UNIQUE,
  cnpj varchar(14) NOT NULL UNIQUE,
  telefone varchar(14) UNIQUE,
  email varchar(300) NOT NULL UNIQUE,
  senha varchar(300) NOT NULL,
  setor varchar(300),
  qtd_funcionarios int,
  fk_endereco uuid,
  assinante_newsletter boolean,
  fk_plano int DEFAULT 1,
  foreign key (fk_plano) references plano(id),
  foreign key (fk_endereco) references endereco(id)
);


truncate table empresa cascade;
truncate table endereco;

INSERT INTO ENDERECO (id, numero, cep, logradouro, complemento, bairro, uf)
VALUES ('6ba7b810-9dad-11d1-80b4-00c04fd430c8', '456', '54321-876', 'Avenida Central', 'Andar 5', 'Bairro Norte', 'RJ');

INSERT INTO ENDERECO (id, numero, cep, logradouro, complemento, bairro, uf)
VALUES ('6ba7b811-9dad-11d1-80b4-00c04fd430c8', '789', '67890-123', 'Rua da Saúde', 'Sala 202', 'Bairro Sul', 'SP');

INSERT INTO ENDERECO (id, numero, cep, logradouro, complemento, bairro, uf)
VALUES ('6ba7b812-9dad-11d1-80b4-00c04fd430c8', '101', '76543-210', 'Avenida da Educação', 'Andar 3', 'Bairro Oeste', 'RJ');

INSERT INTO ENDERECO (id, numero, cep, logradouro, complemento, bairro, uf)
VALUES ('6ba7b813-9dad-11d1-80b4-00c04fd430c8', '123', '12345-678', 'Rua Principal', 'Sala 101', 'Bairro Central', 'SP');

-- Inserir dados na tabela PLANO

INSERT INTO PLANO (id, tipo, valor)
VALUES (1, 'free', 0.00);

INSERT INTO PLANO (id, tipo, valor)
VALUES (2, 'analytics', 9.99);

INSERT INTO PLANO (id, tipo, valor)
VALUES (3, 'provider', 19.99);

-- Inserir dados na tabela EMPRESA
INSERT INTO EMPRESA (id, razao_social, cnpj, telefone, email, senha, setor, qtd_funcionarios, assinante_newsletter, fk_endereco, fk_plano)
VALUES ('6ba7b810-9dad-11d1-80b4-00c04fd430c1', 'Empresa A', '12345678901234', '1234567890', 'empresaA@email.com', 'senha123', 'Tecnologia', 50, true, '6ba7b810-9dad-11d1-80b4-00c04fd430c8', 1);

INSERT INTO EMPRESA (id, razao_social, cnpj, telefone, email, senha, setor, qtd_funcionarios, assinante_newsletter, fk_endereco, fk_plano)
VALUES ('6ba7b811-9dad-11d1-80b4-00c04fd430c2', 'Empresa B', '98765432104321', '9876543210', 'empresaB@email.com', 'senha456', 'Varejo', 100, false, '6ba7b811-9dad-11d1-80b4-00c04fd430c8', 2);

INSERT INTO EMPRESA (id, razao_social, cnpj, telefone, email, senha, setor, qtd_funcionarios, assinante_newsletter, fk_endereco, fk_plano)
VALUES ('6ba7b812-9dad-11d1-80b4-00c04fd430c3', 'Empresa C', '55555555555555', '5555555555', 'empresaC@email.com', 'senha789', 'Saúde', 25, true, '6ba7b812-9dad-11d1-80b4-00c04fd430c8', 1);

INSERT INTO EMPRESA (id, razao_social, cnpj, telefone, email, senha, setor, qtd_funcionarios, assinante_newsletter, fk_endereco, fk_plano)
VALUES ('6ba7b813-9dad-11d1-80b4-00c04fd430c4', 'Empresa D', '77777777777777', '7777777777', 'empresaD@email.com', 'senhaabc', 'Educação', 75, false, '6ba7b813-9dad-11d1-80b4-00c04fd430c8', 3);

