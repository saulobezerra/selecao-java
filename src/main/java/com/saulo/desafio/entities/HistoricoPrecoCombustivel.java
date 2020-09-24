package com.saulo.desafio.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class HistoricoPrecoCombustivel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date dataDaColeta;
	
	@ManyToOne
	@JoinColumn(name = "localidade_id")
	private Localidade localidade;
	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;
	@ManyToOne
	@JoinColumn(name = "revendedora_id")
	private Revendedora revendedora;
	
	public HistoricoPrecoCombustivel() {
		
	}

	public HistoricoPrecoCombustivel(Long id, Date dataDaColeta, Localidade localidade, Produto produto, Revendedora revendedora) {
		super();
		this.id = id;
		this.dataDaColeta = dataDaColeta;
		this.localidade = localidade;
		this.produto = produto;
		this.revendedora = revendedora;
	}

	public Long getId() {
		return id;
	}	
	
	public Date getDataDaColeta() {
		return dataDaColeta;
	}

	public void setDataDaColeta(Date dataDaColeta) {
		this.dataDaColeta = dataDaColeta;
	}

	public Localidade getLocalidade() {
		return localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Revendedora getRevendedora() {
		return revendedora;
	}

	public void setRevendedora(Revendedora revendedora) {
		this.revendedora = revendedora;
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
		HistoricoPrecoCombustivel other = (HistoricoPrecoCombustivel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
