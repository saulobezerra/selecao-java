package com.saulo.desafio.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.saulo.desafio.entities.Produto;
import com.saulo.desafio.services.ProdutoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api( description = "Recursos para CRUD de produto (combustível)")
@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService produtoService;

	@ApiOperation(value = "Retorna uma lista com todos os produtos cadastrados")
	@GetMapping
	public ResponseEntity<List<Produto>> findAll() {
		List<Produto> list = produtoService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@ApiOperation(value = "Retorna um produto cadastrado especificado pelo Id.")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Produto> findById(
			@ApiParam(value="Número inteiro que represente o id do produto.", example = "1", required = true)
			@PathVariable Long id) {
		Produto obj = produtoService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@ApiOperation(value = "Insere um novo produto")
	@PostMapping
	public ResponseEntity<Produto> insert(@RequestBody Produto produto) {
		produto = produtoService.insert(produto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
				buildAndExpand(produto.getId()).toUri();
		return ResponseEntity.created(uri).body(produto);
	}
	
	@ApiOperation(value = "Exclui um produto determinado pelo Id.")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity <Void> delete(
			@ApiParam(value="Número inteiro que represente o id do produto.", example = "1", required = true)
			@PathVariable Long id) {
		produtoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Edita um determinado produto.")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Produto> update(
			@ApiParam(value="Número inteiro que represente o id do produto.", example = "1", required = true)
			@PathVariable Long id, @RequestBody Produto produto) {
		produto = produtoService.update(id, produto);
		return ResponseEntity.ok().body(produto);
	}

}
