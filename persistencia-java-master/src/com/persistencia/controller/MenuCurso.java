package com.persistencia.controller;

import java.util.Scanner;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import com.persistencia.helper.CursoHelper;
import com.persistencia.model.Curso;

public class MenuCurso implements Menu {
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia-java");
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public void mostrarMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("MENU CURSO - ESCOLA FIAP");
		System.out.println("-----------------------------");
		System.out.println("Escolha alguma opção para navegar:");
		System.out.println("1 - Cadastrar Novo Curso");
		System.out.println("2 - Alterar dados Curso");
		System.out.println("3 - Listar Cursos Cadastrados");	
		System.out.println("4 - Menu Principal");

		int opcao = scanner.nextInt();

		switch (opcao) {
		case 1:
			cadastrarCurso();
			break;
		case 2:
			alterarDadosCurso();
			break;
		case 3:
			listarTodosCursos();
			break;
		case 4:
			MenuPrincipal menu = new MenuPrincipal();
			menu.mostrarMenu();
			break;
		default:			
			System.out.println("OPÇÃO INVÁLIDA \n");
			mostrarMenu();
			break;
		}
	}
	
	private void listarTodosCursos() {
		CursoHelper helper = new CursoHelper(emf.createEntityManager());
		System.out.println("LISTA DE CURSOS");
		System.out.println("---------------");
		for (Curso curso : helper.findAll()) {
			System.out.println(curso.toString());
		}
		mostrarMenu();
	}

	private void cadastrarCurso() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("CADASTRO DE CURSO");
		System.out.println("-----------------");
		System.out.println("Digite as seguintes informações:");
		System.out.println("Nome do curso, carga horaria (horas), preço (com ponto para casa decimal), nome do instrutor (informa��es separadas por virgula)");
		String dadosCurso = scanner.nextLine();
		try {
			String dados[] = dadosCurso.split(",");
			if (dados.length > 4) {
				System.out.println("Entrada Inválida");
				mostrarMenu();
				return;
			}
			Curso curso = new Curso();
			curso.setNome(dados[0].trim());
			curso.setCargaHoraria(Integer.parseInt(dados[1].trim()));
			curso.setPreco(Double.parseDouble(dados[2].trim()));
			curso.setInstrutor(dados[3].trim());
			CursoHelper helper = new CursoHelper(emf.createEntityManager());
			System.out.println(helper.salvar(curso));
			mostrarMenu();
		} catch (Exception e) {
			System.out.println("Erro Inesperado \n");
			e.printStackTrace();
			mostrarMenu();
		}
	}
	
	private void alterarDadosCurso() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("ALTERAR DADOS DO CURSO ");
		System.out.println("----------------------");
		System.out.println("Menu Edição");
		System.out.println("1 - Nome");
		System.out.println("2 - Carga Horaria");
		System.out.println("3 - Preço");
		System.out.println("4 - Instrutor");
		System.out.println("Digite o ID do curso e a opção que deseja editar: ID, opção, novo dado");
		System.out.println("Exemplo: Edição de preço = ID, 3, 180.90");
		String dadosCurso = scanner.nextLine();
		try {
			String dados[] = dadosCurso.split(",");
			CursoHelper helper = new CursoHelper(emf.createEntityManager());
			Curso curso = helper.find("id", dados[0].trim());
			switch (dados[1].trim()) {
				case "1":
					curso.setNome(dados[2].trim());
					break;
				case "2":
					curso.setCargaHoraria(Integer.parseInt(dados[2].trim()));
					break;
				case "3":
					curso.setPreco(Double.parseDouble(dados[2].trim()));
					break;
				case "4":
					curso.setInstrutor((dados[2].trim()));
					break;
				default:
					System.out.println("OPÇÃO INVÁLIDA");
					break;
			}
			System.out.println(helper.update(curso));
			mostrarMenu();
		} catch (NoResultException e) {
			logger.severe("Não foi possivel localizar o curso com o ID informado");
			mostrarMenu();
		} catch (Exception e) {
			System.out.println("Erro Inesperado \n");
			e.printStackTrace();
			mostrarMenu();
		}
	}


}
