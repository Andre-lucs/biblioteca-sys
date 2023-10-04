-- Create table cliente
CREATE TABLE cliente (
  id serial PRIMARY KEY,
  Nome VARCHAR(50) NOT NULL,
  Senha VARCHAR(20) NOT NULL,
  Email VARCHAR(50) NOT NULL UNIQUE ,
  Telefone VARCHAR(15) NOT NULL,
  CPF VARCHAR(11) NOT NULL UNIQUE,
  Idade INT NOT NULL,
);

-- Create table administrador
CREATE TABLE administrador (
  id serial PRIMARY KEY,
  Nome VARCHAR(50) NOT NULL,
  Senha VARCHAR(20) NOT NULL,
  Email VARCHAR(50) NOT NULL UNIQUE ,
  Nivel INTEGER NOT NULL,
);

-- Create table livro
CREATE TABLE livro (
  id serial PRIMARY KEY,
  Titulo VARCHAR(100) NOT NULL UNIQUE,
  Autor VARCHAR(50) NOT NULL,
  NumPag INT NOT NULL,
  Assuntos VARCHAR(50) [] NOT NULL,
  Edicao INT NOT NULL,
  Capa VARCHAR(255) NOT NULL
);

-- Create table historico
CREATE TABLE status (
    id int primary key,
    name VARCHAR(20) NOT NULL
);

INSERT INTO status (id, name) VALUES
  (1, 'emprestado'),
  (2, 'devolvido'),
  (3, 'atrasado');

CREATE TABLE historico (
  id serial PRIMARY KEY,
  Cliente_id BIGINT NOT NULL,
  Livro_id BIGINT NOT NULL,
  Data_emp DATE NOT NULL,
  Periodo INTEGER NOT NULL,
  Periodo_format CHAR NOT NULL,
	Status INT NOT NULL,
	FOREIGN KEY (Status) REFERENCES status (id),
  FOREIGN KEY (Cliente_id) REFERENCES cliente (id),
  FOREIGN KEY (Livro_id) REFERENCES livro (id)
);

CREATE TABLE livros_quantidade (
  Livro_id BIGINT NOT NULL,
  Quantidade_disponivel INTEGER NOT NULL DEFAULT 0,
  PRIMARY KEY (Livro_id),
  FOREIGN KEY (livro_id) REFERENCES livro (id)
);
