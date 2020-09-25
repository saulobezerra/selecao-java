package com.saulo.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saulo.desafio.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByEmail(String email);
	
}
