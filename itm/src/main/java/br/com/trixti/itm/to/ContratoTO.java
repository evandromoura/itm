package br.com.trixti.itm.to;

import java.util.Date;

import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoAutenticacao;
import br.com.trixti.itm.entity.ContratoLancamento;
import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.entity.Produto;

public class ContratoTO {
	
	private Contrato contrato;
	
	
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

	

}
