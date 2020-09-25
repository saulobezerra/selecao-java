package com.saulo.desafio.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.saulo.desafio.entities.Usuario;
import com.saulo.desafio.repositories.UsuarioRepository;
import com.saulo.desafio.services.exceptions.ResourceDataConflit;
import com.saulo.desafio.services.exceptions.ResourceNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private UsuarioRepository repository;
	
	public List<Usuario> findAll() {
		return repository.findAll();
	}
	
	public Usuario insert(Usuario obj) {		
		
		if(repository.findByEmail(obj.getEmail()) != null)
			throw new ResourceDataConflit("E-mail " + obj.getEmail() + " já cadastrado");
		
		if(obj.getSenha() == null || "".equals(obj.getSenha()))
			throw new ResourceNotFoundException("Senha não informada.");
		
		obj.setSenha(pe.encode(obj.getSenha()));
		return repository.save(obj);
	}

	public Usuario findById(Long id) {
		Optional<Usuario> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Id " + id));
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Usuario update(Long id, Usuario obj) {
		Usuario usuario = repository.getOne(id);
		udateData(usuario, obj);
		return repository.save(usuario);
	}
	
	private void udateData(Usuario usuario, Usuario obj) {
		if(repository.findByEmail(obj.getEmail()) != null)
			throw new ResourceDataConflit(obj.getEmail());
		
		usuario.setEmail(obj.getEmail());
		usuario.setNome(usuario.getNome());
		usuario.setSobrenome(usuario.getSobrenome());
	}
	
	public String dataParaString(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data);
	}
	
}

