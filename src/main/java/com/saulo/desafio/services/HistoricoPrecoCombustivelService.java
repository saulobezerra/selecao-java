package com.saulo.desafio.services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saulo.desafio.dtos.ValorMedioPorBandeiraDTO;
import com.saulo.desafio.dtos.ValorMedioPorMunicipioDTO;
import com.saulo.desafio.entities.HistoricoPrecoCombustivel;
import com.saulo.desafio.entities.Localidade;
import com.saulo.desafio.entities.Produto;
import com.saulo.desafio.entities.Revendedora;
import com.saulo.desafio.repositories.HistoricoPrecoCombustivelRepository;
import com.saulo.desafio.services.exceptions.ResourceNotFoundException;

@Service
public class HistoricoPrecoCombustivelService {

	@Autowired
	private HistoricoPrecoCombustivelRepository repository;
	
	@Autowired
	private LocalidadeService localService;
	
	@Autowired
	private ProdutoService prodService;
	
	@Autowired
	private RevendedoraService revService;
	
	public List<HistoricoPrecoCombustivel> findAll() {
		return repository.findAll();
	}
	
	public HistoricoPrecoCombustivel insert(HistoricoPrecoCombustivel obj) {	
		return repository.save(obj);
	}

	public HistoricoPrecoCombustivel findById(Long id) {
		Optional<HistoricoPrecoCombustivel> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public HistoricoPrecoCombustivel update(Long id, HistoricoPrecoCombustivel obj) {
		HistoricoPrecoCombustivel localidade = repository.getOne(id);
		udateData(localidade, obj);
		return repository.save(localidade);
	}
	
	private void udateData(HistoricoPrecoCombustivel hpc, HistoricoPrecoCombustivel obj) {
		hpc.setDataDaColeta(obj.getDataDaColeta());
		hpc.setLocalidade(obj.getLocalidade());
		hpc.setProduto(obj.getProduto());
		hpc.setRevendedora(obj.getRevendedora());
	}
	
	public String dataParaString(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data);
	}
	
	public Double mediaDePrecoBaseadoNoMunicipio(String nomeMunicipio) {
		return repository.mediaDePrecoBaseadoNoMunicipio(nomeMunicipio.toUpperCase());
	}
	
	public List<HistoricoPrecoCombustivel> todasInformacoesPorRegiao () {
		return repository.todasInformacoesPorRegiao();
	}
	
	public List<HistoricoPrecoCombustivel> dadosAgrupadosPorDistribuidora () {
		return repository.dadosAgrupadosPorDistribuidora();
	}
	
	public List<HistoricoPrecoCombustivel> dadosAgrupadosPorDataColeta () {
		return repository.dadosAgrupadosPorDataColeta();
	}
	
	public List<ValorMedioPorMunicipioDTO> mediaValorCompraEVendaPorMunicipio() {		
		List<String> retornoSql = repository.mediaValorCompraEVendaPorMunicipio();
		List<ValorMedioPorMunicipioDTO> listaDedados = new ArrayList<ValorMedioPorMunicipioDTO>();
		ValorMedioPorMunicipioDTO dados = null;

		for(int i = 0; i < retornoSql.size(); i++) {
			String [] strArray = retornoSql.get(i).split(",");
			dados = new ValorMedioPorMunicipioDTO(strArray[0], Double.parseDouble(strArray[1]), Double.parseDouble(strArray[2]));
			listaDedados.add(dados);
		}
		
		return listaDedados;
	}
	
	public List<ValorMedioPorBandeiraDTO> mediaValorCompraEVendaPorBandeira() {
		List<String> retornoSql = repository.mediaValorCompraEVendaPorBandeira();
		List<ValorMedioPorBandeiraDTO> listaDedados = new ArrayList<ValorMedioPorBandeiraDTO>();
		ValorMedioPorBandeiraDTO dados = null;

		for(int i = 0; i < retornoSql.size(); i++) {
			String [] strArray = retornoSql.get(i).split(",");
			dados = new ValorMedioPorBandeiraDTO(strArray[0], Double.parseDouble(strArray[1]), Double.parseDouble(strArray[2]));
			listaDedados.add(dados);
		}
		
		return listaDedados;
	}
	
