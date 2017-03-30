package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.ContaCorrente;

public class ContaCorrenteTO {

	private ContaCorrente conta;
	private List<ContaCorrente> contas;
	private ContaCorrente contaPesquisa;
	
	
	public ContaCorrente getConta() {
		if (conta == null) {
			conta = new ContaCorrente();
		}
		return conta;
	}
	
	public void setConta(ContaCorrente conta) {
		this.conta = conta;
	}
	
	public List<ContaCorrente> getContas() {
		if (contas == null) {
			contas = new ArrayList<ContaCorrente>();
		}

		return contas;
	}
	
	public void setContas(List<ContaCorrente> contas) {
		this.contas = contas;
	}

	public ContaCorrente getContaPesquisa() {
		if (contaPesquisa == null) {
			contaPesquisa = new ContaCorrente();
		}
		return contaPesquisa;
	}

	public void setContaPesquisa(ContaCorrente contaPesquisa) {
		this.contaPesquisa = contaPesquisa;
	}

}