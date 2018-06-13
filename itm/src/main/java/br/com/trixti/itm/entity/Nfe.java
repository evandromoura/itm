package br.com.trixti.itm.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "itm_nfe", schema = "public")
public class Nfe implements java.io.Serializable {

	private static final long serialVersionUID = 5179058518692932631L;

	@Id
	@SequenceGenerator(name="NFE_ID_GENERATOR", sequenceName="itm_nfe_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NFE_ID_GENERATOR") 
	private Integer id;

	@Column(name="nome")
	private String nome;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	private String mes;
	private String ano;
	
	@OneToMany(mappedBy="nfe",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<NfeArquivo> arquivos;
	
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
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public List<NfeArquivo> getArquivos() {
		return arquivos;
	}
	public void setArquivos(List<NfeArquivo> arquivos) {
		this.arquivos = arquivos;
	}
	
}
