package com.saulo.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saulo.desafio.entities.Produto;
import com.saulo.desafio.repositories.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	public List<Produto> findAll() {
		return repository.findAll();
	}
	
	public Produto insert(Produto obj) {
		return repository.save(obj);
	}

	public Produto findById(Long id) {
		Optional<Produto> obj = repository.findById(id);
		return obj.get();
	}
	
	public Produto findByNomeProduto(String nomeProduto) {
		Produto obj = repository.findByNome(nomeProduto);
		return obj;
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
		produto.setNome(obj.getNome());
		produto.setValorDeCompra(obj.getValorDeCompra());
		produto.setValorDeVenda(obj.getValorDeVenda());		
	}

//	public Produto gravaProduto(Produto produto) {
//		Produto rev = new Produto();
//		rev = new Produto(null, produto.getNome(), produto.getCnpj(), produto.getBandeira());		
//		return insert(rev);
//	}
	
}

