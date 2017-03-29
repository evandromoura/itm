package br.com.trixti.itm.entity;
// default package
// Generated 22/02/2017 14:34:07 by Hibernate Tools 4.3.4.Final

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

/**
 * ItmBoleto generated by hbm2java
 */
@Entity
@Table(name = "itm_boleto_lancamento", schema = "public")
public class BoletoLancamento implements java.io.Serializable {


	private static final long serialVersionUID = -8450695550671578132L;

	@Id
	@SequenceGenerator(name="BOLETO_LANCAMENTO_ID_GENERATOR", sequenceName="itm_boleto_lancamento_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BOLETO_LANCAMENTO_ID_GENERATOR") 
	@Column(name = "id", unique = true, nullable = false) 
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_boleto")
	private Boleto boleto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_lancamento")
	private ContratoLancamento contratoLancamento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boleto getBoleto() {
		return boleto;
	}

	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}

	public ContratoLancamento getContratoLancamento() {
		return contratoLancamento;
	}

	public void setContratoLancamento(ContratoLancamento contratoLancamento) {
		this.contratoLancamento = contratoLancamento;
	}
	
	

}
