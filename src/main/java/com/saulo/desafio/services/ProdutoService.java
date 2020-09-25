package com.saulo.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saulo.desafio.entities.Produto;
import com.saulo.desafio.repositories.ProdutoRepository;
import com.saulo.desafio.services.exceptions.ResourceDataConflit;
import com.saulo.desafio.services.exceptions.ResourceNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	public List<Produto> findAll() {
		return repository.findAll();
	}
	
	public Produto insert(Produto obj) {
		if(repository.findByNome(obj.getNome().toUpperCase()) != null)
			new ResourceDataConflit("Produto " + obj.getNome() + " já cadastrado");
		
		obj.setNome(obj.getNome().toUpperCase());
		return repository.save(obj);
	}
	
	public void insertAll(List<Produto> produtos) {
		repository.saveAll(produtos);
	}

	public Produto findById(Long id) {
		Optional<Produto> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public Produto update(Long id, Produto obj) {
		Produto produto = repository.getOne(id);
		udateData(produto, obj);
		return repository.save(produto);
	}
	
	private void udateData(Produto produto, Produto obj) {
		if(repository.findByNome(obj.getNome().toUpperCase()) != null)
			new ResourceDataConflit("Produto " + obj.getNome() + " já cadastrado");
		
		produto.setNome(obj.getNome());
		produto.setValorDeCompra(obj.getValorDeCompra());
		produto.setValorDeVenda(obj.getValorDeVenda());		
	}
	
}

