package br.com.trixti.itm.to;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardWSTO {

	private Integer qtdCliente;
	private Integer qtdContrato;
	private BigDecimal totalAtrasado;
	private BigDecimal totalFixoMensal;
	
	private NovosContratosWSTO novosContratos;
	private List<TagContratoWSTO> listaTags;
	
	public Integer getQtdCliente() {
		return qtdCliente;
	}
	public void setQtdCliente(Integer qtdCliente) {
		this.qtdCliente = qtdCliente;
	}
	public Integer getQtdContrato() {
		return qtdContrato;
	}
	public void setQtdContrato(Integer qtdContrato) {
		this.qtdContrato = qtdContrato;
	}
	public BigDecimal getTotalAtrasado() {
		return totalAtrasado;
	}
	public void setTotalAtrasado(BigDecimal totalAtrasado) {
		this.totalAtrasado = totalAtrasado;
	}
	public BigDecimal getTotalFixoMensal() {
		return totalFixoMensal;
	}
	public void setTotalFixoMensal(BigDecimal totalFixoMensal) {
		this.totalFixoMensal = totalFixoMensal;
	}
	
	public NovosContratosWSTO getNovosContratos() {
		if (novosContratos == null) {
			novosContratos = new NovosContratosWSTO();
		}
		return novosContratos;
	}
	public void setNovosContratos(NovosContratosWSTO novosContratos) {
		this.novosContratos = novosContratos;
	}
	
	public List<TagContratoWSTO> getListaTags() {
		if (listaTags == null) {
			listaTags = new ArrayList<TagContratoWSTO>();
		}

		return listaTags;
	}
	public void setListaTags(List<TagContratoWSTO> listaTags) {
		this.listaTags = listaTags;
	}
	
	
	
	
}
