package com.saulo.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saulo.desafio.entities.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	Produto findByNome(String nome);
}
