package com.saulo.desafio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.saulo.desafio.entities.HistoricoPrecoCombustivel;

public interface HistoricoPrecoCombustivelRepository extends JpaRepository<HistoricoPrecoCombustivel, Long> {
	
	@Query("select avg(P.valorDeVenda) " + 
			"from Produto as P " + 
			"join HistoricoPrecoCombustivel as HPC on P.id = HPC.produto.id " + 
			"join Localidade as L on L.id = HPC.localidade.id " +
			"where L.municipio = (?1)")
	Double mediaDePrecoBaseadoNoMunicipio(String nomeMunicipio);
//	
//	HistoricoPrecoCombustivel todasInformacoesPorRegiao ();
//	
//	HistoricoPrecoCombustivel dadosAgrupadosPorDistribuidora ();
//	
//	HistoricoPrecoCombustivel dadosAgrupadosPorDataColeta () ;

	@Query("select L.municipio, avg(P.valorDeVenda), avg(P.valorDeCompra) " + 
			"from Produto as P " + 
			"join HistoricoPrecoCombustivel as HPC on P.id = HPC.produto.id " + 
			"join Localidade as L on L.id = HPC.localidade.id " +
			"group by L.municipio")
	List<String> mediaValorCompraEVendaPorMunicipio() ;

	@Query("select R.bandeira, avg(P.valorDeVenda), avg(P.valorDeCompra) " + 
			"from Produto as P " + 
			"join HistoricoPrecoCombustivel as HPC on P.id = HPC.produto.id " + 
			"join Revendedora as R on R.id = HPC.revendedora.id " +
			"group by R.bandeira")
	List<String> mediaValorCompraEVendaPorBandeira() ;
}
