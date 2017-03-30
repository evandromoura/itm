package br.com.trixti.itm.to;

import java.util.List;

import br.com.trixti.itm.entity.ContaCorrente;

public class ContaCorrenteTO {

	private ContaCorrente conta;
	
	
	private List<ContaCorrente> contas;
	

	
	
	public ContaCorrente getContaCorrente() {
		if (conta == null) {
			conta = new ContaCorrente();
		}
		return conta;
	}



	public List<ContaCorrente> getContas() {
		return contas;
	}

	public void setContas(List<ContaCorrente> contas) {
		this.contas = contas;
	}
	
	
	
	
	
	
	
	


}