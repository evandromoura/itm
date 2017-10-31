package br.com.trixti.itm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "itm_tag", schema = "public")
public class Tag implements java.io.Serializable {

	private static final long serialVersionUID = 22775385501094990L;
	
	@Id
	@SequenceGenerator(name="TAG_ID_GENERATOR", sequenceName="itm_tag_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAG_ID_GENERATOR") 
	private Integer id;
	
	private String nome;

	private String style;
	
	@Transient
	private boolean selecionado;

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

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

}
