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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api( description = "Recursos para CRUD das revendedoras (distribuidora) de combustíveis")
@RestController
@RequestMapping(value = "/revendedora")
public class RevendedoraResource {
	
	@Autowired
	private RevendedoraService revendedoraService;

	@ApiOperation(value = "Retorna uma lista com todos as revendedoras cadastrados")
	@GetMapping
	public ResponseEntity<List<Revendedora>> findAll() {
		List<Revendedora> list = revendedoraService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@ApiOperation(value = "Consulta uma determinada revendedora pelo Id.")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Revendedora> findById(
			@ApiParam(value="Número inteiro que represente o id da revendedora.", example = "1", required = true)
			@PathVariable Long id) {
		Revendedora obj = revendedoraService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@ApiOperation(value = "Cadastra uma nova revendedora.")
	@PostMapping
	public ResponseEntity<Revendedora> insert(@RequestBody Revendedora revendedora) {
		revendedora = revendedoraService.insert(revendedora);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
				buildAndExpand(revendedora.getId()).toUri();
		return ResponseEntity.created(uri).body(revendedora);
	}
	
	@ApiOperation(value = "Exclui uma determinada revendedora.")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity <Void> delete(
			@ApiParam(value="Número inteiro que represente o id da revendedora.", example = "1", required = true)
			@PathVariable Long id) {
		revendedoraService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Atualiza os dados de uma revendedora.")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Revendedora> update(
			@ApiParam(value="Número inteiro que represente o id da revendedora.", example = "1", required = true)
			@PathVariable Long id, 
			@RequestBody Revendedora revendedora) {
		revendedora = revendedoraService.update(id, revendedora);
		return ResponseEntity.ok().body(revendedora);
	}

}
