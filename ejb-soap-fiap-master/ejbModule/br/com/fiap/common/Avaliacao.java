package br.com.fiap.common;

import java.util.List;

public interface Avaliacao {
	   List obterQuestoes(int codigoAvaliacao);
	   void removerEJB();
}
