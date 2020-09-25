package com.saulo.desafio.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.saulo.desafio.entities.HistoricoPrecoCombustivel;

public interface HistoricoPrecoCombustivelRepository extends JpaRepository<HistoricoPrecoCombustivel, Long> {
	
	@Query("select avg(P.valorDeVenda) " + 
			"from Produto as P " + 
			"join HistoricoPrecoCombustivel as HPC on P.id = HPC.produto.id " + 
			"join Localidade as L on L.id = HPC.localidade.id " +
			"where L.municipio = (?1)")
	Double mediaDePrecoBaseadoNoMunicipio(String nomeMunicipio);

	@Transactional(readOnly = true)
	@Query("select HPC " + 
			"from HistoricoPrecoCombustivel as HPC " + 
			"join Localidade as L on L.id = HPC.localidade.id " +
			"order by L.regiao")
	Page<HistoricoPrecoCombustivel> todasInformacoesPorRegiao(PageRequest pageRequest);
	
	@Transactional(readOnly = true)
	@Query("select HPC " + 
			"from HistoricoPrecoCombustivel as HPC " + 
			"join Revendedora as R on R.id = HPC.revendedora.id " +
			"order by R.nome")
	Page<HistoricoPrecoCombustivel> dadosAgrupadosPorDistribuidora (PageRequest pageRequest);
	
	@Query("select hpc from HistoricoPrecoCombustivel hpc " + 
			"order by hpc.dataDaColeta")
	Page<HistoricoPrecoCombustivel> dadosAgrupadosPorDataColeta (PageRequest pageRequest);

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
