package br.com.trixti.itm.to;

import br.com.trixti.itm.entity.Cliente;

public class LoginTO {
	
	private Cliente cliente;
	private boolean primeiroAcesso;

	public boolean isPrimeiroAcesso() {
		return primeiroAcesso;
	}

	public void setPrimeiroAcesso(boolean primeiroAcesso) {
		this.primeiroAcesso = primeiroAcesso;
	}

	public Cliente getCliente() {
		if (cliente == null) {
			cliente = new Cliente();
		}

		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	

}
