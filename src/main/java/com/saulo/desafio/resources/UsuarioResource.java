package com.saulo.desafio.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import com.saulo.desafio.dtos.UsuarioDTO;
import com.saulo.desafio.entities.Usuario;
import com.saulo.desafio.services.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api( description = "Recursos para CRUD de usuário")
@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService service;

	@ApiOperation(value = "Retorna uma lista com todos os usuários cadastrados")
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> findAll() {
		List<Usuario> list = service.findAll();
		List<UsuarioDTO> listDto = list.stream().map(usuario -> new UsuarioDTO(usuario)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@ApiOperation(value = "Retorna um determinado usuário especificado pelo id")
	@GetMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> findById(
			@ApiParam(value="Número inteiro que represente o id do usuário.", example = "1", required = true) 
			@PathVariable Long id) {
		
		Usuario user = service.findById(id);
		UsuarioDTO userDto = new UsuarioDTO(user); 
		return ResponseEntity.ok().body(userDto);
	}
	
	@ApiOperation(value = "Cadastra um novo usuário")
	@PostMapping
	public ResponseEntity<UsuarioDTO> insert(
			@ApiParam(required = true)
			@RequestBody Usuario usuario) {
		
		usuario = service.insert(usuario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
				buildAndExpand(usuario.getId()).toUri();
		
		UsuarioDTO userDto = new UsuarioDTO(usuario); 
		return ResponseEntity.created(uri).body(userDto);
	}
	
	@ApiOperation(value = "Exclui um determinado usuário especificado pelo id")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity <Void> delete(
			@ApiParam(value="Número inteiro que represente o id do usuário.", example = "1", required = true)
			@PathVariable Long id) {
		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Atualiza os dados de um determinado usuário")
	@PutMapping(value = "/{id}")
	public ResponseEntity<UsuarioDTO> update(
			@ApiParam(value="Número inteiro que represente o id do usuário.", example = "1", required = true)
			@PathVariable Long id, 
			@ApiParam(value="Objeto json com dados do usuário.", required = true)
			@RequestBody Usuario usuario) {
		
		usuario = service.update(id, usuario);
		UsuarioDTO userDto = new UsuarioDTO(usuario); 
		return ResponseEntity.ok().body(userDto);
	}
}
