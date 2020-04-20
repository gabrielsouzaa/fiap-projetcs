package com.persistencia.helper;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.persistencia.model.Aluno;
import com.persistencia.model.Curso;
import com.persistencia.model.Matricula;

public class MatriculaHelper implements Helper<Matricula>{
	
	private EntityManager em;
	Logger logger = Logger.getLogger(this.getClass().getName());
	AlunoHelper alunoHelper;
	
	public MatriculaHelper(EntityManager em) {
		super();
		this.em = em;
	}
	
	public String salvar(Matricula matricula) {
		try {
			em.getTransaction().begin();
			em.persist(matricula);
			em.getTransaction().commit();
			return "Matricula realizada com sucesso -- ID Gerado = " + matricula.getId()+ "\n";
		} catch (Exception e) {
			logger.severe("Erro ao realizar matricula, verifique os dados de entrada");
			return "Não foi possível concluir a ação";
		} finally {
			em.close();
		}
	}
	
	public Matricula find(String nomeColuna, String parametroConsulta) {
		Session session = (Session) em.getDelegate(); 
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Matricula> query = builder.createQuery(Matricula.class);
		Root<Matricula> root = query.from(Matricula.class);
        query.select(root).where(builder.equal(root.get(nomeColuna), parametroConsulta));
        Query<Matricula> q = session.createQuery(query);
        return q.getSingleResult();  
	}
	
	public List<Matricula> listarMatriculasPorAluno(Aluno aluno) {
		Session session = (Session) em.getDelegate(); 
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Matricula> query = builder.createQuery(Matricula.class);
		Root<Matricula> root = query.from(Matricula.class);
        query.select(root).where(builder.equal(root.get("aluno"), aluno));
        Query<Matricula> q = session.createQuery(query);
        return q.getResultList();
	}
	
	public String update(Matricula matricula) {
		try {
			em.getTransaction().begin();
			em.merge(matricula);
			em.getTransaction().commit();
			return "Atualização de dados realizada com sucesso \n";
		} catch (Exception e) {
			logger.severe("Erro ao atualizar dados da matricula, verifique os dados de entrada");
			return "Não foi possível concluir a ação";
		} finally {
			em.close();
		}
	}

	@Override
	public List<Matricula> findAll() {
		return null;
	}
	

}
