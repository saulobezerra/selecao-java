package com.saulo.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saulo.desafio.entities.Localidade;
import com.saulo.desafio.repositories.LocalidadeRepository;
import com.saulo.desafio.services.exceptions.ResourceDataConflit;
import com.saulo.desafio.services.exceptions.ResourceNotFoundException;

@Service
public class LocalidadeService {

	@Autowired
	private LocalidadeRepository repository;
	
	public List<Localidade> findAll() {
		return repository.findAll();
	}
	
	public Localidade insert(Localidade obj) {
		if(repository.findByMunicipio(obj.getMunicipio().toUpperCase()) != null)
			throw new ResourceDataConflit("O municipio " + obj.getMunicipio() + " já está cadastrado");
		
		obj.setMunicipio(obj.getMunicipio().toUpperCase());
		obj.setEstado(obj.getEstado().toUpperCase());
		obj.setRegiao(obj.getRegiao().toUpperCase());
		return repository.save(obj);
	}
	
	public void insertAll(List<Localidade> localidades) {
		repository.saveAll(localidades);
	}

	public Localidade findById(Long id) {
		Optional<Localidade> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Id " + id));
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Localidade update(Long id, Localidade obj) {
		Localidade localidade = repository.getOne(id);
		udateData(localidade, obj);
		return repository.save(localidade);
	}
	
	private void udateData(Localidade localidade, Localidade obj) {
		if(repository.findByMunicipio(obj.getMunicipio().toUpperCase()) != null)
			throw new ResourceDataConflit("O municipio " + obj.getMunicipio() + " já está cadastrado");
		
		localidade.setEstado(obj.getEstado());
		localidade.setMunicipio(obj.getMunicipio());
		localidade.setRegiao(obj.getRegiao());		
	}
	
	public List<String> findAllMunicipios() {
		return repository.findAllMunicipios();
	}

}

