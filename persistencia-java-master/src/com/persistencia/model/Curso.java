package com.persistencia.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="curso", schema="persistencia-java")
public class Curso {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;
	
	@Column(name="nome", nullable = false)
	private String nome;
	
	@Column(name="carga_horaria", nullable = false)
	private int cargaHoraria;
	
	@Column(name="preco", nullable = false)
	private Double preco;
	
	@Column(name="instrutor", nullable = false)
	private String instrutor;
	
	@ManyToMany
	@JoinTable(name="matricula", 
	joinColumns={ @JoinColumn(name="curso_id")}, 
	inverseJoinColumns={@JoinColumn(name="aluno_id")})
	private List<Aluno> alunos;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getInstrutor() {
		return instrutor;
	}

	public void setInstrutor(String instrutor) {
		this.instrutor = instrutor;
	}
	
	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	@Override
	public String toString() {
		return "Curso => ID:" + id + ", Nome:" + nome + ", Carga Horaria:" + cargaHoraria + ", Preço:" + preco
				+ ", Instrutor:" + instrutor + " \n";
	}
	
	
}
