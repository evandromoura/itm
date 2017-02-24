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
 * ItmClienteGrupo generated by hbm2java
 */
@Entity
@Table(name = "itm_cliente_grupo", schema = "public")
public class ClienteGrupo implements java.io.Serializable {

	private static final long serialVersionUID = -7925742180350414970L;

	@Id
	@SequenceGenerator(name="CLIENTE_GRUPO_ID_GENERATOR", sequenceName="itm_cliente_grupo_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CLIENTE_GRUPO_ID_GENERATOR")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_grupo")
	private Grupo grupo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_criacao")
	private Date dataCriacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_exclusao")
	private Date dateExclusao;
	
	

	public ClienteGrupo() {
	}

	public ClienteGrupo(Integer id) {
		this.id = id;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
	public Grupo getGrupo() {
		return this.grupo;
	}

	public void setGrupo(Grupo itmGrupo) {
		this.grupo = itmGrupo;
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

}
