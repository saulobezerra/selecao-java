package com.saulo.desafio.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.saulo.desafio.dtos.ValorMedioPorBandeiraDTO;
import com.saulo.desafio.dtos.ValorMedioPorMunicipioDTO;
import com.saulo.desafio.entities.HistoricoPrecoCombustivel;
import com.saulo.desafio.services.HistoricoPrecoCombustivelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	public ResponseEntity<HistoricoPrecoCombustivel> findById(
			@ApiParam(value="Número inteiro que represente o id de um registro de histórico.", example = "1", required = true)
			@PathVariable Long id) {
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
	public ResponseEntity <Void> delete(
			@ApiParam(value="Número inteiro que represente o id de um registro de histórico.", example = "1", required = true)
			@PathVariable Long id) {
		hpcService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Atualiza um registro de histórico de preço de combustível")
	@PutMapping(value = "/{id}")
	public ResponseEntity<HistoricoPrecoCombustivel> update(
			@ApiParam(value="Número inteiro que represente o id de um registro de histórico.", example = "1", required = true)
			@PathVariable Long id, 
			@RequestBody HistoricoPrecoCombustivel hpc) {
		hpc = hpcService.update(id, hpc);
		return ResponseEntity.ok().body(hpc);
	}
	
	@ApiOperation(value = "Importa dados do arquivo CSV")
	@PostMapping(value = "/importaCSV")
	public ResponseEntity <Void> importaCSV(
			@ApiParam(value="Caminho do arquivo CSV a ser importado. Exemplo: C:\\Users\\saulo\\Desktop\\arquivo.csv.\n"
					+ " AVISO: A importação do arquivo para a base de dados está demora entre 2 e 3 minutos.", 
			required = true)
			@RequestBody String nomeDoArquivo) {
		
		System.out.println("Importando arquivo... " + nomeDoArquivo);
		hpcService.lerArquivoCSV(nomeDoArquivo);
		System.out.println("Terminou");
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Retorna média de preço de combustível com base no nome do município")
	@GetMapping(value = "/mediaDePrecoBaseadoNoMunicipio/{nomeMunicipio}")
	public ResponseEntity<Double> mediaDePrecoBaseadoNoMunicipio(
			@ApiParam(value="Nome do município o qual quer consultar. Você pode consultar a lista de municipios em Localidade-Resource", 
			example = "", required = true)
			@PathVariable String nomeMunicipio) {
		return ResponseEntity.ok().body(hpcService.mediaDePrecoBaseadoNoMunicipio(nomeMunicipio));
	}
	
	@ApiOperation(value = "Retorna todas as informações importadas por sigla da região")
	@GetMapping(value = "/todasInformacoesPorRegiao/page")
	public ResponseEntity<Page<HistoricoPrecoCombustivel>> todasInformacoesPorRegiao(
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="page", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="page", defaultValue = "id") String orderBy,
			@RequestParam(value="page", defaultValue = "ASC") String direction) {
		return ResponseEntity.ok().body(hpcService.todasInformacoesPorRegiao(page, linesPerPage, orderBy, direction));
	}
	
	@ApiOperation(value = "Retorna os dados agrupados por distribuidora")
	@GetMapping(value = "/dadosAgrupadosPorDistribuidora/page")
	public ResponseEntity<Page<HistoricoPrecoCombustivel>> dadosAgrupadosPorDistribuidora(
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="page", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="page", defaultValue = "id") String orderBy,
			@RequestParam(value="page", defaultValue = "ASC") String direction) {
		return ResponseEntity.ok().body(hpcService.dadosAgrupadosPorDistribuidora(page, linesPerPage, orderBy, direction));
	}
	
	@ApiOperation(value = "Retorna os dados agrupados por data da coleta")
	@GetMapping(value = "/dadosAgrupadosPorDataColeta/page")
	public ResponseEntity<Page<HistoricoPrecoCombustivel>> dadosAgrupadosPorDataColeta(
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="page", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="page", defaultValue = "id") String orderBy,
			@RequestParam(value="page", defaultValue = "ASC") String direction) {
		return ResponseEntity.ok().body(hpcService.dadosAgrupadosPorDataColeta(page, linesPerPage, orderBy, direction));
	}
	
	@ApiOperation(value = "Retorna o valor médio do valor da compra e do valor da venda por município")
	@GetMapping(value = "/mediaValorCompraEVendaPorMunicipio")
	public ResponseEntity<List<ValorMedioPorMunicipioDTO>> mediaValorCompraEVendaPorMunicipio() {
		return ResponseEntity.ok().body(hpcService.mediaValorCompraEVendaPorMunicipio());
	}
	
	@ApiOperation(value = "Retorna o valor médio do valor da compra e do valor da venda por bandeira")
	@GetMapping(value = "/mediaValorCompraEVendaPorBandeira")
	public ResponseEntity<List<ValorMedioPorBandeiraDTO>> mediaValorCompraEVendaPorBandeira() {
		return ResponseEntity.ok().body(hpcService.mediaValorCompraEVendaPorBandeira());
	}

}
