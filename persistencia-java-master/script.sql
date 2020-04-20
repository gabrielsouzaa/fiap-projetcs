CREATE DATABASE `persistencia-java` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `persistencia-java`.`aluno` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `email` varchar(80) NOT NULL,
  `telefone` char(11) NOT NULL,
  `data_nascimento` varchar(8) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

CREATE TABLE `persistencia-java`.`curso` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `carga_horaria` smallint(6) NOT NULL,
  `preco` double(10,2) NOT NULL,
  `instrutor` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

CREATE TABLE `persistencia-java`.`matricula` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_matricula` timestamp NOT NULL,
  `nota_aluno` smallint(6) DEFAULT NULL,
  `aluno_id` int(11) NOT NULL,
  `curso_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_aluno_id_idx` (`aluno_id`),
  KEY `fk_curso_id_idx` (`curso_id`),
  CONSTRAINT `fk_aluno_id` FOREIGN KEY (`aluno_id`) REFERENCES `aluno` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_curso_id` FOREIGN KEY (`curso_id`) REFERENCES `curso` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
