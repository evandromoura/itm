package br.com.trixti.itm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "itm_equipamento_historico", schema = "public")
public class EquipamentoHistorico implements java.io.Serializable {

	private static final long serialVersionUID = -885644173049243773L;

	@EmbeddedId
	private EquipamentoHistoricoEmbedId pk;
	
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="id_marca")
	private EquipamentoMarca marca;
	
	@ManyToOne
	@JoinColumn(name="id_modelo")
	private EquipamentoMarcaModelo modelo;
	
	@Column(name="numero_serie")
	private String numeroSerie;
	
	@Column(name="patrimonio")
	private String patrimonio;
	
	@Enumerated(EnumType.STRING)
	private UnidadeEquipamentoEnum unidade;
	
	@ManyToOne
	@JoinColumn(name="id_tipo")
	private EquipamentoTipo tipo;
	
	private Integer quantidade;
	
	@Enumerated(EnumType.STRING)
	private StatusEquipamentoEnum status;
	
	private String descricao;
	
	@Column(name="data_ultima_atualizacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUltimaAtualizacao;
	
	@ManyToOne
	@JoinColumn(name="id_usuario_ultima_atualizacao")
	private Usuario usuarioUltimaAtualizacao;

	public EquipamentoHistoricoEmbedId getPk() {
		return pk;
	}

	public void setPk(EquipamentoHistoricoEmbedId pk) {
		this.pk = pk;
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

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
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
	

}
