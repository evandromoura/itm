package br.com.trixti.itm.entity;
// default package

import java.math.BigDecimal;

// Generated 22/02/2017 14:34:07 by Hibernate Tools 4.3.4.Final

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * ItmContratoEquipamento generated by hbm2java
 */
@Audited
@Entity
@Table(name = "itm_contrato_equipamento", schema = "public")
@AuditTable(value="itm_contrato_equipamento_historico")
public class ContratoEquipamento implements java.io.Serializable {

	private static final long serialVersionUID = -6823889197930426210L;

	@Id
	@SequenceGenerator(name = "CONTRATO_EQUIPAMENTO_ID_GENERATOR", sequenceName = "itm_contrato_equipamento_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRATO_EQUIPAMENTO_ID_GENERATOR")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_contrato")
	private Contrato contrato;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_equipamento")
	private Equipamento equipamento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao")
	private Date dataCriacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_exclusao")
	private Date dateExclusao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_instalacao")
	private Date dataInstalacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_retirada")
	private Date dataRetirada;

	@Column(name = "numero_serie")
	private String numeroSerie;

	@Column(name = "patrimonio")
	private String patrimonio;

	@Column(name = "protocolo_instalacao")
	private String protocoloInstalacao;

	@Column(name = "protocolo_retirada")
	private String protocoloRetirada;
	
	private BigDecimal quantidade;

	public ContratoEquipamento() {
	}

	@Transient
	private List<ContratoEquipamento> listaContratoEquipamento;

	public ContratoEquipamento(Integer id) {
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

	public Equipamento getEquipamento() {
		return this.equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDateExclusao() {
		return dateExclusao;
	}

	public void setDateExclusao(Date dateExclusao) {
		this.dateExclusao = dateExclusao;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public Date getDataInstalacao() {
		return dataInstalacao;
	}

	public void setDataInstalacao(Date dataInstalacao) {
		this.dataInstalacao = dataInstalacao;
	}

	public Date getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(Date dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

	public String getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}

	public String getProtocoloInstalacao() {
		return protocoloInstalacao;
	}

	public void setProtocoloInstalacao(String protocoloInstalacao) {
		this.protocoloInstalacao = protocoloInstalacao;
	}

	public String getProtocoloRetirada() {
		return protocoloRetirada;
	}

	public void setProtocoloRetirada(String protocoloRetirada) {
		this.protocoloRetirada = protocoloRetirada;
	}

	public List<ContratoEquipamento> getListaContratoEquipamento() {
		return listaContratoEquipamento;
	}

	public void setListaContratoEquipamento(List<ContratoEquipamento> listaContratoEquipamento) {
		this.listaContratoEquipamento = listaContratoEquipamento;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

}
