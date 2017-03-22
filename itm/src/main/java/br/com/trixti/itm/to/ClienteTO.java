package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.ClienteLancamento;
import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.entity.Produto;

public class ClienteTO {
	
	
	/**
	 * Produto a ser Manipulado no Form
	 */
	private Produto produto;
	
	
	/**
	 * Equipamento a ser Manipulado no Form
	 */
	private Equipamento equipamento;
	
	
	
	/**
	 * Grupo a ser Manipulado no Form
	 */
	private Grupo grupo;
	
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
	
	
	private ClienteLancamento clienteLancamento;

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

	public Produto getProduto() {
		if (produto == null) {
			produto = new Produto();
		}
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Equipamento getEquipamento() {
		if (equipamento == null) {
			equipamento = new Equipamento();
		}
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Grupo getGrupo() {
		if (grupo == null) {
			grupo = new Grupo();
		}
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public ClienteLancamento getClienteLancamento() {
		if (clienteLancamento == null) {
			clienteLancamento = new ClienteLancamento();
		}
		return clienteLancamento;
	}

	public void setClienteLancamento(ClienteLancamento clienteLancamento) {
		this.clienteLancamento = clienteLancamento;
	}

}
