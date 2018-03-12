package br.com.trixti.itm.entity;
// default package
// Generated 22/02/2017 14:34:07 by Hibernate Tools 4.3.4.Final

import java.util.Date;
import java.util.List;

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

//@Audited
@Entity
@Table(name = "itm_arquivo_sici", schema = "public")
//@AuditTable(value="itm_arquivo_sici_historico")
public class ArquivoSici implements java.io.Serializable {

	private static final long serialVersionUID = 5179058518692932631L;

	@Id
	@SequenceGenerator(name="ARQUIVO_SICI_ID_GENERATOR", sequenceName="itm_arquivo_sici_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ARQUIVO_SICI_ID_GENERATOR") 
	private Integer id;
	
	@Column(name="nome_arquivo")
	private String nomeArquivo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	
	private String xml;
	
	@OneToMany(mappedBy="arquivoSici")
	private List<MovimentacaoFinanceira> movimentacoes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<MovimentacaoFinanceira> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(List<MovimentacaoFinanceira> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

}
