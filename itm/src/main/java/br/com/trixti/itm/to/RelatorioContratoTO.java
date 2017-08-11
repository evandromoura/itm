package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.entity.Produto;
import br.com.trixti.itm.entity.StatusContrato;

public class RelatorioContratoTO {
	
	private List<Contrato> contratos;
	private Map<Grupo,Integer> mapaQuantidadePorGrupo;
	private Map<Produto,Integer> mapaQuantidadePorProduto;
	private Map<StatusContrato,Integer> mapaQuantidadePorStatusContrato;
	private Map<String,Integer> mapaQuantidadeContratoBoleto;
	
	public List<Contrato> getContratos() {
		if (contratos == null) {
			contratos = new ArrayList<Contrato>();
		}

		return contratos;
	}
	
	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}
	
	public Map<Grupo, Integer> getMapaQuantidadePorGrupo() {
		if (mapaQuantidadePorGrupo == null) {
			mapaQuantidadePorGrupo = new HashMap<Grupo,Integer>();
		}
		return mapaQuantidadePorGrupo;
	}
	
	public void setMapaQuantidadePorGrupo(Map<Grupo, Integer> mapaQuantidadePorGrupo) {
		this.mapaQuantidadePorGrupo = mapaQuantidadePorGrupo;
	}
	
	public String getMapaQuantidadePorGrupoJson(){
		StringBuilder sb = new StringBuilder();
		int i=0;
		int total = 0;
		for(Grupo grupo:getMapaQuantidadePorGrupo().keySet()){
			total += getMapaQuantidadePorGrupo().get(grupo);
		}
		for(Grupo grupo:getMapaQuantidadePorGrupo().keySet()){
			sb.append("{\"name\":\""+grupo.getNome()+"\",\"y\":"+getPorcentagem(total, getMapaQuantidadePorGrupo().get(grupo))+"}");
			i++;
			if(i<getMapaQuantidadePorGrupo().keySet().size()){
				sb.append(",");
			}
		}
		
		return sb.toString();
	}


	
	public Map<Produto, Integer> getMapaQuantidadePorProduto() {
		if (mapaQuantidadePorProduto == null) {
			mapaQuantidadePorProduto = new HashMap<Produto,Integer>();
		}
		return mapaQuantidadePorProduto;
	}
	
	public void setMapaQuantidadePorProduto(Map<Produto, Integer> mapaQuantidadePorProduto) {
		this.mapaQuantidadePorProduto = mapaQuantidadePorProduto;
	}
	
	public String getMapaQuantidadePorProdutoJson(){
		StringBuilder sb = new StringBuilder();
		int i=0;
		int total = 0;
		for(Produto produto:getMapaQuantidadePorProduto().keySet()){
			total += getMapaQuantidadePorProduto().get(produto);
		}
		for(Produto produto:getMapaQuantidadePorProduto().keySet()){
			sb.append("{\"name\":\""+produto.getNome()+"\",\"y\":"+getPorcentagem(total, getMapaQuantidadePorProduto().get(produto))+"}");
			i++;
			if(i<getMapaQuantidadePorProduto().keySet().size()){
				sb.append(",");
			}
		}
		
		return sb.toString();
	}
	
	public Map<StatusContrato, Integer> getMapaQuantidadePorStatusContrato() {
		if (mapaQuantidadePorStatusContrato == null) {
			mapaQuantidadePorStatusContrato = new HashMap<StatusContrato,Integer>();
		}
		return mapaQuantidadePorStatusContrato;
	}
	
	public void setMapaQuantidadePorStatusContrato(Map<StatusContrato, Integer> mapaQuantidadePorStatusContrato) {
		this.mapaQuantidadePorStatusContrato = mapaQuantidadePorStatusContrato;
	}
	
	public String getMapaQuantidadePorStatusContratoJson(){
		StringBuilder sb = new StringBuilder();
		int i=0;
		int total = 0;
		for(StatusContrato statusContrato:getMapaQuantidadePorStatusContrato().keySet()){
			total += getMapaQuantidadePorStatusContrato().get(statusContrato);
		}
		for(StatusContrato statusContrato:getMapaQuantidadePorStatusContrato().keySet()){
			sb.append("{\"name\":\""+statusContrato.getName()+"\",\"y\":"+getPorcentagem(total, getMapaQuantidadePorStatusContrato().get(statusContrato))+"}");
			i++;
			if(i<getMapaQuantidadePorStatusContrato().keySet().size()){
				sb.append(",");
			}
		}
		
		return sb.toString();
	}
	
	
	
	
	public String getMapaQuantidadePorBoletoContratoJson(){
		StringBuilder sb = new StringBuilder();
		int i=0;
		int total = 0;
		for(String statusContrato:getMapaQuantidadeContratoBoleto().keySet()){
			total += getMapaQuantidadeContratoBoleto().get(statusContrato);
		}
		for(String statusContrato:getMapaQuantidadeContratoBoleto().keySet()){
			sb.append("{\"name\":\""+statusContrato+"\",\"y\":"+getPorcentagem(total, getMapaQuantidadeContratoBoleto().get(statusContrato))+"}");
			i++;
			if(i<getMapaQuantidadeContratoBoleto().keySet().size()){
				sb.append(",");
			}
		}
		
		return sb.toString();
	}
	
	
	public Long getPorcentagem(Integer total,Integer qtd){
		return new Long((100 * qtd)/total);
	}

	public Map<String, Integer> getMapaQuantidadeContratoBoleto() {
		if (mapaQuantidadeContratoBoleto == null) {
			mapaQuantidadeContratoBoleto = new HashMap<String,Integer>();
		}
		return mapaQuantidadeContratoBoleto;
	}

	public void setMapaQuantidadeContratoBoleto(Map<String, Integer> mapaQuantidadeContratoBoleto) {
		this.mapaQuantidadeContratoBoleto = mapaQuantidadeContratoBoleto;
	}

}
