create TABLE IF NOT EXISTS EMPRESA  (
  id uuid PRIMARY KEY,
  razaoSocial varchar(300) NOT NULL,
  cnpj varchar(14) NOT NULL UNIQUE,
  telefone varchar(14) NOT NULL UNIQUE,
  email varchar(300) NOT NULL UNIQUE,
  senha varchar(300) NOT NULL,
  qtdFuncionarios int NOT NULL
);