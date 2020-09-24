package com.saulo.desafio.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Produto implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private Double valorDeCompra;
	private Double valorDeVenda;
	
	@JsonIgnore
	@OneToMany(mappedBy = "produto")
	private Set<HistoricoPrecoCombustivel> historicoDePrecos = new HashSet<>();
	
	public Produto() {
		
	}

	public Produto(Long id, String nome,  Double valorDeCompra, Double valorDeVenda) {
		super();
		this.id = id;
		this.nome = nome;
		this.valorDeCompra = valorDeCompra;
		this.valorDeVenda = valorDeVenda;
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

	public Double getValorDeCompra() {
		return valorDeCompra;
	}

	public void setValorDeCompra(Double valorDeCompra) {
		this.valorDeCompra = valorDeCompra;
	}

	public Double getValorDeVenda() {
		return valorDeVenda;
	}

	public void setValorDeVenda(Double valorVenda) {
		this.valorDeVenda = valorVenda;
	}

	public Set<HistoricoPrecoCombustivel> getHistoricoDePrecos() {
		return historicoDePrecos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
	
}
