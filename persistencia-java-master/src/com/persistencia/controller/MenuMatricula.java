package com.persistencia.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import com.persistencia.helper.AlunoHelper;
import com.persistencia.helper.CursoHelper;
import com.persistencia.helper.MatriculaHelper;
import com.persistencia.model.Aluno;
import com.persistencia.model.Curso;
import com.persistencia.model.Matricula;

public class MenuMatricula implements Menu{

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia-java");
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public void mostrarMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("MENU MATRICULA - ESCOLA FIAP");
		System.out.println("-----------------------------");
		System.out.println("Escolha alguma opção para navegar:");
		System.out.println("1 - Nova Matricula - c/ Aluno Novo");
		System.out.println("2 - Nova Matricula - c/ Aluno Existente");
		System.out.println("3 - Avaliar Aluno");
		System.out.println("4 - Menu Principal");

		int opcao = scanner.nextInt();

		switch (opcao) {
		case 1:
			novaMatriculaComAlunoNovo();
			break;
		case 2:
			novaMatriculaComAlunoExistente();
			break;
		case 3:
			avaliarAluno();
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

	private void avaliarAluno() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("AVALIAR ALUNO");
		System.out.println("--------------");
		System.out.println("ETAPA 1 - Insira o CPF do aluno:");
		String cpf = scanner.nextLine();
		try {
			AlunoHelper alunoHelper = new AlunoHelper(emf.createEntityManager());
			MatriculaHelper helper = new MatriculaHelper(emf.createEntityManager());
			List<Matricula> lista = helper.listarMatriculasPorAluno(alunoHelper.find("cpf",cpf.trim()));
			for(Matricula matricula : lista) {
				System.out.println("ID: " + matricula.getId() + " | Nome Aluno:" + matricula.getAluno().getNome() 
						+ " | Curso:" + matricula.getCurso().getNome()+ " | Nota: " + matricula.getNotaAluno() + "");
			}
			if (lista.size() > 0) {
				System.out.println("\nEtapa 2 - Insira o ID da matricula e a nota => Exemplo: 35, 10");
				String idMatricula = scanner.nextLine();
				String dados[] = idMatricula.split(",");
				if (Integer.parseInt(dados[1].trim()) > 0 && Integer.parseInt(dados[1].trim()) <= 10) {
					Matricula matricula = helper.find("id", dados[0].trim());
					matricula.setNotaAluno(Integer.parseInt(dados[1].trim()));
					System.out.println(helper.update(matricula));
					mostrarMenu();
				} else {
					System.out.println("Nota inválida - Valores permitidos de 0 a 10 \n");
					mostrarMenu();
				}
			} else {
				System.out.println("Não encontramos registro de matricula para o CPF informado \n");
				mostrarMenu();
			}
		} catch (NoResultException e) {
			logger.warning("Não foi possível localizar o registro\n");
			mostrarMenu();
		} catch (Exception e) {
			logger.warning("Erro inesperado");
		}
	}

	private void novaMatriculaComAlunoNovo() {
		Scanner scanner = new Scanner(System.in);
		Matricula matricula = new Matricula();
		System.out.println("NOVA MATRICULA c/ ALUNO NOVO - ESCOLA FIAP");
		System.out.println("-----------------------------------------------");
		System.out.println("ETAPA 1 - Insira os dados do novo aluno:");
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
			AlunoHelper alunoHelper = new AlunoHelper(emf.createEntityManager());
			aluno = alunoHelper.salvar(aluno);
			matricula.setAluno(aluno);
		} catch (Exception e) {
			System.out.println("Erro Inesperado \n");
			e.printStackTrace();
			mostrarMenu();
		}

		matricularEtapa2(scanner, matricula);

		Date date = new Date();
		matricula.setDataMatricula(new Timestamp(date.getTime()));
		MatriculaHelper matriculaHelper = new MatriculaHelper(emf.createEntityManager());
		System.out.println(matriculaHelper.salvar(matricula));
		mostrarMenu();

	}

	private void novaMatriculaComAlunoExistente() {
		Scanner scanner = new Scanner(System.in);
		Matricula matricula = new Matricula();
		System.out.println("NOVA MATRICULA c/ ALUNO EXISTENTE - ESCOLA FIAP");
		System.out.println("-----------------------------------------------");
		System.out.println("ETAPA 1 - Insira o CPF do Aluno que deseja matricular:");
		String cpf = scanner.nextLine();

		try {
			AlunoHelper alunoHelper = new AlunoHelper(emf.createEntityManager());
			Aluno aluno = alunoHelper.find("cpf", cpf);
			System.out.println(aluno.toString());
			matricula.setAluno(aluno);
		} catch (NoResultException e) {
			System.out.println("Não foi possível localizar o aluno com o CPF informado");
			mostrarMenu();
		} catch (Exception e) {
			System.out.println("Erro Inesperado \n");
			e.printStackTrace();
			mostrarMenu();
		}

		matricularEtapa2(scanner, matricula);

		Date date = new Date();
		matricula.setDataMatricula(new Timestamp(date.getTime()));
		MatriculaHelper matriculaHelper = new MatriculaHelper(emf.createEntityManager());
		System.out.println(matriculaHelper.salvar(matricula));
		mostrarMenu();
	}


	private void matricularEtapa2(Scanner scanner, Matricula matricula) {
		try {
			System.out.println("ETAPA 2 - Escolha o curso");
			System.out.println("Digite o ID do curso desejado");
			int idCurso = scanner.nextInt();
			CursoHelper cursoHelper = new CursoHelper(emf.createEntityManager());
			Curso curso = cursoHelper.find("id", Integer.toString(idCurso));
			System.out.println(curso.toString());
			matricula.setCurso(curso);
		} catch (NoResultException e) {
			logger.warning("Não foi possível localizar o curso com o ID informado");
			mostrarMenu();
		} catch (Exception e) {
			logger.warning("Erro Inesperado!");
			e.printStackTrace();
			mostrarMenu();
		}	
	}

}
