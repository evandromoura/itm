package br.com.trixti.itm.to;

import java.util.List;

public class ClienteWSTO {
	
	private Integer id;
	private String cpf;
	private String nome;
	private Integer qtdBoletoEmAtraso;
	private Integer qtdTag;
	private List<TagWS> tags;
	private List<BoletoWS> boletos;
	private boolean resultado;
	private boolean promessa;
	
	public ClienteWSTO(Integer id, String cpf,String nome){
		setId(id);
		setCpf(cpf);
		setNome(nome);
	}
	
	public ClienteWSTO(){
		
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQtdBoletoEmAtraso() {
		return qtdBoletoEmAtraso;
	}

	public void setQtdBoletoEmAtraso(Integer qtdBoletoEmAtraso) {
		this.qtdBoletoEmAtraso = qtdBoletoEmAtraso;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<BoletoWS> getBoletos() {
		return boletos;
	}

	public void setBoletos(List<BoletoWS> boletos) {
		this.boletos = boletos;
	}

	public List<TagWS> getTags() {
		return tags;
	}

	public void setTags(List<TagWS> tags) {
		this.tags = tags;
	}

	public Integer getQtdTag() {
		return qtdTag;
	}

	public void setQtdTag(Integer qtdTag) {
		this.qtdTag = qtdTag;
	}

	public boolean isResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}

	public boolean isPromessa() {
		return promessa;
	}

	public void setPromessa(boolean promessa) {
		this.promessa = promessa;
	}
	
}
