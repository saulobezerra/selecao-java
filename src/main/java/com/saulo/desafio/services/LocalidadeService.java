package com.saulo.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saulo.desafio.entities.Localidade;
import com.saulo.desafio.repositories.LocalidadeRepository;

@Service
public class LocalidadeService {

	@Autowired
	private LocalidadeRepository repository;
	
	public List<Localidade> findAll() {
		return repository.findAll();
	}
	
	public Localidade insert(Localidade obj) {
		return repository.save(obj);
	}

	public Localidade findById(Long id) {
		Optional<Localidade> obj = repository.findById(id);
		return obj.get();
	}
	
	public Localidade findByMunicipio(String nomeMunicipio) {
		Localidade obj = repository.findByMunicipio(nomeMunicipio);
		return obj;
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
		localidade.setEstado(obj.getEstado());
		localidade.setMunicipio(obj.getMunicipio());
		localidade.setRegiao(obj.getRegiao());		
	}

//	public Localidade gravaLocalidade(Localidade localidade) {
//		Localidade rev = new Localidade();
//		rev = new Localidade(null, localidade.getNome(), localidade.getCnpj(), localidade.getBandeira());		
//		return insert(rev);
//	}
	
}

