package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Radacct;

public class HomeTO {
	
	private Cliente cliente;

	private List<Radacct> listaUtilizacaoCliente;
	
	private List<Boleto> listaUltimosBoletos;
	
	public List<Radacct> getListaUtilizacaoCliente() {
		return listaUtilizacaoCliente;
	}

	public void setListaUtilizacaoCliente(List<Radacct> listaUtilizacaoCliente) {
		this.listaUtilizacaoCliente = listaUtilizacaoCliente;
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

	public List<Boleto> getListaUltimosBoletos() {
		if (listaUltimosBoletos == null) {
			listaUltimosBoletos = new ArrayList<Boleto>();
		}
		return listaUltimosBoletos;
	}

	public void setListaUltimosBoletos(List<Boleto> listaUltimosBoletos) {
		this.listaUltimosBoletos = listaUltimosBoletos;
	}

}