	public void lerArquivoCSV(String path) {
		try(BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(path), StandardCharsets.ISO_8859_1) )){
			
			long tempoInicio = System.currentTimeMillis();
			carregaBaseDeDados(br);			
			System.out.println("Tempo Total: "+(System.currentTimeMillis()-tempoInicio));
			
		} catch(IOException e) {
			System.out.println("lerArquivoCSV - Error: " + e.getMessage());
			throw new ResourceNotFoundException(e.getMessage());
		}
	}
	
	public void carregaBaseDeDados(BufferedReader br) throws IOException {
		StringBuilder line = new StringBuilder(br.readLine());
		String linha = "";
		int numLine = 0;
		ArrayList<HistoricoPrecoCombustivel> listHpc = new ArrayList<HistoricoPrecoCombustivel>();
		ArrayList<Produto> listProds = new ArrayList<Produto>();
		ArrayList<Localidade> listLocais = new ArrayList<Localidade>();
		ArrayList<Revendedora> listRev = new ArrayList<Revendedora>();
		Produto produto = null;
		Localidade local = null;
		Revendedora rev = null;

		long tempoInicio = System.currentTimeMillis();
		while(line != null) {
			
			if (numLine > 1 && line.length() != 1) {
				StringBuilder linha2 = new StringBuilder("");
				char[] letras = new char[line.length()];
				line.getChars(0, line.length(), letras, 0);
				for (int i=0; i < letras.length; i++) {
				    if (letras[i] != 0) {
				       linha2.append(letras[i]);
				    }
				}
				
				produto = new Produto();
				local = new Localidade();
				rev = new Revendedora();
				String [] contentCSV = linha2.toString().split("\\t");		
				try {
					if (contentCSV.length == 11) {
						
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");			
						
						if(contentCSV[7].equals(""))
							contentCSV[7] = "0";
						else
							contentCSV[7] = contentCSV[7].replace(',', '.');
						
						if(contentCSV[8].equals(""))					
							contentCSV[8] = "0";
						else
							contentCSV[8] = contentCSV[8].replace(',', '.');		
												
						
						produto.setNome(contentCSV[5]);						
						produto.setValorDeVenda(Double.parseDouble(contentCSV[7]));
						produto.setValorDeCompra(Double.parseDouble(contentCSV[8]));
						if(!listProds.contains(produto))
							listProds.add(produto);
						else {
							produto = listProds.get(listProds.indexOf(produto));
						}
						
						local.setRegiao(contentCSV[0]);
						local.setEstado(contentCSV[1]);
						local.setMunicipio(contentCSV[2]);
						if(!listLocais.contains(local))
							listLocais.add(local);
						else {
							local = listLocais.get(listLocais.indexOf(local));
						}
						
						rev.setNome(contentCSV[3]);
						rev.setCnpj(contentCSV[4]);
						rev.setBandeira(contentCSV[10]);
						if(!listRev.contains(rev))
							listRev.add(rev);
						else {
							rev = listRev.get(listRev.indexOf(rev));
						}
						
						listHpc.add(new HistoricoPrecoCombustivel(null, sdf.parse(contentCSV[6]), 
							local, produto, rev));
					}
						
				} 
				catch(NumberFormatException e) {
					System.out.println("carregaBaseDeDados -NFE Error:" + e.getMessage() + " linha: " + numLine);
				} 
				catch (ParseException e) {
					System.out.println("Erro ao formatar a data da coleta:" + e.getMessage() + " linha: " + numLine);
				}
			}
			
			linha = br.readLine();
			if (linha == null)
				break;
			line = new StringBuilder(linha);
			numLine++;				
		}
		prodService.insertAll(listProds);
		localService.insertAll(listLocais);
		revService.insertAll(listRev);
		repository.saveAll(listHpc);
		System.out.println("Tempo da importação: "+(System.currentTimeMillis()-tempoInicio));
	}
	
}

