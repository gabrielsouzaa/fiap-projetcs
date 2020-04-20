package com.gabsbank.utils;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.gabsbank.model.Conta;
import com.gabsbank.model.Usuario;

public class GabsBankBot extends TelegramLongPollingBot {
	
	private SendMessage mensagem = new SendMessage();
	private List<Usuario> listaUsuarios = new ArrayList();

	@Override
	public String getBotUsername() {
		return "gabsbank_bot";
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.getMessage().getText().substring(0,1).equals("/")) {
			salvarUltimoComando(update.getMessage().getChatId(), update.getMessage().getText());
			controlaComandos(update);
		} else {
			controlaInputs(update);
		}
	}

	@Override
	public String getBotToken() {
		return "660468520:AAEQ275GZUvw1nEE1j1sfglabQWLrlQ0cmA";
	}
	
	private void controlaComandos(Update update) {
		String mensagemRecebida = update.getMessage().getText();
		switch (mensagemRecebida) {
		case "/start":
			mensagem.setText("Olá "+update.getMessage().getFrom().getFirstName() + "\n"
					+ "Seja bem vindo ao Gabs Bank - Seu banco virtual \n\n"
					+ "Menu:\n"
					+ "/novaconta : Crie uma nova conta no banco \n"
					+ "/editarconta : Altere os dados da sua conta \n"
					);
			break;
		case "/novaconta":
			mensagem.setText("CRIAR CONTA \n"
					+ "Ok vamos criar uma conta, preciso das seguintes informações \n"
					+ "*Obs: Por favor use esse formato de mensagem. \n\n"
					+ "<Nome Completo>, "
					+ "<CPF>, "
					+ "<Endereço>, "
					+ "<Email>, "
					+ "<Telefone>"
					);
			break;
		case "/editarconta":
			mensagem.setText("EDITAR DADOS DO TITULAR DA CONTA \n"
					+ "Ok vamos editar sua conta!\n"
					+ "*Obs: Por favor use esse formato de mensagem. \n\n"
					+ "MENU EDIÇÃO: \n"
					+ "1-Nome Completo\n"
					+ "2-CPF\n"
					+ "3-Endereço\n"
					+ "4-Email\n"
					+ "5-Telefone\n\n"
					+ ""
					+ "FORMATO:\n <cpf>,<opcao do menu>,<texto>\n"
					+ "EXEMPLO - Edição Telefone:\n 12345678910,5,991231121"
					);
			break;
		case "/adddependentes":
			mensagem.setText("INCLUSÃO DEPENDENTES \n"
					+ "Ok vamos incluir um dependente!\n"
					+ "*Obs: Por favor use esse formato de mensagem. \n\n"
					+ "<CPF da conta titular>, "
					+ "<Nome Completo>, "
					+ "<CPF>, "
					+ "<Endereço>, "
					+ "<Email>, "
					+ "<Telefone>"
					);
			break;
		case "/visualizardados":
			mensagem.setText("VISUALIZAR DADOS \n"
					+ "Visualização do dados!\n "
					+ "<CPF do Titular> ");
			break;
		case "/depositar":
			mensagem.setText("DEPÓSITO \n"
					+ "Depositar um valor na conta!\n"
					+ "*Obs: Por favor use esse formato de mensagem. \n\n"
					+ "<CPF do Titular>,  "
					+ "<Valor a depositar> \n\n"
					+ "EXEMPLO 1 - Deposito R$100,00 : 12345678910, 100\n"
					+ "EXEMPLO 2 - Deposito R$50,12 : 12345678910, 50.12");
			break;
		case "/sacar":
			mensagem.setText("SAQUE \n"
					+ "Sacar um valor da conta!\n"
					+ "*Obs: Por favor use esse formato de mensagem. \n\n"
					+ "<CPF do Titular>,  "
					+ "<Valor a sacar> \n\n"
					+ "EXEMPLO 1 - Saque R$100,00 : 12345678910, 100\n"
					+ "EXEMPLO 2 - Saque R$50,12 : 12345678910, 50.12");
			break;
		case "/extrato":
			mensagem.setText("EXTRATO \n"
					+ "Visualização do extrato!\n "
					+ "<CPF do Titular> ");
			break;
		case "/emprestimo":
			mensagem.setText("EMPRESTIMO \n"
					+ "Verificar valor disponível para empréstimo!\n\n "
					+ "<CPF do Titular>");
			break;
		case "/contrataremprestimo":
			mensagem.setText("EMPRESTIMO - Contratação \n"
					+ "Contratação de empréstimo!\n\n "
					+ "<CPF do Titular>, <valor do emprestimo>, <nº de parcelas>\n"
					+ "EXEMPLO - Emprestimo R$1000,00 : 37081918212, 1000, 10");
			break;
		case "/totalretiradas":
			mensagem.setText("TOTAL RETIRADAS \n"
					+ "Visualização do extrato!\n "
					+ "<CPF do Titular> ");
			break;
		case "/totalentradas":
			mensagem.setText("TOTAL ENTRADAS \n"
					+ "Visualização do extrato!\n "
					+ "<CPF do Titular> ");
			break;
		case "/saldodevedoremprestimo":
			mensagem.setText("EMPRESTIMO - Saldo devedor\n"
					+ "Visualização do saldo devedor do emprestimo!\n "
					+ "<CPF do Titular>");
			break;
		case "/tarifas":
			mensagem.setText("TARIFAS \n"
					+ "Visualização das tarifas bancárias!\n "
					+ "<CPF do Titular>");
			break;
		case "/pagaremprestimo":
			mensagem.setText("Emprestimo - Realizar Pagamento \n"
					+ "Pagamento de parcelas do emprestimo!\n "
					+ "<CPF do Titular>, <numero de parcelas a pagar>");
			break;
		default:
			enviarMensagemErro(update);
			break;
		}
		enviarMensagem(update);
	}
	
	public void controlaInputs(Update update){
		Conta conta = new Conta();
		String mensagemRecebida = update.getMessage().getText();
		try {
			switch (pegarUltimoComando(update.getMessage().getChatId())) {
			case "/novaconta":
				String mensagemRetorno = "Conta já existente";
				String[] dadosNovaConta = mensagemRecebida.split(",");
				File file = new File("contas/"+dadosNovaConta[1].trim()+".txt");
				if (!file.exists()) {
					conta.criarConta(dadosNovaConta);
					mensagem.setText("Conta criada com sucesso! =)");
				} else {
					mensagem.setText(mensagemRetorno);
				}
				break;
			case "/editarconta":
				String[] dadosEditados = mensagemRecebida.split(",");
				conta.editarConta(dadosEditados);
				mensagem.setText("Conta alterada com sucesso! =)");
				break;
			case "/adddependentes":
				String[] dadosDependente = mensagemRecebida.split(",");
				conta.adicionarDependentes(dadosDependente);
				mensagem.setText("Legal, adicionamos um dependente! =)");
				break;
			case "/visualizardados":
				mensagem.setText(conta.visualizarDados(mensagemRecebida));
				break;
			case "/depositar":
				String[] deposito = mensagemRecebida.split(",");
				conta.depositar(deposito[0], deposito[1]);
				mensagem.setText("Depósito realizado com sucesso!");
				break;
			case "/sacar":
				String[] saque = mensagemRecebida.split(",");
				mensagem.setText(conta.sacar(saque[0], saque[1]));
				break;
			case "/extrato":
				mensagem.setText(conta.verExtrato(mensagemRecebida));
				break;
			case "/emprestimo":
				mensagem.setText(conta.verificarValorEmprestimo(mensagemRecebida));
				break;
			case "/contrataremprestimo":
				mensagem.setText(conta.contratarEmprestimo(mensagemRecebida.split(",")));
				break;
			case "/totalretiradas":
				mensagem.setText(conta.verificarTotais(mensagemRecebida, 5));
				break;
			case "/totalentradas":
				mensagem.setText(conta.verificarTotais(mensagemRecebida, 3));
				break;
			case "/saldodevedoremprestimo":
				mensagem.setText(conta.verificarSaldoDevedorEmprestimo(mensagemRecebida));
				break;
			case "/tarifas":
				mensagem.setText(conta.verTarifas(mensagemRecebida));
				break;
			case "/pagaremprestimo":
				mensagem.setText(conta.pagarEmprestimo(mensagemRecebida.split(",")));
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			mensagem.setText("Erro ao processar solicitação");
		}
		enviarMensagem(update);
	}
	
	private void enviarMensagemErro(Update update) {
		mensagem.setText("Desculpa, não entendi!");
		enviarMensagem(update);
	}
	
	private void enviarMensagem(Update update) {
		mensagem.setChatId(update.getMessage().getChatId());
		try {
			execute(mensagem);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	private void salvarUltimoComando(Long chatId, String comando) {
		Usuario usuarioNovo;
		if (listaUsuarios.size() == 0) {
			usuarioNovo = new Usuario();
			usuarioNovo.setChatId(chatId);
			usuarioNovo.setUltimoComando(comando);
			listaUsuarios.add(usuarioNovo);
		} else {
			for(Usuario usuario : listaUsuarios) {
				if (usuario.getChatId().equals(chatId)) {
					usuario.setUltimoComando(comando);
				} else {
					usuarioNovo = new Usuario();
					usuarioNovo.setChatId(chatId);
					usuarioNovo.setUltimoComando(comando);
					listaUsuarios.add(usuarioNovo);
				}
			}
		}
	}
	
	private String pegarUltimoComando(Long chatId) {
		String comando = "";
		for (Usuario usuario : listaUsuarios) {
			if (usuario.getChatId().equals(chatId)) {
				comando = usuario.getUltimoComando();
			}
		}
		return comando;
	}

}
