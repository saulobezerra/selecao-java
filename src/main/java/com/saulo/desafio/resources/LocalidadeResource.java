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

@RestController
@RequestMapping(value = "/localidades")
public class LocalidadeResource {
	
	@Autowired
	private LocalidadeService localidadeService;

	@GetMapping
	public ResponseEntity<List<Localidade>> findAll() {
		List<Localidade> list = localidadeService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Localidade> findById(@PathVariable Long id) {
		Localidade obj = localidadeService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Localidade> insert(@RequestBody Localidade localidade) {
		localidade = localidadeService.insert(localidade);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
				buildAndExpand(localidade.getId()).toUri();
		return ResponseEntity.created(uri).body(localidade);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity <Void> delete(@PathVariable Long id) {
		localidadeService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Localidade> update(@PathVariable Long id, @RequestBody Localidade localidade) {
		localidade = localidadeService.update(id, localidade);
		return ResponseEntity.ok().body(localidade);
	}

}
