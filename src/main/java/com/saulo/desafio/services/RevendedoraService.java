package com.saulo.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saulo.desafio.entities.Revendedora;
import com.saulo.desafio.repositories.RevendedoraRepository;

@Service
public class RevendedoraService {

	@Autowired
	private RevendedoraRepository repository;
	
	public List<Revendedora> findAll() {
		return repository.findAll();
	}
	
	public Revendedora insert(Revendedora obj) {
		return repository.save(obj);
	}

	public Revendedora findById(Long id) {
		Optional<Revendedora> obj = repository.findById(id);
		return obj.get();
	}
	
	public Revendedora findByNomeRevendedora(String nomeRevendedora) {
		Revendedora obj = repository.findByNome(nomeRevendedora);
		return obj;
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Revendedora update(Long id, Revendedora obj) {
		Revendedora revendedora = repository.getOne(id);
		udateData(revendedora, obj);
		return repository.save(revendedora);
	}
	
	private void udateData(Revendedora revendedora, Revendedora obj) {
		revendedora.setNome(obj.getNome());
		revendedora.setCnpj(obj.getCnpj());
		revendedora.setBandeira(obj.getBandeira());		
	}

//	public Revendedora gravaRevendedora(Revendedora revendedora) {
//		Revendedora rev = new Revendedora();
//		rev = new Revendedora(null, revendedora.getNome(), revendedora.getCnpj(), revendedora.getBandeira());		
//		return insert(rev);
//	}
	
}

