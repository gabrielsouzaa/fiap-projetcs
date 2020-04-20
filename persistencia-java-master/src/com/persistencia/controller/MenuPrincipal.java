package com.persistencia.controller;
import java.util.Scanner;

public class MenuPrincipal implements Menu{
	
	Scanner scanner = new Scanner(System.in);

	@Override
	public void mostrarMenu() {
		System.out.println("MENU PRINCIPAL - ESCOLA FIAP");
		System.out.println("-----------------------------");
		System.out.println("Escolha alguma opção para navegar:");
		System.out.println("1 - Menu Cursos");
		System.out.println("2 - Menu Alunos");
		System.out.println("3 - Manu Matricula");
		System.out.println("4 - Sair");
		
		int opcao = scanner.nextInt();
		
		switch (opcao) {
		case 1:
			MenuCurso menuCurso = new MenuCurso();
			menuCurso.mostrarMenu();
			break;
		case 2:
			MenuAluno menuAluno = new MenuAluno();
			menuAluno.mostrarMenu();
			break;
		case 3:
			MenuMatricula menuMatricula = new MenuMatricula();
			menuMatricula.mostrarMenu();
			break;
		case 4:
			System.out.println("ENCERRANDO DO PROGRAMA ...");
			System.out.println("PROGRAMA ENCERRADO");
			System.exit(0);
			break;
		default:
			System.out.println("OPÇÃO INVÁLIDA \n");
			mostrarMenu();
			break;
		}
		
	}
	
	

}
