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
@Table(name="aluno", schema="persistencia-java")
public class Aluno {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;

	@Column(name="cpf", nullable = false)
	private String cpf;

	@Column(name="nome", nullable = false)
	private String nome;

	@Column(name="email", nullable = false)
	private String email;

	@Column(name="telefone", nullable = false)
	private String telefone;

	@Column(name="data_nascimento", nullable = false)
	private String dataNascimento;

	@ManyToMany
	@JoinTable(name="matricula", 
	joinColumns={ @JoinColumn(name="aluno_id")}, 
	inverseJoinColumns={@JoinColumn(name="curso_id")})
	private List<Curso> cursos;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	@Override
	public String toString() {
		return "Aluno => CPF:" + cpf + ", Nome Completo:" + nome + ", E-mail=:" + email + ", Telefone:" + telefone
				+ ", Data Nascimento:" + dataNascimento + "\n";
	}	


}
