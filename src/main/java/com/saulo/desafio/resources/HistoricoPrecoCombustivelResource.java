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

import com.saulo.desafio.entities.HistoricoPrecoCombustivel;
import com.saulo.desafio.services.HistoricoPrecoCombustivelService;

@RestController
@RequestMapping(value = "/historicoDePrecos")
public class HistoricoPrecoCombustivelResource {
	
	@Autowired
	private HistoricoPrecoCombustivelService hpcService;

	@GetMapping
	public ResponseEntity<List<HistoricoPrecoCombustivel>> findAll() {
		List<HistoricoPrecoCombustivel> list = hpcService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<HistoricoPrecoCombustivel> findById(@PathVariable Long id) {
		HistoricoPrecoCombustivel obj = hpcService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<HistoricoPrecoCombustivel> insert(@RequestBody HistoricoPrecoCombustivel hpc) {
		hpc = hpcService.insert(hpc);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
				buildAndExpand(hpc.getId()).toUri();
		return ResponseEntity.created(uri).body(hpc);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity <Void> delete(@PathVariable Long id) {
		hpcService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<HistoricoPrecoCombustivel> update(@PathVariable Long id, @RequestBody HistoricoPrecoCombustivel hpc) {
		hpc = hpcService.update(id, hpc);
		return ResponseEntity.ok().body(hpc);
	}
	
	@PostMapping(value = "/importaCSV")
	public ResponseEntity <Void> importaCSV(@RequestBody String nomeDoArquivo) {
		System.out.println("Lendo arquivo " + nomeDoArquivo);
		hpcService.lerArquivoCSV(nomeDoArquivo);
		System.out.println("Terminou");
		return ResponseEntity.noContent().build();
	}

}
