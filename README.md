# Desafio JDBC - Sistema de Cursos e Matrículas

## Descrição
Este projeto é um sistema simples desenvolvido em Java, utilizando Maven e JDBC para integração com banco de dados MySQL. A aplicação roda via terminal e permite gerenciar alunos e cursos, incluindo funcionalidades para matrículas, listagens e exclusões. O sistema explora consultas SQL com `LEFT JOIN` para apresentar relacionamentos entre alunos e cursos.

## Objetivo
Desenvolver uma aplicação que permita:
- Gerenciar alunos e cursos.
- Matricular alunos em cursos.
- Listar alunos matriculados ou não matriculados.
- Visualizar cursos e seus alunos.


## Tecnologias Utilizadas
- Java
- Maven
- JDBC
- MySQL
- Terminal (Scanner para entrada de dados)

## Modelagem

### Entidades principais

| Entidade | Atributos                           | Descrição                                |
|----------|-----------------------------------|-----------------------------------------|
| Aluno    | `id` (INT), `nome` (VARCHAR), `email` (VARCHAR) 
| Curso    | `id` (INT), `nome` (VARCHAR), `descricao` (TEXT) 
| Matricula| `aluno_id` (INT), `curso_id` (INT) 

### Estrutura do banco de dados

```sql
CREATE DATABASE curso_matricula;


USE curso_matricula;

-- Tabela aluno
CREATE TABLE  aluno (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- Tabela curso
CREATE TABLE curso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT
);

-- Tabela matricula (associação N:N)
CREATE TABLE matricula (
    aluno_id INT NOT NULL,
    curso_id INT NOT NULL,
    PRIMARY KEY (aluno_id, curso_id),
    CONSTRAINT fk_aluno FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON DELETE CASCADE,
    CONSTRAINT fk_curso FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE
);




-- View para alunos não matriculados
CREATE OR REPLACE VIEW alunos_nao_matriculados AS
SELECT a.id, a.nome, a.email
FROM aluno a
LEFT JOIN matricula m ON a.id = m.aluno_id
WHERE m.curso_id IS NULL;

-- View para total de alunos por curso
CREATE OR REPLACE VIEW total_alunos_por_curso AS
SELECT c.id AS curso_id, c.nome AS curso_nome, COUNT(m.aluno_id) AS total_alunos
FROM curso c
LEFT JOIN matricula m ON c.id = m.curso_id
GROUP BY c.id, c.nome;

-- View para cursos sem alunos
CREATE OR REPLACE VIEW cursos_sem_alunos AS
SELECT c.id, c.nome, c.descricao
FROM curso c
LEFT JOIN matricula m ON c.id = m.curso_id
WHERE m.aluno_id IS NULL;
```
