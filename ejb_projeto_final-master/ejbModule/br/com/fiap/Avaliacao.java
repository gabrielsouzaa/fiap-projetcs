package br.com.fiap;

import java.util.List;

public interface Avaliacao {

   public List obterQuestoes(int codigoAvaliacao);
   public void removerEJB();
}
