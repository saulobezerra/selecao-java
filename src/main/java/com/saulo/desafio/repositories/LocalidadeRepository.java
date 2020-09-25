package com.saulo.desafio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.saulo.desafio.entities.Localidade;

public interface LocalidadeRepository extends JpaRepository<Localidade, Long> {
	
	Localidade findByMunicipio(String nomeMunicipio);
	
	@Query("select municipio from Localidade")
	List<String> findAllMunicipios();
	
}
