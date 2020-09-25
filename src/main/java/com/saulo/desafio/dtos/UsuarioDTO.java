package com.saulo.desafio.dtos;

import java.io.Serializable;

import com.saulo.desafio.entities.Usuario;

public class UsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String sobrenome;
	private String email;
	
	public UsuarioDTO() {
		
	}
	
	public UsuarioDTO(Usuario user) {
		super();
		this.id = user.getId();
		this.nome = user.getNome();
		this.sobrenome = user.getSobrenome();
		this.email = user.getEmail();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
