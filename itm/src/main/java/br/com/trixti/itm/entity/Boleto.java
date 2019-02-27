package br.com.trixti.itm.entity;
// default package
// Generated 22/02/2017 14:34:07 by Hibernate Tools 4.3.4.Final

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Audited
@Entity
@Table(name = "itm_boleto", schema = "public")
@AuditTable(value="itm_boleto_historico") 
@XmlRootElement
public class Boleto implements java.io.Serializable {

	private static final long serialVersionUID = 8924539309625088184L;

	@Id
	@SequenceGenerator(name="BOLETO_ID_GENERATOR", sequenceName="itm_boleto_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BOLETO_ID_GENERATOR") 
	@Column(name = "id", unique = true, nullable = false) 
	private Integer id;
	
	private BigDecimal valor;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_vencimento", length = 13)
	private Date dataVencimento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", length = 29)
	private Date dataCriacao;
	
	
	@NotAudited
	@OneToMany(mappedBy="boleto",cascade=CascadeType.ALL,orphanRemoval=true)
	@JsonIgnore
	private List<BoletoLancamento> lancamentos;
	
	@Enumerated(EnumType.STRING)
	private StatusBoletoEnum status;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_contrato")
	private Contrato contrato;
	
	@Column(name="numero_documento")
	private String numeroDocumento;
	
	@NotAudited
	@ManyToOne
	@JoinColumn(name="id_remessa")
	@JsonIgnore
	private Remessa remessa;
	
	@NotAudited
	@ManyToOne
	@JoinColumn(name="id_nfe")
	@JsonIgnore
	private Nfe nfe;
	
	@NotAudited
	@ManyToOne
	@JoinColumn(name="id_retorno")
	@JsonIgnore
	private Retorno retorno;
	
	@Column(name="valor_pago")
	private BigDecimal valorPago;
	
	@Column(name="nosso_numero")
	private String nossoNumero;
	
	@Column(name="digito_nosso_numero")
	private String digitoNossoNumero;
	
	@Column(name="nosso_numero_completo")
	private String nossoNumeroCompleto;
	
	@Column(name="data_pagamento")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataPagamento;
	
	@Column(name="data_notificacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataNotificacao;
	
	@Column(name="data_sms")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataSms;
	
	@Transient
	@JsonIgnore
	private long diferencaDias;

	public Boleto() {
	}
	
	public Boleto(BigDecimal valor,Date dataVencimento,
				Date dataCriacao,StatusBoletoEnum status,Contrato contrato,
				BoletoLancamento... lancamentos) {
		
		setValor(valor);
		setDataVencimento(dataVencimento);
		setDataCriacao(dataCriacao);
		setStatus(status);
		setContrato(contrato);
		setLancamentos(Arrays.asList(lancamentos));
	}

	public Boleto(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	
	public Date getDataVencimento() {
		return this.dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public List<BoletoLancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<BoletoLancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public StatusBoletoEnum getStatus() {
		return status;
	}

	public void setStatus(StatusBoletoEnum status) {
		this.status = status;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Remessa getRemessa() {
		return remessa;
	}

	public void setRemessa(Remessa remessa) {
		this.remessa = remessa;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public String getDigitoNossoNumero() {
		return digitoNossoNumero;
	}

	public void setDigitoNossoNumero(String digitoNossoNumero) {
		this.digitoNossoNumero = digitoNossoNumero;
	}

	public String getNossoNumeroCompleto() {
		return nossoNumeroCompleto;
	}

	public void setNossoNumeroCompleto(String nossoNumeroCompleto) {
		this.nossoNumeroCompleto = nossoNumeroCompleto;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Retorno getRetorno() {
		return retorno;
	}

	public void setRetorno(Retorno retorno) {
		this.retorno = retorno;
	}

	public Date getDataNotificacao() {
		return dataNotificacao;
	}

	public void setDataNotificacao(Date dataNotificacao) {
		this.dataNotificacao = dataNotificacao;
	}

	public Date getDataSms() {
		return dataSms;
	}

	public void setDataSms(Date dataSms) {
		this.dataSms = dataSms;
	}

	public long getDiferencaDias() {
		return diferencaDias;
	}

	public void setDiferencaDias(long diferencaDias) {
		this.diferencaDias = diferencaDias;
	}

}
