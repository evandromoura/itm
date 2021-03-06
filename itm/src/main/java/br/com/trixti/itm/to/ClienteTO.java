package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.Tag;

public class ClienteTO {
	
	
	
	private Contrato contrato;
	
	private Contrato contratoAcao;
	
	
	/**
	 * Cliente que vai ser manipulado no form
	 */
	private Cliente cliente;
	
	/**
	 * Lista de Clientes da tela de Pesquisa
	 */
	private List<Cliente> clientes;
	
	/**
	 * Cliente usado como filtro de pesquisa
	 */
	private Cliente clientePesquisa;
	
	
	private List<Tag> tags;
	
	
	
	public Cliente getCliente() {
		if (cliente == null) {
			cliente = new Cliente();
		}
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		if (clientes == null) {
			clientes = new ArrayList<Cliente>();
		}
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente getClientePesquisa() {
		if (clientePesquisa == null) {
			clientePesquisa = new Cliente();
		}
		return clientePesquisa;
	}

	public void setClientePesquisa(Cliente clientePesquisa) {
		this.clientePesquisa = clientePesquisa;
	}

	
	public Contrato getContrato() {
		if (contrato == null) {
			contrato = new Contrato();
		}
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Contrato getContratoAcao() {
		if (contratoAcao == null) {
			contratoAcao = new Contrato();
		}
		return contratoAcao;
	}

	public void setContratoAcao(Contrato contratoAcao) {
		this.contratoAcao = contratoAcao;
	}

	public List<Tag> getTags() {
		if (tags == null) {
			tags = new ArrayList<Tag>();
		}
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

}
