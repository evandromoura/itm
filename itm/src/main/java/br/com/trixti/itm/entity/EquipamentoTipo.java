package br.com.trixti.itm.entity;
// default package
// Generated 22/02/2017 14:34:07 by Hibernate Tools 4.3.4.Final

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * ItmGrupo generated by hbm2java
 */
@Entity
@Table(name = "itm_equipamento_tipo", schema = "public")
public class EquipamentoTipo implements java.io.Serializable {

	private static final long serialVersionUID = -8140969307057217925L;

	@Id
	@SequenceGenerator(name="EQUIPAMENTO_TIPO_ID_GENERATOR", sequenceName="itm_equipamento_tipo_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EQUIPAMENTO_TIPO_ID_GENERATOR") 
	private Integer id;
	
	private String nome;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}