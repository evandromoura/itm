package br.com.trixti.itm.entity;
// default package
// Generated 22/02/2017 14:34:07 by Hibernate Tools 4.3.4.Final

import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * ItmContratoProduto generated by hbm2java
 */
@Entity
@Table(name = "itm_contrato_produto", schema = "public")
public class ContratoProduto implements java.io.Serializable {

	private static final long serialVersionUID = -2460160021577572413L;

	@Id
	@SequenceGenerator(name="CONTRATO_PRODUTO_ID_GENERATOR", sequenceName="itm_contrato_produto_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRATO_PRODUTO_ID_GENERATOR") 
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_contrato")
	private Contrato contrato;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_produto")
	private Produto produto;
	
	private BigDecimal valor;
	
	@Column(name="valor_base")
	private BigDecimal valorBase;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_criacao")
	private Date dataCriacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_exclusao")
	private Date dataExclusao;
	
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_inicio")
	private Date dataInicio;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_fim")
	private Date dataFim;
	
	private Integer qtd;
	
	@Enumerated(EnumType.STRING)
	private TecnologiaEnum tecnologia;
	
	public ContratoProduto() {
	}

	public ContratoProduto(int id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Contrato getContrato() {
		return this.contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorBase() {
		return valorBase;
	}

	public void setValorBase(BigDecimal valorBase) {
		this.valorBase = valorBase;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	@Transient
	public boolean isAtivo(){
		return this.dataExclusao == null?true:false;
	}
	
	public void setAtivo(boolean valor){
		
	}
	
	public boolean isVigente(){
		return (getDataInicio() != null && getDataInicio().compareTo(new Date()) <= 0) && 
				(getDataFim() != null && getDataFim().compareTo(new Date()) >= 0) &&
				getDataExclusao() == null;
	}

	public TecnologiaEnum getTecnologia() {
		return tecnologia;
	}

	public void setTecnologia(TecnologiaEnum tecnologia) {
		this.tecnologia = tecnologia;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}
	
	@Transient
	public BigDecimal getValorTotal(){
		return getValor().multiply(new BigDecimal(getQtd()));
	}

}
