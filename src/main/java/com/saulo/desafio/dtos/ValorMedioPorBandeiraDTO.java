package com.saulo.desafio.dtos;

public class ValorMedioPorBandeiraDTO {
	
	private String bandeira;
	private Double valorDeVenda;
	private Double valorDeCompra;
	
	public ValorMedioPorBandeiraDTO() {
		
	}

	public ValorMedioPorBandeiraDTO(String bandeira, Double valorDeVenda, Double valorDeCompra) {
		super();
		this.bandeira = bandeira;
		this.valorDeVenda = valorDeVenda;
		this.valorDeCompra = valorDeCompra;
	}

	public String getBandeira() {
		return bandeira;
	}

	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
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
