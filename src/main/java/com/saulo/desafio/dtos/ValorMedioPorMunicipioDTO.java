package com.saulo.desafio.dtos;

public class ValorMedioPorMunicipioDTO {

	private String municipio;
	private Double valorDeVenda;
	private Double valorDeCompra;
	
	public ValorMedioPorMunicipioDTO() {
		
	}

	public ValorMedioPorMunicipioDTO(String municipio, Double valorDeVenda, Double valorDeCompra) {
		super();
		this.municipio = municipio;
		this.valorDeVenda = valorDeVenda;
		this.valorDeCompra = valorDeCompra;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Double getValorDeVenda() {
		return valorDeVenda;
	}

	public void setValorDeVenda(Double valorDeVenda) {
		this.valorDeVenda = valorDeVenda;
	}

	public Double getValorDeCompra() {
		return valorDeCompra;
	}

	public void setValorDeCompra(Double valorDeCompra) {
		this.valorDeCompra = valorDeCompra;
	}
	
}
