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

import org.hibernate.envers.NotAudited;


@Entity
@Table(name = "itm_boleto", schema = "public")
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
	private List<BoletoLancamento> lancamentos;
	
	@Enumerated(EnumType.STRING)
	private StatusBoletoEnum status;
	
	@ManyToOne
	@JoinColumn(name="id_contrato")
	private Contrato contrato;
	
	@Column(name="numero_documento")
	private String numeroDocumento;
	
	@ManyToOne
	@JoinColumn(name="id_remessa")
	private Remessa remessa;

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

}
