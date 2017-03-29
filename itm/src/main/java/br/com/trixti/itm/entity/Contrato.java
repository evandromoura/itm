package br.com.trixti.itm.entity;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "itm_contrato", schema = "public")
public class Contrato implements java.io.Serializable {

	private static final long serialVersionUID = -3714699261681620589L;
	
	@Id
	@SequenceGenerator(name="CONTRATO_ID_GENERATOR", sequenceName="itm_contrato_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRATO_ID_GENERATOR") 
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="id_conta_corrente")
 	private ContaCorrente contaCorrente;
	
	private String identificador;
	
	@Column(name="gera_boleto")
	private boolean geraBoleto;
	
	@Column(name="dia_mes_vencimento")
	private Integer diaMesVencimento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_criacao")
	private Date dataCriacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_exclusao")
	private Date dataExclusao;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato",cascade=CascadeType.REMOVE,orphanRemoval=true)
	private List<ContratoEquipamento> contratoEquipamentos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato",cascade=CascadeType.REMOVE,orphanRemoval=true)
	private List<ContratoProduto> contratoProdutos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato",cascade=CascadeType.REMOVE,orphanRemoval=true)
	private List<ContratoGrupo> contratoGrupos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato",cascade=CascadeType.REMOVE,orphanRemoval=true)
	private List<Boleto> boletos;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato",cascade=CascadeType.REMOVE,orphanRemoval=true)
	private List<ContratoLancamento> lancamentos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ContaCorrente getContaCorrente() {
		return contaCorrente;
	}

	public void setContaCorrente(ContaCorrente contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public boolean isGeraBoleto() {
		return geraBoleto;
	}

	public void setGeraBoleto(boolean geraBoleto) {
		this.geraBoleto = geraBoleto;
	}

	public Integer getDiaMesVencimento() {
		return diaMesVencimento;
	}

	public void setDiaMesVencimento(Integer diaMesVencimento) {
		this.diaMesVencimento = diaMesVencimento;
	}

	public List<ContratoEquipamento> getContratoEquipamentos() {
		return contratoEquipamentos;
	}

	public void setContratoEquipamentos(List<ContratoEquipamento> contratoEquipamentos) {
		this.contratoEquipamentos = contratoEquipamentos;
	}

	public List<ContratoProduto> getContratoProdutos() {
		return contratoProdutos;
	}

	public void setContratoProdutos(List<ContratoProduto> contratoProdutos) {
		this.contratoProdutos = contratoProdutos;
	}

	public List<ContratoGrupo> getContratoGrupos() {
		return contratoGrupos;
	}

	public void setContratoGrupos(List<ContratoGrupo> contratoGrupos) {
		this.contratoGrupos = contratoGrupos;
	}

	public List<Boleto> getBoletos() {
		return boletos;
	}

	public void setBoletos(List<Boleto> boletos) {
		this.boletos = boletos;
	}

	public List<ContratoLancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<ContratoLancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}
	
}