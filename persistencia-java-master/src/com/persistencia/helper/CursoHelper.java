package com.persistencia.helper;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.persistencia.model.Curso;

public class CursoHelper implements Helper<Curso> {
	
	private EntityManager em;
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	public CursoHelper(EntityManager em) {
		super();
		this.em = em;
	}
	
	public String salvar(Curso curso) {
		try {
			em.getTransaction().begin();
			em.persist(curso);
			em.getTransaction().commit();
			return "Curso cadastrado com sucesso \n";
		} catch (Exception e) {
			logger.severe("Erro ao cadastrar curso, verifique os dados de entrada");
			return "Não foi possível concluir a ação";
		} finally {
			em.close();
		}
	}
	
	public String update(Curso curso) {
		try {
			em.getTransaction().begin();
			em.merge(curso);
			em.getTransaction().commit();
			return "Alteração de dados realizada com sucesso \n";
		} catch (Exception e) {
			logger.severe("Erro ao editar dados do curso, verifique os dados de entrada");
			return "Não foi possóvel concluir a ação";
		} finally {
			em.close();
		}
	}
	
	public List<Curso> findAll(){
		Session session = (Session) em.getDelegate();
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Curso> criteriaQuery = criteriaBuilder.createQuery(Curso.class);
		Root<Curso> root = criteriaQuery.from(Curso.class);
		criteriaQuery.select(root);
		Query<Curso> query = session.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	public Curso find(String nomeColuna, String parametroConsulta) {
		Session session = (Session) em.getDelegate(); 
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Curso> query = builder.createQuery(Curso.class);
		Root<Curso> root = query.from(Curso.class);
        query.select(root).where(builder.equal(root.get(nomeColuna), parametroConsulta));
        Query<Curso> q = session.createQuery(query);
        return q.getSingleResult();  
	}
	

}
