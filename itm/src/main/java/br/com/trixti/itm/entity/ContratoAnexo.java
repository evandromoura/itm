package br.com.trixti.itm.entity;
// default package
// Generated 22/02/2017 14:34:07 by Hibernate Tools 4.3.4.Final

import java.util.Date;

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

/**
 * ItmContratoAnexo generated by hbm2java
 */
@Entity
@Table(name = "itm_contrato_anexo", schema = "public")
public class ContratoAnexo implements java.io.Serializable {

	private static final long serialVersionUID = -6823889197930426210L;

	@Id
	@SequenceGenerator(name="CONTRATO_ANEXO_ID_GENERATOR", sequenceName="itm_contrato_anexo_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRATO_ANEXO_ID_GENERATOR")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_contrato")
	private Contrato contrato;

	private String nome;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_criacao")
	private Date dataCriacao;
	
	public ContratoAnexo(){
		
	}
	public ContratoAnexo(Integer id, String nome, Date dataCriacao){
		setId(id);
		setNome(nome);
		setDataCriacao(dataCriacao);
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getConteudo() {
		return conteudo;
	}
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	private String conteudo;

	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

}