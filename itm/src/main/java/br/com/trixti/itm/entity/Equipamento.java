package br.com.trixti.itm.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "itm_equipamento", schema = "public")
@AuditTable(value="itm_equipamento_historico")
public class Equipamento implements java.io.Serializable {

	private static final long serialVersionUID = -2127519180048329065L;

	@Id
	@SequenceGenerator(name="EQUIPAMENTO_ID_GENERATOR", sequenceName="itm_equipamento_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EQUIPAMENTO_ID_GENERATOR")
	private Integer id;
	
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="id_marca")
	private EquipamentoMarca marca;
	
	@ManyToOne
	@JoinColumn(name="id_modelo")
	private EquipamentoMarcaModelo modelo;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "equipamento")
	private List<ContratoEquipamento> contratoEquipamentos;
	
	@Column(name="numero_serie")
	private String numeroSerie;
	
	@Column(name="patrimonio")
	private String patrimonio;
	
	@Enumerated(EnumType.STRING)
	private UnidadeEquipamentoEnum unidade;
	
	@ManyToOne
	@JoinColumn(name="id_tipo")
	private EquipamentoTipo tipo;
	
	private BigDecimal quantidade;

	@Column(name="quantidade_minima")
	private BigDecimal quantidadeMinima;
	
	@Enumerated(EnumType.STRING)
	private StatusEquipamentoEnum status;
	
	private String descricao;
	
	@Column(name="data_ultima_atualizacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUltimaAtualizacao;
	
	@ManyToOne
	@JoinColumn(name="id_usuario_ultima_atualizacao")
	private Usuario usuarioUltimaAtualizacao;
	
	@Transient
	private List<EquipamentoHistorico> historico;
	
	@Transient
	private List<ContratoEquipamentoHistorico> contratosHistorico;
	
	@Transient
	private List<ContratoEquipamento> contratos;
	
	@Transient
	private BigDecimal quantidadeDisponivel;
	
	
	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getPatrimonio() {
		return patrimonio;
	}

	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}

	public Equipamento() {
	}

	public Equipamento(Integer id) {
		this.id = id;
	}

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

	public EquipamentoMarca getMarca() {
		return marca;
	}

	public void setMarca(EquipamentoMarca marca) {
		this.marca = marca;
	}

	public EquipamentoMarcaModelo getModelo() {
		return modelo;
	}

	public void setModelo(EquipamentoMarcaModelo modelo) {
		this.modelo = modelo;
	}

	public List<ContratoEquipamento> getContratoEquipamentos() {
		return contratoEquipamentos;
	}

	public void setContratoEquipamentos(List<ContratoEquipamento> contratoEquipamentos) {
		this.contratoEquipamentos = contratoEquipamentos;
	}

	public UnidadeEquipamentoEnum getUnidade() {
		return unidade;
	}

	public void setUnidade(UnidadeEquipamentoEnum unidade) {
		this.unidade = unidade;
	}

	public EquipamentoTipo getTipo() {
		return tipo;
	}

	public void setTipo(EquipamentoTipo tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public StatusEquipamentoEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEquipamentoEnum status) {
		this.status = status;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<EquipamentoHistorico> getHistorico() {
		return historico;
	}

	public void setHistorico(List<EquipamentoHistorico> historico) {
		this.historico = historico;
	}

	public Date getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public Usuario getUsuarioUltimaAtualizacao() {
		return usuarioUltimaAtualizacao;
	}

	public void setUsuarioUltimaAtualizacao(Usuario usuarioUltimaAtualizacao) {
		this.usuarioUltimaAtualizacao = usuarioUltimaAtualizacao;
	}

	public List<ContratoEquipamentoHistorico> getContratosHistorico() {
		return contratosHistorico;
	}

	public void setContratosHistorico(List<ContratoEquipamentoHistorico> contratosHistorico) {
		this.contratosHistorico = contratosHistorico;
	}

	public BigDecimal getQuantidadeMinima() {
		return quantidadeMinima;
	}

	public void setQuantidadeMinima(BigDecimal quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}

	public List<ContratoEquipamento> getContratos() {
		return contratos;
	}

	public void setContratos(List<ContratoEquipamento> contratos) {
		this.contratos = contratos;
	}

	public BigDecimal getQuantidadeDisponivel() {
		return quantidadeDisponivel;
	}

	public void setQuantidadeDisponivel(BigDecimal quantidadeDisponivel) {
		this.quantidadeDisponivel = quantidadeDisponivel;
	}

}
