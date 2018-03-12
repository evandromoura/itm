package br.com.trixti.itm.entity;
// default package
// Generated 22/02/2017 14:34:07 by Hibernate Tools 4.3.4.Final

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//@Audited
@Entity
@Table(name = "itm_centro_custo", schema = "public")
//@AuditTable(value="itm_centro_custo_historico")
public class CentroCusto implements java.io.Serializable {

	
	private static final long serialVersionUID = 7071443303059514555L;

	@Id
	@SequenceGenerator(name="CENTRO_CUSTO_ID_GENERATOR", sequenceName="itm_centro_custo_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CENTRO_CUSTO_ID_GENERATOR") 
	private Integer id;
	
	private String codigo;
	private String nome;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	
	

}
