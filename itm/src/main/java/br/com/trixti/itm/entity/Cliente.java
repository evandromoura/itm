package br.com.trixti.itm.entity;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import org.hibernate.envers.NotAudited;

import com.google.gson.annotations.Expose;

import br.com.trixti.itm.enums.TipoPessoaEnum;
import br.com.trixti.itm.to.PeriodoTO;

@Audited
@Entity
@Table(name = "itm_cliente", schema = "public")
@AuditTable(value="itm_cliente_historico") 
public class Cliente implements java.io.Serializable {

	private static final long serialVersionUID = -3714699261681620589L;
	
	@Id
	@SequenceGenerator(name="CLIENTE_ID_GENERATOR", sequenceName="itm_cliente_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CLIENTE_ID_GENERATOR") 
	@Expose
	private Integer id;
 	
	@Expose
	private String nome;
	
	@Expose
	private String email;
	
	@Expose
	private String endereco;

	@Expose
	@Column(name = "telefone_celular")
	private String telefoneCelular;
	
	@Column(name = "telefone_fixo")
	private String telefoneFixo;
	
	@Expose
	@Column(name = "cpf_cnpj")
	private String cpfCnpj;
	
	@Expose
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pessoa")
	private TipoPessoaEnum tipoPessoa;
	
	private String cep;
	
	private String bairro;

	@NotAudited
	@ManyToOne
	@JoinColumn(name="uf")
	private Uf uf;
	
	
	@NotAudited
	@ManyToOne
	@JoinColumn(name="id_cidade")
	private Cidade cidade;
	
	@Column(name = "numero_endereco")
	private String numeroEndereco;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao")
	private Date dataCriacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_exclusao", length = 29)
	private Date dataExclusao;
	
	@NotAudited
	@OneToMany(mappedBy="cliente")
	private List<Contrato> contratos;
	
	@ManyToOne
	@JoinColumn(name="id_usuario_ultima_atualizacao")
	private Usuario usuarioUltimaAtualizacao;
	
	private String senha;
	
	@NotAudited
	@OneToMany(mappedBy="cliente",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<ClienteTag> clienteTags;
	
	@Transient
	private String login;
	
	@Transient
	private Grupo grupo;

	@Transient
	private Produto produto;
	
	@Transient
	private StatusContrato statusContrato;
	
	@Transient
	private String[] tags;
	
	@Transient
	private StatusContrato[] status;
	
	@Transient
	private boolean semPagamento;

	@Transient
	private boolean comPagamento;
	
	@Transient
	private boolean semContrato;

	@Transient
	private boolean comContrato;
	
	@Transient
	private boolean semProduto;	
	
	@Transient
	private boolean semTag;
	
	@Transient
	private boolean comPromessa;
	
	@Transient
	private PeriodoTO periodoCadastroContrato;
	
	public Cliente() {
	}

	public Cliente(int id) {
		this.id = id;
	}
	
	public Cliente(int id,String cpfCnpj,String nome) {
		this.id = id;
	}
	
	public Cliente(int id,String nome,String email,Date dataCriacao, String cpfCnpj, String telefoneCelular) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.dataCriacao = dataCriacao;
		this.cpfCnpj = cpfCnpj;
		this.telefoneCelular = telefoneCelular;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefoneCelular() {
		return this.telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public String getTelefoneFixo() {
		return this.telefoneFixo;
	}

	public void setTelefoneFixo(String telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	
	public String getCpfCnpj() {
		return this.cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public TipoPessoaEnum getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoaEnum tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Date getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataExclusao() {
		return this.dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(String numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}

	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		this.uf = uf;
	}

	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario getUsuarioUltimaAtualizacao() {
		return usuarioUltimaAtualizacao;
	}

	public void setUsuarioUltimaAtualizacao(Usuario usuarioUltimaAtualizacao) {
		this.usuarioUltimaAtualizacao = usuarioUltimaAtualizacao;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public StatusContrato getStatusContrato() {
		return statusContrato;
	}

	public void setStatusContrato(StatusContrato statusContrato) {
		this.statusContrato = statusContrato;
	}

	public List<ClienteTag> getClienteTags() {
		return clienteTags;
	}

	public void setClienteTags(List<ClienteTag> clienteTags) {
		this.clienteTags = clienteTags;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public boolean isSemPagamento() {
		return semPagamento;
	}

	public void setSemPagamento(boolean semPagamento) {
		this.semPagamento = semPagamento;
	}

	public boolean isSemTag() {
		return semTag;
	}

	public void setSemTag(boolean semTag) {
		this.semTag = semTag;
	}

	public PeriodoTO getPeriodoCadastroContrato() {
		if (periodoCadastroContrato == null) {
			periodoCadastroContrato = new PeriodoTO();
		}
		return periodoCadastroContrato;
	}

	public void setPeriodoCadastroContrato(PeriodoTO periodoCadastroContrato) {
		this.periodoCadastroContrato = periodoCadastroContrato;
	}

	public boolean isComPagamento() {
		return comPagamento;
	}

	public void setComPagamento(boolean comPagamento) {
		this.comPagamento = comPagamento;
	}

	public StatusContrato[] getStatus() {
		return status;
	}

	public void setStatus(StatusContrato[] status) {
		this.status = status;
	}

	public boolean isSemContrato() {
		return semContrato;
	}

	public void setSemContrato(boolean semContrato) {
		this.semContrato = semContrato;
	}

	public boolean isComContrato() {
		return comContrato;
	}

	public void setComContrato(boolean comContrato) {
		this.comContrato = comContrato;
	}

	public boolean isSemProduto() {
		return semProduto;
	}

	public void setSemProduto(boolean semProduto) {
		this.semProduto = semProduto;
	}

	public boolean isComPromessa() {
		return comPromessa;
	}

	public void setComPromessa(boolean comPromessa) {
		this.comPromessa = comPromessa;
	}
	
}
