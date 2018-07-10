package br.com.trixti.itm.entity;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Audited
@Entity
@Table(name = "itm_contrato", schema = "public")
@AuditTable(value="itm_contrato_historico") 
public class Contrato implements java.io.Serializable {

	private static final long serialVersionUID = -3714699261681620589L;
	
	@Id
	@SequenceGenerator(name="CONTRATO_ID_GENERATOR", sequenceName="itm_contrato_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTRATO_ID_GENERATOR")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private Cliente cliente;
	
	@NotAudited
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_credito_inicial")
	private Date dataCreditoInicial;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_cancelamento")
	private Date dataCancelamento;
	
	@NotAudited
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato",cascade=CascadeType.REMOVE,orphanRemoval=true)
	private List<ContratoEquipamento> contratoEquipamentos;
	
	@NotAudited
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato",cascade=CascadeType.REMOVE,orphanRemoval=true)
	private List<ContratoProduto> contratoProdutos;
	
	@NotAudited
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato",cascade={CascadeType.ALL},orphanRemoval=true)
	private List<ContratoGrupo> contratoGrupos;
	
	@NotAudited
	@OrderBy("dataVencimento desc")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato",cascade=CascadeType.REMOVE,orphanRemoval=true)
	private List<Boleto> boletos;
	
	@NotAudited
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato",cascade=CascadeType.REMOVE,orphanRemoval=true)
	private List<ContratoAutenticacao> autenticacoes;

	@NotAudited
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato",cascade=CascadeType.REMOVE,orphanRemoval=true)
	private List<ContratoNotificacao> notificacoes;
	
	@NotAudited
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contrato",cascade=CascadeType.REMOVE,orphanRemoval=true)
	private List<ContratoAnexo> anexos;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_para_bloqueio")
	private Date dataParaBloqueio;
	
	@Transient
	private List<Radacct> listaUtilizacao;
	
	@Transient
	private List<ContratoLancamento> lancamentos;
	
	@Enumerated(EnumType.STRING)
	private StatusContrato status;
	
	@ManyToOne
	@JoinColumn(name="id_usuario_alteracao")
	private Usuario usuarioAlteracao;
	
	@Transient
	private boolean criarLancamentoCredito;
	
	@Transient
	private boolean geraMultaCancelamento = true;

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

	public List<ContratoAutenticacao> getAutenticacoes() {
		return autenticacoes;
	}

	public void setAutenticacoes(List<ContratoAutenticacao> autenticacoes) {
		this.autenticacoes = autenticacoes;
	}

	public StatusContrato getStatus() {
		return status;
	}

	public void setStatus(StatusContrato status) {
		this.status = status;
	}

	public Date getDataParaBloqueio() {
		return dataParaBloqueio;
	}

	public void setDataParaBloqueio(Date dataParaBloqueio) {
		this.dataParaBloqueio = dataParaBloqueio;
	}

	public List<Radacct> getListaUtilizacao() {
		return listaUtilizacao;
	}

	public void setListaUtilizacao(List<Radacct> listaUtilizacao) {
		this.listaUtilizacao = listaUtilizacao;
	}

	public Usuario getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}

	public Date getDataCreditoInicial() {
		return dataCreditoInicial;
	}

	public void setDataCreditoInicial(Date dataCreditoInicial) {
		this.dataCreditoInicial = dataCreditoInicial;
	}

	public List<ContratoNotificacao> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<ContratoNotificacao> notificacoes) {
		this.notificacoes = notificacoes;
	}

	public boolean isCriarLancamentoCredito() {
		return criarLancamentoCredito;
	}

	public void setCriarLancamentoCredito(boolean criarLancamentoCredito) {
		this.criarLancamentoCredito = criarLancamentoCredito;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public boolean isGeraMultaCancelamento() {
		return geraMultaCancelamento;
	}

	public void setGeraMultaCancelamento(boolean geraMultaCancelamento) {
		this.geraMultaCancelamento = geraMultaCancelamento;
	}

	public List<ContratoAnexo> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<ContratoAnexo> anexos) {
		this.anexos = anexos;
	}
	
}
