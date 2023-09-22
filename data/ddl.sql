create TABLE IF NOT EXISTS EMPRESA  (
  id uuid PRIMARY KEY,
  razaoSocial varchar(300) NOT NULL UNIQUE,
  cnpj varchar(14) NOT NULL UNIQUE,
  telefone varchar(14) UNIQUE,
  email varchar(300) NOT NULL UNIQUE,
  senha varchar(300) NOT NULL,
  setor varchar(300),
  qtdFuncionarios int
);

create TABLE IF NOT EXISTS ENDERECO  (
  id UUID PRIMARY KEY,
  numero varchar(5),
  cep varchar(10),
  logradouro varchar(200),
  complemento varchar(300),
  bairro varchar(300),
  uf char(2),
  fk_empresa UUID,
  foreign key (fk_empresa) references empresa(id)
);