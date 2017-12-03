package br.com.trixti.itm.to;

public class ClienteWSTO {
	
	private String cpf;
	private String nome;
	private Integer qtdBoletoEmAtraso;
	
	public ClienteWSTO(String cpf,String nome){
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

	
	
}
