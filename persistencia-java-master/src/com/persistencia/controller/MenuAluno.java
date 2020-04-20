package com.persistencia.controller;


import java.util.Scanner;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import com.persistencia.helper.AlunoHelper;
import com.persistencia.helper.CursoHelper;
import com.persistencia.model.Aluno;
import com.persistencia.model.Curso;

public class MenuAluno implements Menu {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia-java");
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public void mostrarMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("MENU ALUNO - ESCOLA FIAP");
		System.out.println("-----------------------------");
		System.out.println("Escolha alguma opção para navegar:");
		System.out.println("1 - Cadastrar Novo Aluno");
		System.out.println("2 - Alterar dados Aluno");
		System.out.println("3 - Consutlar dados do Aluno");
		System.out.println("4 - Listar todos alunos");
		System.out.println("5 - Menu Principal");
		int opcao = scanner.nextInt();

		switch (opcao) {
		case 1:
			cadastrarAluno();
			break;
		case 2:
			alterarDadosAluno();
			break;
		case 3:
			consultarDados();
			break;
		case 4:
			listarTodosAlunos();
			break;
		case 5:
			MenuPrincipal menu = new MenuPrincipal();
			menu.mostrarMenu();
			break;
		default:
			System.out.println("OPÇÃO INVÁLIDA \n");
			mostrarMenu();
			break;
		}
	}
	
	private void listarTodosAlunos() {
		AlunoHelper helper = new AlunoHelper(emf.createEntityManager());
		System.out.println("LISTA DE ALUNOS");
		System.out.println("---------------");
		for (Aluno aluno : helper.findAll()) {
			System.out.println(aluno.toString());
		}
		mostrarMenu();
	}

	private void consultarDados() {
		Scanner scanner = new Scanner(System.in);
		AlunoHelper helper = new AlunoHelper(emf.createEntityManager());
		System.out.println("CONSULTA DE DADOS DO ALUNO");
		System.out.println("--------------------------");
		System.out.println("Digite o CPF do aluno que deseja consultar:");
		String cpf = scanner.nextLine();
		try {
			System.out.println(helper.find("cpf", cpf.trim()).toString());
			mostrarMenu();
		} catch (NoResultException e) {
			logger.warning("Não foi possível encontrar o aluno com esse CPF");
		} catch (Exception e) {
			logger.warning("Erro Inesperado");
		}
	}

	public void cadastrarAluno() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("CADASTRO DE ALUNO ");
		System.out.println("------------------");
		System.out.println("Digite as seguintes informações:");
		System.out.println("Nome completo, CPF, email, telefone, data de nascimento (separados por virgula)");
		String dadosAluno = scanner.nextLine();
		try {
			String dados[] = dadosAluno.split(",");
			Aluno aluno = new Aluno();
			aluno.setNome(dados[0].trim());
			aluno.setCpf(dados[1].trim());
			aluno.setEmail(dados[2].trim());
			aluno.setTelefone(dados[3].trim());
			aluno.setDataNascimento(dados[4].trim());
			AlunoHelper helper = new AlunoHelper(emf.createEntityManager());
			helper.salvar(aluno);
			mostrarMenu();
		} catch (Exception e) {
			System.out.println("Erro Inesperado \n");
			e.printStackTrace();
			mostrarMenu();
		}
	}

	private void alterarDadosAluno() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("ALTERAR DADOS DO ALUNO ");
		System.out.println("----------------------");
		System.out.println("Menu Edição");
		System.out.println("1 - Nome");
		System.out.println("2 - E-mail");
		System.out.println("3 - Telefone");
		System.out.println("4 - Data de Nascimento");
		System.out.println("Digite o CPF do aluno e a opção que deseja editar: CPF, opção, novo dado");
		System.out.println("Exemplo: Edição de telefone = CPF, 3, 11998765432");
		String dadosAluno = scanner.nextLine();
		try {
			String dados[] = dadosAluno.split(",");
			AlunoHelper helper = new AlunoHelper(emf.createEntityManager());
			Aluno aluno = helper.find("cpf", dados[0].trim());
			switch (dados[1].trim()) {
				case "1":
					aluno.setNome(dados[2].trim());
					break;
				case "2":
					aluno.setEmail(dados[2].trim());
					break;
				case "3":
					aluno.setTelefone(dados[2].trim());
					break;
				case "4":
					aluno.setDataNascimento(dados[2].trim());
					break;
				default:
					System.out.println("OPÇÃO INVÁLIDA");
					break;
			}
			System.out.println(helper.update(aluno));
			mostrarMenu();
		} catch (NoResultException e) {
			logger.severe("Não foi possível localizar o aluno com o CPF informado");
			mostrarMenu();
		} catch (Exception e) {
			System.out.println("Erro Inesperado \n");
			e.printStackTrace();
			mostrarMenu();
		}
		
	}

}
