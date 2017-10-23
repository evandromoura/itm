package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Parametro;

public class RelatorioClienteInadimplenteTO {

	private Map<Cliente,List<Boleto>> mapaClienteInadimplente;
	
	private List<Boleto> boletos15Dias;
	private List<Boleto> boletos30Dias;
	private List<Boleto> boletos60Dias;
	private List<Boleto> boletos90Dias;
	private Parametro parametro;
	private Cliente cliente;
	private List<Cliente> clientes;


	public List<Cliente> getClientes() {
		if (clientes == null) {
			clientes = new ArrayList<Cliente>();
		}
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Map<Cliente, List<Boleto>> getMapaClienteInadimplente() {
		if (mapaClienteInadimplente == null) {
			mapaClienteInadimplente = new HashMap<Cliente,List<Boleto>>();
		}
		return mapaClienteInadimplente;
	}

	public void setMapaClienteInadimplente(Map<Cliente, List<Boleto>> mapaClienteInadimplente) {
		this.mapaClienteInadimplente = mapaClienteInadimplente;
	}
	
	public List<Boleto> getBoletos15Dias() {
		if (boletos15Dias == null) {
			boletos15Dias = new ArrayList<Boleto>();
		}
		return boletos15Dias;
	}

	public void setBoletos15Dias(List<Boleto> boletos15Dias) {
		this.boletos15Dias = boletos15Dias;
	}
	
	public List<Boleto> getBoletos30Dias() {
		if (boletos30Dias == null) {
			boletos30Dias = new ArrayList<Boleto>();
		}
		return boletos30Dias;
	}

	public void setBoletos30Dias(List<Boleto> boletos30Dias) {
		this.boletos30Dias = boletos30Dias;
	}
	
	public List<Boleto> getBoletos60Dias() {
		if (boletos60Dias == null) {
			boletos60Dias = new ArrayList<Boleto>();
		}
		return boletos60Dias;
	}

	public void setBoletos60Dias(List<Boleto> boletos60Dias) {
		this.boletos60Dias = boletos60Dias;
	}
	
	public List<Boleto> getBoletos90Dias() {
		if (boletos90Dias == null) {
			boletos90Dias = new ArrayList<Boleto>();
		}
		return boletos90Dias;
	}

	public void setBoletos90Dias(List<Boleto> boletos90Dias) {
		this.boletos90Dias = boletos90Dias;
	}

	public Parametro getParametro() {
		if (parametro == null) {
			parametro = new Parametro();
		}
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
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
