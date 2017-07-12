package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Radacct;

public class HomeTO {
	
	private Cliente cliente;

	private List<Radacct> listaUtilizacaoCliente;
	
	private List<Boleto> listaUltimosBoletos;
	
	private String oid;
	
	private Map<String,String> listaSnmp;
	
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

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
	
	public Map<String,String> getListaSnmp() {
		if (listaSnmp == null) {
			listaSnmp = new HashMap<String,String>();
		}
		return listaSnmp;
	}

	public void setListaSnmp(Map<String,String> listaSnmp) {
		this.listaSnmp = listaSnmp;
	}
	public List<String> getListaKeySnmp(){
		return new ArrayList<String>(getListaSnmp().keySet());
	}
	
	public Date getDataAtual(){
		return new Date();
	}

}
