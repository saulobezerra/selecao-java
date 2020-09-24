package com.saulo.desafio.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saulo.desafio.entities.Usuario;
import com.saulo.desafio.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	public List<Usuario> findAll() {
		return repository.findAll();
	}
	
	public Usuario insert(Usuario obj) {
		return repository.save(obj);
	}

	public Usuario findById(Long id) {
		Optional<Usuario> obj = repository.findById(id);
		return obj.get();
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
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		usuario.setNome(obj.getNome());
//		usuario.setSobrenome(obj.getSobrenome());
//		usuario.setEmail(obj.getEmail());
//		try {
		usuario.setDataNascimento(obj.getDataNascimento());
//		} catch (ParseException e) {
//			System.out.println("UpdateData - Erro no formato da data de nascimento: " + e.getMessage());
//		}
	}

	public Usuario gravaUsuario(Usuario usuario) {
//		Usuario user = new Usuario();
//		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
//		Date dataNascimento;
//		try {
//			dataNascimento = formato.parse(dataParaString(usuario.getDataNascimento()));
//			
//			user = new Usuario(null, usuario.getNome(), usuario.getSobrenome(), usuario.getEmail(),
//					dataNascimento);
//		} catch (ParseException e) {
//			System.out.println("Erro no formato da data de nascimento: " + e.getMessage());
//		}
		return insert(usuario);
	}
	
	public String dataParaString(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data);
	}
	
}

