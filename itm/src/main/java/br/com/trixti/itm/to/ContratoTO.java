package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoAutenticacao;
import br.com.trixti.itm.entity.ContratoEquipamento;
import br.com.trixti.itm.entity.ContratoLancamento;
import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.entity.Produto;

public class ContratoTO {
	
	private List<Contrato> contratos;
	
	private Contrato contrato;
	private String chaveMestra;
	private Boleto boleto;
	private boolean exibeSnmp;
	private String protocolo;
	private ContratoEquipamento contratoEquipamento;
	
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
	
	
	
	private ContratoLancamento contratoLancamento;
	
	
	private Date dataVencimentoBoleto;
	
	
	private String abaAtiva;
	
	private ContratoAutenticacao contratoAutenticacao;

	private Parametro parametro;
	
	private List<String> listaSnmp;
	
	private String oid;
	
	private Date dataSegundaViaBoleto;
	
	private Boleto boletoSegundaVia;

	public Contrato getContrato() {
		if (contrato == null) {
			contrato = new Contrato();
		}
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
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

	public ContratoLancamento getContratoLancamento() {
		if (contratoLancamento == null) {
			contratoLancamento = new ContratoLancamento();
		}
		return contratoLancamento;
	}

	public void setContratoLancamento(ContratoLancamento contratoLancamento) {
		this.contratoLancamento = contratoLancamento;
	}

	public String getAbaAtiva() {
		return abaAtiva;
	}

	public void setAbaAtiva(String abaAtiva) {
		this.abaAtiva = abaAtiva;
	}

	public Date getDataVencimentoBoleto() {
		return dataVencimentoBoleto;
	}

	public void setDataVencimentoBoleto(Date dataVencimentoBoleto) {
		this.dataVencimentoBoleto = dataVencimentoBoleto;
	}

	public ContratoAutenticacao getContratoAutenticacao() {
		if (contratoAutenticacao == null) {
			contratoAutenticacao = new ContratoAutenticacao();
		}

		return contratoAutenticacao;
	}

	public void setContratoAutenticacao(ContratoAutenticacao contratoAutenticacao) {
		this.contratoAutenticacao = contratoAutenticacao;
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	public List<String> getListaSnmp() {
		if (listaSnmp == null) {
			listaSnmp = new ArrayList<String>();
		}
		return listaSnmp;
	}

	public void setListaSnmp(List<String> listaSnmp) {
		this.listaSnmp = listaSnmp;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
	
	public Date getDataAtual(){
		return new Date();
	}

	public Date getDataSegundaViaBoleto() {
		return dataSegundaViaBoleto;
	}

	public void setDataSegundaViaBoleto(Date dataSegundaViaBoleto) {
		this.dataSegundaViaBoleto = dataSegundaViaBoleto;
	}

	public Boleto getBoletoSegundaVia() {
		if (boletoSegundaVia == null) {
			boletoSegundaVia = new Boleto();
		}
		return boletoSegundaVia;
	}

	public void setBoletoSegundaVia(Boleto boletoSegundaVia) {
		this.boletoSegundaVia = boletoSegundaVia;
	}

	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}

	public String getChaveMestra() {
		return chaveMestra;
	}

	public void setChaveMestra(String chaveMestra) {
		this.chaveMestra = chaveMestra;
	}

	public Boleto getBoleto() {
		if (boleto == null) {
			boleto = new Boleto();
		}
		return boleto;
	}

	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}

	public boolean isExibeSnmp() {
		return exibeSnmp;
	}

	public void setExibeSnmp(boolean exibeSnmp) {
		this.exibeSnmp = exibeSnmp;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public ContratoEquipamento getContratoEquipamento() {
		if (contratoEquipamento == null) {
			contratoEquipamento = new ContratoEquipamento();
		}
		return contratoEquipamento;
	}

	public void setContratoEquipamento(ContratoEquipamento contratoEquipamento) {
		this.contratoEquipamento = contratoEquipamento;
	}
	

}
