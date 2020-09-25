package com.saulo.desafio.config;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.saulo.desafio.entities.HistoricoPrecoCombustivel;
import com.saulo.desafio.entities.Localidade;
import com.saulo.desafio.entities.Produto;
import com.saulo.desafio.entities.Revendedora;
import com.saulo.desafio.repositories.HistoricoPrecoCombustivelRepository;
import com.saulo.desafio.repositories.LocalidadeRepository;
import com.saulo.desafio.repositories.ProdutoRepository;
import com.saulo.desafio.repositories.RevendedoraRepository;

@Configuration
@Profile("test")
public class TesteConfig implements CommandLineRunner {
	
	@Autowired
	private ProdutoRepository prodRepositorio;
	
	@Autowired
	private LocalidadeRepository localRepositorio;
	
	@Autowired
	private RevendedoraRepository revRepositorio;
	
	@Autowired
	private HistoricoPrecoCombustivelRepository hpcRepositorio;
	
	@Override
	public void run(String... args) throws Exception {
		
		Localidade local1 = new Localidade(null, "NE", "PB", "JOAO PESSOA");
		Localidade local2 = new Localidade(null, "se", "sp", "sao paulo");
		Localidade local3 = new Localidade(null, "N", "AM", "MANAUS");
		localRepositorio.saveAll(Arrays.asList(local1, local2, local3));
		
		Produto p1 = new Produto(null, "ALCOOL", 3.20, 3.50);
		Produto p2 = new Produto(null, "GASOLINA", 3.80, 4.30);
		Produto p3 = new Produto(null, "GNV", 2.20, 2.50);
		prodRepositorio.saveAll(Arrays.asList(p1,p2,p3));
		
		Revendedora rev1 = new Revendedora(null, "REVENDEDORA 1", "012345678901234", "RAIZEN");
		Revendedora rev2 = new Revendedora(null, "REVENDEDORA 2", "012345678901235", "PETROBRAS");
		Revendedora rev3 = new Revendedora(null, "REVENDEDORA 3", "012345678901236", "BRANCA");
		revRepositorio.saveAll(Arrays.asList(rev1, rev2, rev3));
		
		HistoricoPrecoCombustivel hpc1 = new HistoricoPrecoCombustivel(null, new Date(), local1, p1, rev1);
		HistoricoPrecoCombustivel hpc2 = new HistoricoPrecoCombustivel(null, new Date(), local2, p3, rev3);
		HistoricoPrecoCombustivel hpc3 = new HistoricoPrecoCombustivel(null, new Date(), local3, p2, rev2);
		hpcRepositorio.saveAll(Arrays.asList(hpc1, hpc2, hpc3));
		
	}

}
