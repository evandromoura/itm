package br.com.trixti.itm.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "itm_cidade", schema = "public")
public class Cidade implements java.io.Serializable {

	private static final long serialVersionUID = 22775385501094990L;
	
	@Id
	private Integer id;
	
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="uf")
	private Uf uf;

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

	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		this.uf = uf;
	}

}
