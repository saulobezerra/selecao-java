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

import com.saulo.desafio.entities.Localidade;
import com.saulo.desafio.services.LocalidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api( description = "Recursos para CRUD de revendedora de combustível")
@RestController
@RequestMapping(value = "/localidades")
public class LocalidadeResource {
	
	@Autowired
	private LocalidadeService localidadeService;

	@ApiOperation(value = "Retorna uma lista com todas as localidades das distribuidoras")
	@GetMapping
	public ResponseEntity<List<Localidade>> findAll() {
		List<Localidade> list = localidadeService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@ApiOperation(value = "Retorna um localidade especificada pelo Id")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Localidade> findById(
			@ApiParam(value="Número inteiro que represente o id da localidade.", example = "1", required = true)
			@PathVariable Long id) {
		Localidade obj = localidadeService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@ApiOperation(value = "Insere uma nova localidade")
	@PostMapping
	public ResponseEntity<Localidade> insert(@RequestBody Localidade localidade) {
		localidade = localidadeService.insert(localidade);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
				buildAndExpand(localidade.getId()).toUri();
		return ResponseEntity.created(uri).body(localidade);
	}
	
	@ApiOperation(value = "Exclui uma localidade")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity <Void> delete(
			@ApiParam(value="Número inteiro que represente o id da localidade.", example = "1", required = true)
			@PathVariable Long id) {
		localidadeService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Edita uma localidade")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Localidade> update(
			@ApiParam(value="Número inteiro que represente o id da localidade.", example = "1", required = true)
			@PathVariable Long id, @RequestBody Localidade localidade) {
		localidade = localidadeService.update(id, localidade);
		return ResponseEntity.ok().body(localidade);
	}

}
