package com.gabsbank.model;

public class ClienteAdicional extends Cliente {

	@Override
	public String toString() {
		return "Dependente : \n"
				+ "- Nome = " + getNomeCliente() + "\n"
				+ "- CPF = " + getCpf() + "\n"
				+ "- Endereço = " + getEndereco() + "\n"
				+ "- E-mail = " + getEmail() + "\n"
				+ "- Telefone = " + getTelefone() + "\n";
	}
	
	

}
