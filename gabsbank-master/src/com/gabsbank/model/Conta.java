package com.gabsbank.model;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.gabsbank.utils.ManipuladorArquivos;
import com.gabsbank.utils.UtilsDateTime;

public class Conta {
	
	private double saldo;
	private ClienteTitular cliente;
	private List<ClienteAdicional> listaAdicionais;
	private static final double TARIFA_SAQUE = 2.5;
	private static final int TARIFA_EXTRATO = 1;
	private static final int TARIFA_EMPRESTIMO = 15;
	DecimalFormat fmt = new DecimalFormat("0.00");
	
	public String sacar(String cpf, String valor) {
		String mensagem = "";
		try {
			String linha = ManipuladorArquivos.lerArquivo(cpf, 2);
			double novoSaldo, saldo;
			double valorSaque = Double.parseDouble(valor);
			if (linha.equals("")) {
				saldo = 0;
			} else {
				saldo = Double.parseDouble(linha.replace(",", "."));
			}
			if (saldo == 0 || saldo < (valorSaque+TARIFA_SAQUE)) {
				mensagem = "Saldo R$" + String.format("%.2f",saldo) + "- Valor insuficiente!";
			} else {
				novoSaldo = saldo - (valorSaque + TARIFA_SAQUE);  
				ManipuladorArquivos.editarArquivo(cpf, 2, fmt.format(novoSaldo));
				mensagem = "Saque realizado com sucesso!";
				String registroSaque = UtilsDateTime.pegarDataHora()+ " R$" + String.format("%.2f", Double.parseDouble(valor)) + "|";
				String registroExtrato = UtilsDateTime.pegarDataHora()+ " - R$" + String.format("%.2f", Double.parseDouble(valor)) + "|";
				registrarMovimentacao(cpf, registroSaque, 5);
				registrarMovimentacao(cpf, registroExtrato, 4);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mensagem;
	}
	
	public void depositar(String cpf, String valor) {
		try {
			String linha = ManipuladorArquivos.lerArquivo(cpf, 2);
			if (linha.equals("")) {
				ManipuladorArquivos.escreverArquivo(cpf, 2, valor);
			} else {
				double saldo = Double.parseDouble(linha.replace(",", "."));
				double novoSaldo = saldo + Double.parseDouble(valor);
				ManipuladorArquivos.editarArquivo(cpf, 2, fmt.format(novoSaldo));
			}
			String registroDeposito = UtilsDateTime.pegarDataHora()+ " R$" + String.format("%.2f", Double.parseDouble(valor)) + "|";
			String registroExtrato = UtilsDateTime.pegarDataHora()+ " + R$" + String.format("%.2f", Double.parseDouble(valor)) + "|";
			registrarMovimentacao(cpf, registroDeposito, 3);
			registrarMovimentacao(cpf, registroExtrato, 4);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void criarConta(String[] dados) {
		String dadosCliente = "";
		for(int i = 0; i < dados.length; i++) {
			dadosCliente = dadosCliente.trim() + dados[i] + ",";
		}
		try {
			ManipuladorArquivos.escreverArquivo(dados[1], 0, dadosCliente);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void editarConta(String[] dados) {
		int opcaoMenu = Integer.parseInt(dados[1].trim());
		String dadosCliente = "";
		String line = ManipuladorArquivos.lerArquivo(dados[0], 0);
		String[] info = line.split(",");
		info[opcaoMenu-1] = dados[2];
		for(int i = 0; i < info.length; i++) {
			dadosCliente = dadosCliente.trim() + info[i] + ",";
		}
		try {
			ManipuladorArquivos.editarArquivo(dados[0], 0, dadosCliente);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void adicionarDependentes(String[] dados) {
		String dadosCliente = "";
		String linha = ManipuladorArquivos.lerArquivo(dados[0], 1);
		if (!linha.equals("")) {
			dadosCliente = linha;
		}
		for(int i = 1; i < dados.length; i++) {
			dadosCliente = dadosCliente.trim() + dados[i] + ",";
		}
		dadosCliente = dadosCliente + "|";
		try {
			if (!linha.equals("")) {
				ManipuladorArquivos.editarArquivo(dados[0], 1, dadosCliente);
			} else {
				ManipuladorArquivos.escreverArquivo(dados[0], 1, dadosCliente);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String visualizarDados(String cpf) {
		String dados = "";
		String linha[] = ManipuladorArquivos.lerArquivo(cpf, 0).split(",");
		Cliente titular = new ClienteTitular();
		titular.setCpf(linha[1]);
		titular.setEmail(linha[3]);
		titular.setEndereco(linha[2]);
		titular.setNomeCliente(linha[0]);
		titular.setTelefone(linha[4]);
		dados = dados + titular.toString() + "\n\n";
		String dependentes[] = ManipuladorArquivos.lerArquivo(cpf, 1).split("\\|");
		Cliente dependente;
		for (String cliente : dependentes) {
			dependente = new ClienteAdicional();
			linha = cliente.split(",");
			dependente.setCpf(linha[1]);
			dependente.setEmail(linha[3]);
			dependente.setEndereco(linha[2]);
			dependente.setNomeCliente(linha[0]);
			dependente.setTelefone(linha[4]);
			dados = dados + dependente.toString();
		}
		return dados;
	}
	
	public String verExtrato(String cpf) {
		String extrato = "Extrato\n";
		String lancamentos[] = ManipuladorArquivos.lerArquivo(cpf, 4).split("\\|");
		if (lancamentos.length != 0 && pegarSaldo(cpf) >= 1){
			for(String lancamento : lancamentos) {
				extrato = extrato + lancamento + "\n";
			}
			extrato = extrato + "\n SALDO ATUAL R$ " + fmt.format(pegarSaldo(cpf));
			registrarContadorOperacoes(cpf, 6);
		} else if (pegarSaldo(cpf) >= 1) {
			extrato = "Saldo insuficiente para realizar operação!";
		} else {
			extrato = "Não foram encontrados lançamentos!";
		}
		return extrato;
	}
	
	private void registrarMovimentacao(String cpf, String registro, int numLinha) {
		String extrato = "";
		String linha = ManipuladorArquivos.lerArquivo(cpf, numLinha);
		try {
			if (linha.equals("")) {
				extrato = registro;
				ManipuladorArquivos.escreverArquivo(cpf, numLinha, extrato);
			} else {
				extrato = linha + registro;
				ManipuladorArquivos.editarArquivo(cpf, numLinha, extrato);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 6 - Extratos | 8 - Emprestimos */
	private void registrarContadorOperacoes(String cpf, int numeroLinha) {
		String linha = ManipuladorArquivos.lerArquivo(cpf, numeroLinha);
		int qtdOperacoesSolicitadas = 0;
		try {
			if (linha.equals("")) {
				qtdOperacoesSolicitadas = qtdOperacoesSolicitadas + 1;
				ManipuladorArquivos.escreverArquivo(cpf, numeroLinha, Integer.toString(qtdOperacoesSolicitadas));
			} else {
				qtdOperacoesSolicitadas = Integer.parseInt(linha) + 1;
				ManipuladorArquivos.editarArquivo(cpf, numeroLinha, Integer.toString(qtdOperacoesSolicitadas));
			}
			if (numeroLinha == 6) {
				double saldo = Double.parseDouble(ManipuladorArquivos.lerArquivo(cpf, 2));
				saldo = saldo - 1;
				ManipuladorArquivos.editarArquivo(cpf, 2, Double.toString(saldo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private double pegarSaldo(String cpf) {
		String linha = ManipuladorArquivos.lerArquivo(cpf, 2);
		double saldo = 0;
		if (!linha.equals("")) {
			saldo = Double.parseDouble(linha.replace(",", "."));
		}
		return saldo;
	}
	
	public String verificarValorEmprestimo(String cpf) {
		String emprestimo;
		if (pegarSaldo(cpf) == 0) {
			emprestimo = "Saldo insuficiente para realizar empréstimo";
		} else {
			emprestimo = "Empréstimo\n\n"
					+ "Valor mínimo disponível = R$"+ String.format("%.2f", pegarSaldo(cpf))+ "\n"
					+ "Valor máximo disponível = R$"+ String.format("%.2f", (pegarSaldo(cpf)*40))+ "\n\n"
					+ "Pagamento - Prazo Máximo = 3 anos (36 meses)";
		}
		return emprestimo;
	}
	
	public String verificarTotais(String cpf, int numeroLinha) {
		String totalRetiradas;
		if (numeroLinha == 3) {
			totalRetiradas = "Entradas\n";
		} else {
			totalRetiradas = "Retiradas\n";
		}
		float total = 0;
		String retiradas[] = ManipuladorArquivos.lerArquivo(cpf, numeroLinha).split("\\|");
		for(String retirada : retiradas) {
			totalRetiradas = totalRetiradas + retirada + "\n";
			String valor[] = retirada.split("\\$");
			total = total + Float.parseFloat(valor[1].replace(",", "."));
		}
		totalRetiradas = totalRetiradas + "\n TOTAL = R$" + fmt.format(total);
		return totalRetiradas;
	}
	
	public String contratarEmprestimo(String dados[]) {
		LocalDateTime now = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String linha = ManipuladorArquivos.lerArquivo(dados[0], 7);
		double valorEmprestimo = Double.parseDouble(dados[1].trim());
		int numeroParcelas = Integer.parseInt(dados[2].trim());
		double saldoAtual = pegarSaldo(dados[0].trim());
		if (linha.equals("")) {
			if (numeroParcelas >= 1 && numeroParcelas <= 36) {
				if (valorEmprestimo >= saldoAtual && valorEmprestimo <= (saldoAtual*40)) {
					double montante = (valorEmprestimo * Math.pow((1+0.05), numeroParcelas)) + TARIFA_EMPRESTIMO;
					float valorParcela = (float) (montante / numeroParcelas);
					String texto = fmt.format(montante).replace(",", ".") + ","+ numeroParcelas + ","
							+ "" + fmt.format(valorParcela).replace(",", ".") + ","
							+ "" + 0 + "," + now.plusMonths(numeroParcelas).format(formatter);
					try {
						ManipuladorArquivos.escreverArquivo(dados[0], 7, texto);
						depositar(dados[0], dados[1].trim());
						registrarContadorOperacoes(dados[0], 8);
					} catch (IOException e) {
						e.printStackTrace();
						return "Não foi possivel realizar sua operação";
					}
					return "Emprestimo contratado com sucesso! \n"
							+ numeroParcelas + " parcelas de " + "R$" + fmt.format(valorParcela) + "\n"
									+ "Realizar pagamento total até " + now.plusMonths(numeroParcelas).format(formatter);
				}
				return "Valor desejado não autorizado";
			} else {
				return "Número de parcelas não permitido permitido";
			}
		} else {
			return "Ops, encontramos um empréstimo já contratado!";
		}
	}

	public String verTarifas(String cpf) {
		try {
			String tarifas = "Tarifas\n\n"
					+ "Saque - R$" + fmt.format(TARIFA_SAQUE) + "\n"
					+ "Extrato - R$" + fmt.format(TARIFA_EXTRATO) + "\n"
					+ "Emprestimo - R$" + fmt.format(TARIFA_EMPRESTIMO) + "\n\n"
					+ "Resumo:\n\n";
			double total = 0;
			String linhaExtrato = ManipuladorArquivos.lerArquivo(cpf, 6);
			String linhaSaques = ManipuladorArquivos.lerArquivo(cpf, 5);
			String linhaEmprestimo = ManipuladorArquivos.lerArquivo(cpf, 8);
			if (!linhaSaques.equals("")) {
				String ocorrencias[] = linhaSaques.split("\\|");
				int numeroOcorrencias = ocorrencias.length;
				tarifas = tarifas + "Qtd. Saques realizados: " + numeroOcorrencias + "\n";
				total = total + (numeroOcorrencias * TARIFA_SAQUE);
			}
			if (!linhaExtrato.equals("")) {
				tarifas = tarifas + "Qtd. Extratos Solicitados: " + linhaExtrato + "\n";
				total = total + (Integer.parseInt(linhaExtrato)*TARIFA_EXTRATO);
			}
			if (!linhaEmprestimo.equals("")) {
				tarifas = tarifas + "Qtd. Emprestimos contratados: " + linhaEmprestimo + "\n";
				total = total + (Integer.parseInt(linhaEmprestimo)*TARIFA_EMPRESTIMO);
			}
			tarifas = tarifas + "\n\n TOTAL GASTO EM SERVIÇOS ATÉ O MOMENTO - R$"+ fmt.format(total);
			return tarifas;
		} catch (Exception e) {
			e.printStackTrace();
			return "Não foi possível processar sua solicitação";
		}
		
	}
	
	public String pagarEmprestimo(String dados[]) {
		try {
			String cpf = dados[0].trim();
			int numeroParcelas = Integer.parseInt(dados[1].trim());
			String linha = ManipuladorArquivos.lerArquivo(cpf, 7);
			if (!linha.equals("")) {
				String emprestimo[] = linha.split(",");
				double valorAPagar = numeroParcelas * Double.parseDouble(emprestimo[2]);
				if (pegarSaldo(cpf) >= valorAPagar) {
					ManipuladorArquivos.editarArquivo(cpf, 2, fmt.format((pegarSaldo(cpf) - valorAPagar)));
					String registro = emprestimo[0] + "," + emprestimo[1] + "," + emprestimo[2] + ","
							+ "" + (numeroParcelas+Integer.parseInt(emprestimo[3])) + "," + emprestimo[4];
					ManipuladorArquivos.editarArquivo(cpf, 7, registro);
					return "Pagamento realizado com sucesso!";
				} else {
					return "Saldo insuficiente para realizar o pagamento!";
				}
			} else {
				return "Não encontrei os dados do emprestimo!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao processar sua solicitação!";
		}
	}

	public String verificarSaldoDevedorEmprestimo(String cpf) {
		try {
			String linha = ManipuladorArquivos.lerArquivo(cpf, 7);
			if (!linha.equals("")) {
				String emprestimo[] = linha.split(",");
				double valorEmprestado = Double.parseDouble(emprestimo[0]);
				double valorParcela = Double.parseDouble(emprestimo[2]);
				int quantidadeParcelasPagas = Integer.parseInt(emprestimo[3]);
				double valorPagoAteMomento = quantidadeParcelasPagas * valorParcela;
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				LocalDate date = LocalDate.parse(emprestimo[4],formatter);
				long diasAteDataLimite = LocalDate.now().until(date, ChronoUnit.DAYS);
				String saldoDevedor = "Empréstimo - Saldo Devedor\n"
						+ "Valor Emprestado : R$" + fmt.format(valorEmprestado) + "\n"
						+ "Qtd. Parcelas Pagas : " + quantidadeParcelasPagas + "\n"
						+ "Valor pago até o momento : R$" + fmt.format(quantidadeParcelasPagas*valorParcela) + "\n"
						+ "Valor restante : R$" + fmt.format((valorEmprestado - valorPagoAteMomento)) + "\n"
						+ "Dias restante para pagamento total : " + diasAteDataLimite + " dias  (" + emprestimo[4] + ")";
				return saldoDevedor;
			} else {
				return "Não encontrei os dados do emprestimo!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao processar sua solicitação";
		}
		
	}

}
