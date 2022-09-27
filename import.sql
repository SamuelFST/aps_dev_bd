/* CRIAÇÃO DO BANCO */

CREATE DATABASE APS_DEV_WEB;

# Criando a tabela de usuário, onde serão salvas as informações básicas para login no sistema e o tipo de usuário
CREATE TABLE users (
	id int NOT NULL AUTO_INCREMENT,
	email varchar(70) NOT NULL UNIQUE,
	password varchar(30) NOT NULL,
	PRIMARY KEY (id)
);

# Criando a tabela de tipo de funcionário, onde vai ter a identificação se ele é Analista ou gerente
CREATE TABLE employee_type (
	id int NOT NULL AUTO_INCREMENT,
	type varchar(50) NOT NULL UNIQUE,
	PRIMARY KEY (id)
);

# Criando a tabela de funcionário, onde terão as informações pessoais do funcionário
CREATE TABLE employees (
	id int NOT NULL AUTO_INCREMENT,
	name varchar(100) NOT NULL,
	age varchar(3) NOT NULL,
	cpf char(11) NOT NULL UNIQUE,
	phone varchar(15) UNIQUE,
	id_user int NOT NULL UNIQUE,
	id_employee_type int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (id_user) REFERENCES users(id),
	FOREIGN KEY (id_employee_type) REFERENCES employee_type(id)
);

# Criando a tabela de projetos
CREATE TABLE projects (
	id int NOT NULL AUTO_INCREMENT,
	name varchar(100) NOT NULL,
	start_date date NOT NULL,
	deadline date NOT NULL,
	is_completed BOOL NOT NULL,
	PRIMARY KEY (id)
);

# Criando a tabela de associação de funcionários com projetos, já que essa será uma relação de N:N
CREATE TABLE employee_projects (
	id int NOT NULL AUTO_INCREMENT,
	id_employee int NOT NULL,
	id_project int NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (id_employee) REFERENCES employees(id),
	FOREIGN KEY (id_project) REFERENCES projects(id) 
); 

/* INSERINDO DADOS NO BANCO */

# Inserindo os valores de "Analista" e "Gerente" na tabela employee_type
INSERT INTO employee_type 
VALUES (1, "Analista"), (2, "Gerente");

# Inserindo 5 usuários na tabela users, 3 deles são analistas e 2 são gerentes
INSERT INTO users 
VALUES (1, "analista1@mail.com", "senha123"), 
	   (2, "analista2@mail.com", "senha123"),
	   (3, "analista3@mail.com", "senha123"), 
	   (4, "gerente1@mail.com", "senha123"), 
	   (5, "gerente2@mail.com", "senha123");

# Inserindo na tabela employees os usuários que foram criados anteriormente
INSERT INTO employees
VALUES (1, "Analista 01", "30", "12345678912", "214543654", 1, 1),
	   (2, "Analista 02", "42", "59863015397", "214387324", 2, 1),
	   (3, "Analista 03", "27", "73920184651", "3245542355", 3, 1),
	   (4, "Gerente 01", "48", "06583164801", "3472897304", 4, 2),
	   (5, "Gerente 02", "52", "64821064816", "23984780532", 5, 2);
	  
# Inserindo na tabela projects 6 projetos, 4 completados e 2 não completados
INSERT INTO projects 
VALUES (1, "Projeto 01", '2022-01-01', '2022-12-31', 0),
	   (2, "Projeto 02", '2019-03-14', '2020-01-06', 1),
	   (3, "Projeto 03", '2021-09-15', '2021-11-15', 1),
	   (4, "Projeto 04", '2022-04-27', '2022-08-30', 1),
	   (5, "Projeto 05", '2018-10-18', '2023-01-19', 0),
	   (6, "Projeto 06", '2019-02-16', '2021-02-15', 1);
	  
# Inserindo os dados das associações entre funcionários e projetos
INSERT INTO employee_projects
VALUES (1, 2, 6), # Analista 02 no Projeto 06
	   (2, 5, 6), # Gerente 02 no Projeto 06
	   (3, 3, 6), # Analista 03 no Projeto 06
	   (4, 1, 4), # Analista 01 no Projeto 04
	   (5, 4, 4), # Gerente 01 no Projeto 04
	   (6, 3, 1), # Analista 03 no Projeto 01
	   (7, 4, 1), # Gerente 01 no Projeto 01
	   (8, 1, 1), # Analista 01 no Projeto 01
	   (9, 5, 5); # Gerente 02 no Projeto 05

/* BUSCANDO DADOS DO BANCO */
	   
# Buscando os dados de todos os usuários do sistema junto com seus respectivos dados de funcionário e tipo (cargo)
SELECT employees.*, users.email, users.password, employee_type.type as cargo FROM employees
INNER JOIN users ON employees.id = users.id
INNER JOIN employee_type ON employees.id_employee_type = employee_type.id;

# Buscando os Gerentes responsáveis pelos projetos que ainda não foram terminados
SELECT employees.name, employees.phone, users.email, projects.*, employee_type.type as cargo FROM employees
INNER JOIN users ON employees.id = users.id
INNER JOIN employee_type ON employees.id_employee_type = employee_type.id
INNER JOIN employee_projects ep ON ep.id_employee = employees.id
INNER JOIN projects ON ep.id_project = projects.id
WHERE employee_type.type = "Gerente" AND projects.is_completed = FALSE;

# Buscando os projetos e ordenando crescente por data de início
SELECT name, start_date, deadline FROM projects p
ORDER BY start_date ASC;

# Contagem de projetos cadastrados
SELECT COUNT(*) FROM projects; 

	   

