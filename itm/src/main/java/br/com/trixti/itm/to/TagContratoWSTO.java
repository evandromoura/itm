package br.com.trixti.itm.to;

import java.math.BigDecimal;

public class TagContratoWSTO {

	private String nome;
	private Integer qtdClientesPagantes;
	private Integer qtdClienteNaoPagantes;
	private BigDecimal valorTotal;
	
	public TagContratoWSTO(String nome, Integer qtdClientesPagantes,Integer qtdClienteNaoPagantes,BigDecimal valorTotal){
		setNome(nome);
		setQtdClientesPagantes(qtdClientesPagantes);
		setQtdClienteNaoPagantes(qtdClienteNaoPagantes);
		setValorTotal(valorTotal);
	}
	
	public Integer getQtdClientesPagantes() {
		return qtdClientesPagantes;
	}
	public void setQtdClientesPagantes(Integer qtdClientesPagantes) {
		this.qtdClientesPagantes = qtdClientesPagantes;
	}
	public Integer getQtdClienteNaoPagantes() {
		return qtdClienteNaoPagantes;
	}
	public void setQtdClienteNaoPagantes(Integer qtdClienteNaoPagantes) {
		this.qtdClienteNaoPagantes = qtdClienteNaoPagantes;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
