CREATE TABLE IF NOT EXISTS EMPRESA  (
  id uuid PRIMARY KEY,
  nome varchar(300) NOT NULL,
  cnpj varchar(14) NOT NULL UNIQUE,
  telefone varchar(14) NOT NULL UNIQUE
);