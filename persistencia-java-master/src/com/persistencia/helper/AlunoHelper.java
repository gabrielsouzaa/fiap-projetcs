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

public class AlunoHelper implements Helper<Aluno>{

	private EntityManager em;
	Logger logger = Logger.getLogger(this.getClass().getName());

	public AlunoHelper(EntityManager em) {
		super();
		this.em = em;
	}
	
	public Aluno salvar(Aluno aluno) {
		try {
			em.getTransaction().begin();
			em.persist(aluno);
			em.getTransaction().commit();
			System.out.println("Aluno cadastrado com sucesso \n");
			return aluno;
		} catch (Exception e) {
			logger.severe("Erro ao cadastrar aluno, verifique os dados de entrada");
			logger.info("Não foi possível concluir a ação");
			return null;
		} finally {
			em.close();
		}
	}
	
	public String update(Aluno aluno) {
		try {
			em.getTransaction().begin();
			em.merge(aluno);
			em.getTransaction().commit();
			return "Alteração de dados realizada com sucesso \n";
		} catch (Exception e) {
			logger.severe("Erro ao editar dados do aluno, verifique os dados de entrada");
			return "Não foi possível concluir a ação";
		} finally {
			em.close();
		}
	}
	
	public List<Aluno> findAll(){
		Session session = (Session) em.getDelegate();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Aluno> criteriaQuery = criteriaBuilder.createQuery(Aluno.class);
		Root<Aluno> root = criteriaQuery.from(Aluno.class);
		criteriaQuery.select(root);
		Query<Aluno> query = session.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	public Aluno find(String nomeColuna, String parametroConsulta) {
		Session session = (Session) em.getDelegate(); 
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Aluno> query = builder.createQuery(Aluno.class);
		Root<Aluno> root = query.from(Aluno.class);
        query.select(root).where(builder.equal(root.get(nomeColuna), parametroConsulta));
        Query<Aluno> q = session.createQuery(query);
        return q.getSingleResult();  
	}
}



