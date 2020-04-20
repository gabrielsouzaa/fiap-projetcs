package com.gabsbank.model;

public class ClienteTitular extends Cliente {

	@Override
	public String toString() {
		return "Cliente Titular : \n"
				+ "- Nome = "+ getNomeCliente() + "\n"
				+ "- CPF = " + getCpf() + "\n"
				+ "- Endereço = " + getEndereco() + "\n"
				+ "- E-mail = " + getEmail() + "\n"
				+ "- Telefone = " + getTelefone();
	}

	
	
}
