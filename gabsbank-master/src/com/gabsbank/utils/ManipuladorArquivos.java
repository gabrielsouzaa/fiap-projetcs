	package com.gabsbank.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ManipuladorArquivos {
	
	public static void editarArquivo(String cpf, int numeroLinha, String texto) throws IOException {
	    Path path = Paths.get("contas/"+cpf.trim()+".txt");
	    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
	    lines.set(numeroLinha, texto);
	    Files.write(path, lines, StandardCharsets.UTF_8);
	}
	
	public static void escreverArquivo(String cpf, int numeroLinha, String texto) throws IOException {
		File file = new File("contas/"+cpf.trim()+".txt");
		file.createNewFile();
		Path path = Paths.get("contas/"+cpf.trim()+".txt");
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		if (lines.size() == 0) {
			lines.add(texto);
			Files.write(path, lines, StandardCharsets.UTF_8);
		} else {
			if (lines.size() < numeroLinha) {
				lines.add(lines.size(), "");
			    Files.write(path, lines, StandardCharsets.UTF_8);
			}
		    lines.add(numeroLinha, texto);
		    Files.write(path, lines, StandardCharsets.UTF_8);
		}
	}
	
	public static String lerArquivo(String cpf, int numeroLinha) {
		Path path = Paths.get("contas/"+cpf.trim()+".txt");
		String linha = null;
		List<String> lines;
		try {
			lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			if (lines.size() > numeroLinha)
		        linha = Files.readAllLines(Paths.get("contas/"+cpf.trim()+".txt")).get(numeroLinha);
			else 
				linha = "";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return linha;
    }
}
