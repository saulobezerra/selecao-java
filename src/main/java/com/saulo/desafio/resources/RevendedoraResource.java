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

import com.saulo.desafio.entities.Revendedora;
import com.saulo.desafio.services.RevendedoraService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/revendedora")
public class RevendedoraResource {
	
	@Autowired
	private RevendedoraService revendedoraService;

	@ApiOperation(value = "Retorna uma lista com todos os revendedoras cadastrados")
	@GetMapping
	public ResponseEntity<List<Revendedora>> findAll() {
		List<Revendedora> list = revendedoraService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Revendedora> findById(@PathVariable Long id) {
		Revendedora obj = revendedoraService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Revendedora> insert(@RequestBody Revendedora revendedora) {
		revendedora = revendedoraService.insert(revendedora);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
				buildAndExpand(revendedora.getId()).toUri();
		return ResponseEntity.created(uri).body(revendedora);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity <Void> delete(@PathVariable Long id) {
		revendedoraService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Revendedora> update(@PathVariable Long id, @RequestBody Revendedora revendedora) {
		revendedora = revendedoraService.update(id, revendedora);
		return ResponseEntity.ok().body(revendedora);
	}

}
