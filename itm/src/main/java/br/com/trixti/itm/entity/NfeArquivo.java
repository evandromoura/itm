package br.com.trixti.itm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "itm_nfe_arquivo", schema = "public")
public class NfeArquivo implements java.io.Serializable {

	private static final long serialVersionUID = 5179058518692932631L;

	@Id
	@SequenceGenerator(name="NFE_ARQUIVO_ID_GENERATOR", sequenceName="itm_nfe_arquivo_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NFE_ARQUIVO_ID_GENERATOR") 
	private Integer id;

	private String conteudo;
	
	@ManyToOne
	@JoinColumn(name="id_nfe")
	private Nfe nfe;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Nfe getNfe() {
		return nfe;
	}

	public void setNfe(Nfe nfe) {
		this.nfe = nfe;
	}
	
}
