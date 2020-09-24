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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="Recursos para consulta de dados da comercialização de combustíveis importados do CSV.")
@RestController
@RequestMapping(value = "/historicoDePrecos")
public class HistoricoPrecoCombustivelResource {
	
	@Autowired
	private HistoricoPrecoCombustivelService hpcService;

	@ApiOperation(value = "Buscas todos os registro de histórico de preço de combustível")
	@GetMapping
	public ResponseEntity<List<HistoricoPrecoCombustivel>> findAll() {
		List<HistoricoPrecoCombustivel> list = hpcService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@ApiOperation(value = "Retorna um determinado registro de histórico de preço de combustível")
	@GetMapping(value = "/{id}")
	public ResponseEntity<HistoricoPrecoCombustivel> findById(@PathVariable Long id) {
		HistoricoPrecoCombustivel obj = hpcService.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@ApiOperation(value = "Cria um registro de histórico de preço de combustível")
	@PostMapping
	public ResponseEntity<HistoricoPrecoCombustivel> insert(@RequestBody HistoricoPrecoCombustivel hpc) {
		hpc = hpcService.insert(hpc);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
				buildAndExpand(hpc.getId()).toUri();
		return ResponseEntity.created(uri).body(hpc);
	}
	
	@ApiOperation(value = "Exclui um registro de histórico de preço de combustível")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity <Void> delete(@PathVariable Long id) {
		hpcService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Atualiza um registro de histórico de preço de combustível")
	@PutMapping(value = "/{id}")
	public ResponseEntity<HistoricoPrecoCombustivel> update(@PathVariable Long id, @RequestBody HistoricoPrecoCombustivel hpc) {
		hpc = hpcService.update(id, hpc);
		return ResponseEntity.ok().body(hpc);
	}
	
	@ApiOperation(value = "Importa dados do arquivo CSV")
	@PostMapping(value = "/importaCSV")
	public ResponseEntity <Void> importaCSV(@RequestBody String nomeDoArquivo) {
		System.out.println("Lendo arquivo " + nomeDoArquivo);
		hpcService.lerArquivoCSV(nomeDoArquivo);
		System.out.println("Terminou");
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Retorna média de preço de combustível com base no nome do município")
	@GetMapping(value = "/mediaDePrecoBaseadoNoMunicipio/{nomeMunicipio}")
	public ResponseEntity<Double> mediaDePrecoBaseadoNoMunicipio(@PathVariable String nomeMunicipio) {
		return ResponseEntity.ok().body(hpcService.mediaDePrecoBaseadoNoMunicipio(nomeMunicipio));
	}
	
	@ApiOperation(value = "Retorna todas as informações importadas por sigla da região")
	@GetMapping(value = "/todasInformacoesPorRegiao")
	public ResponseEntity<HistoricoPrecoCombustivel> todasInformacoesPorRegiao() {
		HistoricoPrecoCombustivel obj = new HistoricoPrecoCombustivel();
		return ResponseEntity.ok().body(obj);
	}
	
	@ApiOperation(value = "dados agrupados por distribuidora")
	@GetMapping(value = "/dadosAgrupadosPorDistribuidora")
	public ResponseEntity<HistoricoPrecoCombustivel> dadosAgrupadosPorDistribuidora() {
		HistoricoPrecoCombustivel obj = new HistoricoPrecoCombustivel();
		return ResponseEntity.ok().body(obj);
	}
	
	@ApiOperation(value = "Retorna dados agrupados por data da coleta")
	@GetMapping(value = "/dadosAgrupadosPorDataColeta")
	public ResponseEntity<HistoricoPrecoCombustivel> dadosAgrupadosPorDataColeta() {
		HistoricoPrecoCombustivel obj = new HistoricoPrecoCombustivel();
		return ResponseEntity.ok().body(obj);
	}
	
	@ApiOperation(value = "Retorna o valor médio do valor da compra e do valor da venda por município")
	@GetMapping(value = "/mediaValorCompraEVendaPorMunicipio")
	public ResponseEntity<List<String>> mediaValorCompraEVendaPorMunicipio() {
		return ResponseEntity.ok().body(hpcService.mediaValorCompraEVendaPorMunicipio());
	}
	
	@ApiOperation(value = "Retorna o valor médio do valor da compra e do valor da venda por bandeira")
	@GetMapping(value = "/mediaValorCompraEVendaPorBandeira")
	public ResponseEntity<List<String>> mediaValorCompraEVendaPorBandeira() {
		return ResponseEntity.ok().body(hpcService.mediaValorCompraEVendaPorBandeira());
	}

}
