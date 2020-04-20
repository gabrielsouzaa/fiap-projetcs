package br.com.fiap.bean;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;

import br.com.fiap.common.Avaliacao;
import br.com.fiap.common.Questao;
import br.com.fiap.common.Resposta;

@Stateless
@WebService
@Remote
public class QuestaoBean implements Avaliacao {

	@PersistenceContext(unitName = "jdbc/fiap")
	private EntityManager em;

	private List<Questao> questoes = new ArrayList<Questao>();
	private static final long serialVersionUID = 1L;

	public QuestaoBean() throws RemoteException {
		super();
	}

	@Override
	@WebMethod
	public List obterQuestoes(@WebParam(name = "codigoAvaliacao") int codigoAvaliacao) {
		List<String> questoes = new ArrayList();
		Query query = em.createQuery("SELECT q FROM Questao q WHERE codigoavaliacao = :codigo", Questao.class);
		query.setParameter("codigo", codigoAvaliacao);
		List<Questao> listaQuestoes = query.getResultList();

		for (Questao questao : listaQuestoes) {
			String saida = "";
			saida += questao.getId() + " - " + questao.getDescricao() + "\n";
			List<Resposta> respostas = (List<Resposta>) questao.getRespostas();
			int cont = 1;
			for (Resposta resposta : respostas) {
				saida += cont++ + " - " + resposta.getDescricao() + "\n";
			}
			questoes.add(saida);
		}

		return questoes;

	}

	public void removerEJB() {
		// TODO Auto-generated method stub
	}

}
