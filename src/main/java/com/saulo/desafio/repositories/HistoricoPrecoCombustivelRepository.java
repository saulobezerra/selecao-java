package com.saulo.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saulo.desafio.entities.HistoricoPrecoCombustivel;

public interface HistoricoPrecoCombustivelRepository extends JpaRepository<HistoricoPrecoCombustivel, Long> {
	
}
