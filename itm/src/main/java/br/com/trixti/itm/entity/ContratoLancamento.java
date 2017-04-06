package br.com.trixti.itm.entity;
// default package
// Generated 22/02/2017 14:34:07 by Hibernate Tools 4.3.4.Final

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * ItmContratoLancamento generated by hbm2java
 */
@Entity
@Table(name = "itm_contrato_lancamento", schema = "public")
public class ContratoLancamento implements java.io.Serializable {

	private static final long serialVersionUID = -6823889197930426210L;

	@Id
	@SequenceGenerator(name="CONTRATO_LANCAMENTO_ID_GENERATOR", sequenceName="itm_contrato_lancamento_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRATO_LANCAMENTO_ID_GENERATOR")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_contrato")
	private Contrato contrato;
	
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_lancamento")
	private TipoLancamentoEnum tipoLancamento;
	
	private BigDecimal valor;
	
	private String descricao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_lancamento")
	private Date dataLancamento;
	
	@Enumerated(EnumType.STRING)
	private StatusLancamentoEnum status;
	
	@OneToMany(mappedBy="contratoLancamento")
	private List<BoletoLancamento> boletoLancamentos;
	
	@Transient
	private boolean geraBoleto;
	
	@Transient
	private boolean selecionado;
	
	public ContratoLancamento(){}
	
	public ContratoLancamento(String descricao,Contrato contrato,
				BigDecimal valor,TipoLancamentoEnum tipoLancamento,Date dataLancamento,
				StatusLancamentoEnum status){
		setDescricao(descricao);
		setContrato(contrato);
		setValor(valor);
		setTipoLancamento(tipoLancamento);
		setDataLancamento(dataLancamento);
		setStatus(status);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public TipoLancamentoEnum getTipoLancamento() {
		return tipoLancamento;
	}

	public void setTipoLancamento(TipoLancamentoEnum tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public StatusLancamentoEnum getStatus() {
		return status;
	}

	public void setStatus(StatusLancamentoEnum status) {
		this.status = status;
	}

	public List<BoletoLancamento> getBoletoLancamentos() {
		return boletoLancamentos;
	}

	public void setBoletoLancamentos(List<BoletoLancamento> boletoLancamentos) {
		this.boletoLancamentos = boletoLancamentos;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isGeraBoleto() {
		return geraBoleto;
	}

	public void setGeraBoleto(boolean geraBoleto) {
		this.geraBoleto = geraBoleto;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}
	
	@Transient
	public String getUid(){
		return UUID.randomUUID().toString();
	}
	
	
	

}
