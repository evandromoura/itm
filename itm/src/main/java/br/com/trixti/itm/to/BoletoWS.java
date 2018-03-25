package br.com.trixti.itm.to;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.trixti.itm.util.UtilData;

@XmlRootElement
public class BoletoWS {

	private Integer id;
	private Date dataVencimento;
	private BigDecimal valor;
	private Integer qtdLancamentos;
	private List<BoletoLancamentoWS> lancamentos;
	
	
	public BoletoWS(Integer id, Date data, BigDecimal valor,List<BoletoLancamentoWS> lancamentos){
		setValor(valor);
		setDataVencimento(data);
		setLancamentos(lancamentos);
		setId(id);
		if(lancamentos != null){
			setQtdLancamentos(lancamentos.size());
		}
	}
	
	public String getDataVencimento() {
		UtilData utilData = new UtilData();
		return utilData.formatDate(this.dataVencimento, "dd/MM/yyyy");
	}
	
	
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public List<BoletoLancamentoWS> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<BoletoLancamentoWS> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getQtdLancamentos() {
		return qtdLancamentos;
	}


	public void setQtdLancamentos(Integer qtdLancamentos) {
		this.qtdLancamentos = qtdLancamentos;
	}
	
	
}
