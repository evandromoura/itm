package br.com.trixti.itm.entity;
// default package
// Generated 22/02/2017 14:34:07 by Hibernate Tools 4.3.4.Final

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.trixti.itm.enums.StatusRemessaEnum;

/**
 * ItmRemessa generated by hbm2java
 */

@Entity
@Table(name = "itm_remessa", schema = "public")
public class Remessa implements java.io.Serializable {

	private static final long serialVersionUID = 8924539309625088184L;

	@Id
	@SequenceGenerator(name="REMESSA_ID_GENERATOR", sequenceName="itm_remessa_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REMESSA_ID_GENERATOR") 
	@Column(name = "id", unique = true, nullable = false) 
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao")
	private Date dataCriacao;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_envio")
	private Date dataEnvio;
	
	private String codigo;
	
	private String arquivo;
	
	private String banco;
	 
	@Enumerated(EnumType.STRING)
	private StatusRemessaEnum status;
	
	@OneToMany(mappedBy="remessa")
	private List<Boleto> boletos;
	
	private BigDecimal valor;
	
	@Column(name="qtd_boleto_aberto")
	private Integer qtdBoletoAberto;

	@Column(name="qtd_boleto_fechado")
	private Integer qtdBoletoFechado;
	
	
	public Remessa(){
		
	}
	
	public Remessa(Integer id,Date dataCriacao,Date dataEnvio,String codigo,String banco,StatusRemessaEnum status, BigDecimal valor,Integer qtdBoletoAberto, Integer qtdBoletoFechado){
		setId(id);
		setDataCriacao(dataCriacao);
		setDataEnvio(dataEnvio);
		setCodigo(codigo);
		setBanco(banco);
		setStatus(status);
		setValor(valor);
		setQtdBoletoAberto(qtdBoletoAberto);
		setQtdBoletoFechado(qtdBoletoFechado);
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public StatusRemessaEnum getStatus() {
		return status;
	}

	public void setStatus(StatusRemessaEnum status) {
		this.status = status;
	}

	public List<Boleto> getBoletos() {
		return boletos;
	}

	public void setBoletos(List<Boleto> boletos) {
		this.boletos = boletos;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Integer getQtdBoletoAberto() {
		return qtdBoletoAberto;
	}

	public void setQtdBoletoAberto(Integer qtdBoletoAberto) {
		this.qtdBoletoAberto = qtdBoletoAberto;
	}

	public Integer getQtdBoletoFechado() {
		return qtdBoletoFechado;
	}

	public void setQtdBoletoFechado(Integer qtdBoletoFechado) {
		this.qtdBoletoFechado = qtdBoletoFechado;
	}
	
	

}
