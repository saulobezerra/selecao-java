package com.saulo.desafio.services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saulo.desafio.entities.HistoricoPrecoCombustivel;
import com.saulo.desafio.entities.Localidade;
import com.saulo.desafio.entities.Produto;
import com.saulo.desafio.entities.Revendedora;
import com.saulo.desafio.repositories.HistoricoPrecoCombustivelRepository;

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
//		System.out.println(obj.getDataDaColeta());
//		if (obj.getLocalidade().getId() == null) {			
//			Localidade local = localService.findByMunicipio(obj.getLocalidade().getMunicipio());
//			if (local == null) {
//				local = new Localidade(null, obj.getLocalidade().getRegiao(), obj.getLocalidade().getEstado(), obj.getLocalidade().getMunicipio());
//				localService.insert(local);
//			}
//		}
//		
//		if (obj.getProduto().getId() == null) {
//			Produto produto = prodService.findByNomeProduto(obj.getProduto().getNome());
//			if (produto == null) {
//				produto = new Produto(null, obj.getProduto().getNome(), obj.getProduto().getValorDeVenda(), obj.getProduto().getValorDeCompra());
//				prodService.insert(produto);
//			}
//		}
//		
//		if (obj.getRevendedora().getId() == null) {			
//			Revendedora rev = revService.findByNomeRevendedora(obj.getRevendedora().getNome());
//			if (rev == null) {
//				rev = new Revendedora(null, obj.getRevendedora().getNome(), obj.getRevendedora().getCnpj(), obj.getRevendedora().getBandeira());
//				revService.insert(rev);
//			}
//		}
		return repository.save(obj);
	}

	public HistoricoPrecoCombustivel findById(Long id) {
		Optional<HistoricoPrecoCombustivel> obj = repository.findById(id);
		return obj.get();
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
	
	public void lerArquivoCSV(String path) {
		
		if (path == "" )
			path = "defaultCSV.csv";

		try(BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(path), StandardCharsets.ISO_8859_1) )){
			String linha = "";
			StringBuilder line = new StringBuilder(br.readLine());
			int numLine = 1;
			while(line != null) {
				if (numLine > 1 && line.length() != 1)
					carregaBaseDeDados(line, numLine);
			    
				linha = br.readLine();
				if (linha == null)
					break;
				line = new StringBuilder(linha);
				numLine++;				
			}
			
		} catch(IOException e) {
			System.out.println("lerArquivoCSV - Error: " + e.getMessage());
		}
	}
	
	public void carregaBaseDeDados(StringBuilder line, int numLine) {
		StringBuilder linha = new StringBuilder("");
		
		char[] letras = new char[line.length()];
		line.getChars(0, line.length(), letras, 0);
		for (int i=0; i < letras.length; i++) {
		    if (letras[i] != 0) {
		       linha.append(letras[i]);
		       
		    }
		}	

		String [] contentCSV = linha.toString().split("\\t");
		try {
			if (contentCSV.length == 11 && numLine < 24) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");			
				
				if(contentCSV[7].equals(""))
					contentCSV[7] = "0";
				else
					contentCSV[7] = contentCSV[7].replace(',', '.');
				
				if(contentCSV[8].equals(""))					
					contentCSV[8] = "0";
				else
					contentCSV[8] = contentCSV[8].replace(',', '.');		
				
				
				Localidade local = localService.findByMunicipio(contentCSV[2]);
				if (local == null) {
					local = new Localidade(null, contentCSV[0], contentCSV[1], contentCSV[2]);
					localService.insert(local);
				}
				
				Produto produto = prodService.findByNomeProduto(contentCSV[5]);
				if (produto == null) {
					produto = new Produto(null, contentCSV[5], Double.parseDouble(contentCSV[7]), Double.parseDouble(contentCSV[8]));
					prodService.insert(produto);
				}
				
				Revendedora rev = revService.findByNomeRevendedora(contentCSV[3]);
				if (rev == null) {
					rev = new Revendedora(null, contentCSV[3], contentCSV[4], contentCSV[10]);
					revService.insert(rev);
				}
				
				HistoricoPrecoCombustivel hpc = new HistoricoPrecoCombustivel(null, sdf.parse(contentCSV[6]), local, produto, rev);
				insert(hpc);
			}
				
		} 
		catch(NumberFormatException e) {
			System.out.println("carregaBaseDeDados -NFE Error:" + e.getMessage() + " linha: " + numLine);
		} catch (ParseException e) {
			System.out.println("Erro ao formatar a data da coleta:" + e.getMessage() + " linha: " + numLine);
		}		
	}
	
	public Double mediaDePrecoBaseadoNoMunicipio() {
		return 0d;
	}
	
	public HistoricoPrecoCombustivel todasInformacoesPorRegiao () {
		return null;
	}
	
	public HistoricoPrecoCombustivel dadosAgrupadosPorDistribuidora () {
		return null;
	}
	
	public HistoricoPrecoCombustivel dadosAgrupadosPorDataColeta () {
		return null;
	}
	
	public Double mediaValorCompraEVendaPorMunicipio() {
		return 0d;
	}
	
	public Double mediaValorCompraEVendaPorBandeira() {
		return 0d;
	}
	
	
}

