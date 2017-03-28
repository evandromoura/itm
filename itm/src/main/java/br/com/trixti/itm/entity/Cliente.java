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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.trixti.itm.enums.TipoPessoaEnum;

@Entity
@Table(name = "itm_cliente", schema = "public")
public class Cliente implements java.io.Serializable {

	private static final long serialVersionUID = -3714699261681620589L;
	
	@Id
	@SequenceGenerator(name="CLIENTE_ID_GENERATOR", sequenceName="itm_cliente_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CLIENTE_ID_GENERATOR") 
	private Integer id;
 	
	private String nome;
	private String email;
	private String endereco;

	@Column(name="dia_mes_vencimento")
	private Integer diaMesVencimento;
	
	@Column(name = "telefone_celular")
	private String telefoneCelular;
	
	@Column(name = "telefone_fixo")
	private String telefoneFixo;
	
	@Column(name = "cpf_cnpj")
	private String cpfCnpj;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_pessoa")
	private TipoPessoaEnum tipoPessoa;
	
	private String cep;
	
	private String bairro;

	private String uf;
	
	@Column(name = "numero_endereco")
	private String numeroEndereco;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao")
	private Date dataCriacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_exclusao", length = 29)
	private Date dataExclusao;
	
	@ManyToOne
	@JoinColumn(name="id_conta_corrente_boleto")
	private ContaCorrente contaCorrenteBoleto;
	
	@Column(name="gera_boleto")
	private Boolean geraBoleto;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente",cascade=CascadeType.REMOVE,orphanRemoval=true)
	private List<ClienteEquipamento> clienteEquipamentos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente",cascade=CascadeType.REMOVE,orphanRemoval=true)
	private List<ClienteProduto> clienteProdutos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente",cascade=CascadeType.REMOVE,orphanRemoval=true)
	private List<ClienteGrupo> clienteGrupos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente",cascade=CascadeType.REMOVE,orphanRemoval=true)
	private List<Boleto> boletos;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente",cascade=CascadeType.REMOVE,orphanRemoval=true)
	private List<ClienteLancamento> lancamentos;

	public Cliente() {
	}

	public Cliente(int id) {
		this.id = id;
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

	
	public List<ClienteEquipamento> getClienteEquipamentos() {
		return this.clienteEquipamentos;
	}

	public List<ClienteProduto> getClienteProdutos() {
		return this.clienteProdutos;
	}

	public List<ClienteGrupo> getClienteGrupos() {
		return this.clienteGrupos;
	}

	public List<Boleto> getBoletos() {
		return this.boletos;
	}

	public void setBoletos(List<Boleto> boletos) {
		this.boletos = boletos;
	}

	public void setClienteEquipamentos(List<ClienteEquipamento> clienteEquipamentos) {
		this.clienteEquipamentos = clienteEquipamentos;
	}

	public void setClienteProdutos(List<ClienteProduto> clienteProdutos) {
		this.clienteProdutos = clienteProdutos;
	}

	public void setClienteGrupos(List<ClienteGrupo> clienteGrupos) {
		this.clienteGrupos = clienteGrupos;
	}

	public Integer getDiaMesVencimento() {
		return diaMesVencimento;
	}

	public void setDiaMesVencimento(Integer diaMesVencimento) {
		this.diaMesVencimento = diaMesVencimento;
	}

	public List<ClienteLancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<ClienteLancamento> lancamentos) {
		this.lancamentos = lancamentos;
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

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public ContaCorrente getContaCorrenteBoleto() {
		return contaCorrenteBoleto;
	}

	public void setContaCorrenteBoleto(ContaCorrente contaCorrenteBoleto) {
		this.contaCorrenteBoleto = contaCorrenteBoleto;
	}

	public Boolean getGeraBoleto() {
		return geraBoleto;
	}

	public void setGeraBoleto(Boolean geraBoleto) {
		this.geraBoleto = geraBoleto;
	}
	

}
