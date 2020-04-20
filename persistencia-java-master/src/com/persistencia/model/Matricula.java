package com.persistencia.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="matricula", schema="persistencia-java")
public class Matricula {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;
	
	@ManyToOne
    @JoinColumn(name="curso_id")
	private Curso curso;
	
	@ManyToOne
    @JoinColumn(name="aluno_id")
	private Aluno aluno;
	
	@Column(name="data_matricula", nullable = false)
	private Timestamp dataMatricula;
	
	@Column(name="nota_aluno", nullable = false)
	private int notaAluno;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Timestamp getDataMatricula() {
		return dataMatricula;
	}

	public void setDataMatricula(Timestamp dataMatricula) {
		this.dataMatricula = dataMatricula;
	}

	public int getNotaAluno() {
		return notaAluno;
	}

	public void setNotaAluno(int notaAluno) {
		this.notaAluno = notaAluno;
	}

}
