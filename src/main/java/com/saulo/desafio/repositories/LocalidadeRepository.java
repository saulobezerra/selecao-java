package com.saulo.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saulo.desafio.entities.Localidade;

public interface LocalidadeRepository extends JpaRepository<Localidade, Long> {
	
	
}
