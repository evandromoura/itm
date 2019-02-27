package br.com.trixti.itm.entity;
// default package
// Generated 22/02/2017 14:34:07 by Hibernate Tools 4.3.4.Final

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * ItmParametro generated by hbm2java
 */
@Entity
@Table(name = "itm_parametro", schema = "public")
public class Parametro implements java.io.Serializable {


	private static final long serialVersionUID = 8924539309625088184L;

	@Id
	@SequenceGenerator(name="PARAMETRO_ID_GENERATOR", sequenceName="itm_parametro_id_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PARAMETRO_ID_GENERATOR") 
	@Column(name = "id", unique = true, nullable = false) 
	private Integer id;
	
	@Column(name="nome_empresa")
	private String nomeEmpresa;
	
	@Column(name="nome_empresa_cobranca")
	private String nomeEmpresaCobranca;
	
	@Column(name="sigla_empresa")
	private String siglaEmpresa;

	@Column(name="cnpj")
	private String cnpj;
	
	@Column(name="cnpj_empresa_cobranca")
	private String cnpjEmpresaCobranca;
	
	@Column(name="qtd_dias_desbloqueio_temporario")
	private Integer qtdDiasDesbloqueioTemporario;
	
	
	@Column(name="qtd_dias_bloqueio")
	private Integer qtdDiasBloqueio;
	
	
	@Column(name="qtd_dias_aviso")
	private Integer qtdDiasAviso;
	
	@Column(name="qtd_dias_inativacao")
	private Integer qtdDiasInativacao;
	
	@Column(name="qtd_dias_negativacao")
	private Integer qtdDiasNegativacao;
	
	@Column(name="logradouro")
	private String logradouro;

	@Column(name="bairro")
	private String bairro;

	@Column(name="cep_empresa_cobranca")
	private String cepEmpresaCobranca;

	@Column(name="cidade")
	private String cidade;

	@Column(name="uf")
	private String uf;
	
	private String smtp;
	
	@Column(name="porta_smtp")
	private Integer portaSmtp;
	
	@Column(name="from_email")
	private String fromEmail;
	
	@Column(name="login_email")
	private String loginEmail;
	
	@Column(name="senha_email")
	private String senhaEmail;

	@Column(name="snmp_host")
	private String snmpHost;
	
	@Column(name="snmp_porta")
	private Integer snmpPorta;
	
	private BigDecimal multa;

	@Column(name="juros_mes")
	private BigDecimal jurosMes;
	
	@Column(name="chave_mestra")
	private String chaveMestra;

	@Column(name="use_tls")
	private boolean useTls;
	
	@Column(name="auth_email")
	private boolean authEmail;
	
	@Column(name="qtd_empregado_contratado")
	private Integer qtdEmpregadoContratado;

	@Column(name="qtd_empregado_terceirizado")
	private Integer qtdEmpregadoTerceirizado;
	
	@Column(name="numero_atendimento_telefonico")
	private String numeroAtendimentoTelefonico;
	
	
	@Column(name="total_fibra_km")
	private BigDecimal totalFibraKM;
	
	@Column(name="total_fibra_terceiro_km")
	private BigDecimal totalFibraTerceiroKM;
	
	@Column(name="total_fibra_prevista_km")
	private BigDecimal totalFibraPrevistaKM;
	
	@Column(name="total_fibra_terceiro_prevista_km")
	private BigDecimal totalFibraTerceiroPrevistaKM;
	
	@Column(name="total_fibra_implantada_km")
	private BigDecimal totalFibraImplantadaKM;
	
	@Column(name="total_fibra_implantada_terceiro_km")
	private BigDecimal totalFibraImplantadaTerceiroKM;
	
	@Column(name="total_fibra_implantada_prevista_km")
	private BigDecimal totalFibraImplantadaPrevistaKM;
	
	@Column(name="total_fibra_implantada_terceiro_prevista_km")
	private BigDecimal totalFibraImplantadaTerceiroPrevistaKM;
	
	@Column(name="upload_suspensao")
	private Integer uploadSuspensao;

	@Column(name="download_suspensao")
	private Integer downloadSuspensao;
	
	@Column(name="upload_bloqueio")
	private Integer uploadBloqueio;

	@Column(name="download_bloqueio")
	private Integer downloadBloqueio;
	
	
	@Column(name="url_chamado")
	private String urlChamado;
	
	@Column(name="framed_ip_start")
	private String framedIpStart;

	@Column(name="framed_ip_stop")
	private String framedIpStop;
	
	@Column(name="pool_name_bloqueio")
	private String poolNameBloqueio;
	
	public String getFramedIpStart() {
		return framedIpStart;
	}

	public void setFramedIpStart(String framedIpStart) {
		this.framedIpStart = framedIpStart;
	}

	public String getFramedIpStop() {
		return framedIpStop;
	}

	public void setFramedIpStop(String framedIpStop) {
		this.framedIpStop = framedIpStop;
	}

	private String telefone;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeEmpresaCobranca() {
		return nomeEmpresaCobranca;
	}

	public void setNomeEmpresaCobranca(String nomeEmpresaCobranca) {
		this.nomeEmpresaCobranca = nomeEmpresaCobranca;
	}
	
	public String getSiglaEmpresa() {
		return siglaEmpresa;
	}

	public void setSiglaEmpresa(String siglaEmpresa) {
		this.siglaEmpresa = siglaEmpresa;
	}

	public String getCnpjEmpresaCobranca() {
		return cnpjEmpresaCobranca;
	}

	public void setCnpjEmpresaCobranca(String cnpj) {
		this.cnpjEmpresaCobranca = cnpj;
	}

	public Integer getQtdDiasDesbloqueioTemporario() {
		return qtdDiasDesbloqueioTemporario;
	}

	public void setQtdDiasDesbloqueioTemporario(Integer qtdDiasDesbloqueioTemporario) {
		this.qtdDiasDesbloqueioTemporario = qtdDiasDesbloqueioTemporario;
	}

	public Integer getQtdDiasBloqueio() {
		return qtdDiasBloqueio;
	}

	public void setQtdDiasBloqueio(Integer qtdDiasBloqueio) {
		this.qtdDiasBloqueio = qtdDiasBloqueio;
	}

	public Integer getQtdDiasAviso() {
		return qtdDiasAviso;
	}

	public void setQtdDiasAviso(Integer qtdDiasAviso) {
		this.qtdDiasAviso = qtdDiasAviso;
	}

	public Integer getQtdDiasInativacao() {
		return qtdDiasInativacao;
	}

	public void setQtdDiasInativacao(Integer qtdDiasInativacao) {
		this.qtdDiasInativacao = qtdDiasInativacao;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCepEmpresaCobranca() {
		return cepEmpresaCobranca;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public void setCepEmpresaCobranca(String cepEmpresaCobranca) {
		this.cepEmpresaCobranca = cepEmpresaCobranca;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}
	
	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public String getSenhaEmail() {
		return senhaEmail;
	}

	public void setSenhaEmail(String senhaEmail) {
		this.senhaEmail = senhaEmail;
	}

	public Integer getPortaSmtp() {
		return portaSmtp;
	}

	public void setPortaSmtp(Integer portaSmtp) {
		this.portaSmtp = portaSmtp;
	}

	public String getSnmpHost() {
		return snmpHost;
	}

	public void setSnmpHost(String snmpHost) {
		this.snmpHost = snmpHost;
	}

	public Integer getSnmpPorta() {
		return snmpPorta;
	}

	public void setSnmpPorta(Integer snmpPorta) {
		this.snmpPorta = snmpPorta;
	}

	public Integer getQtdDiasNegativacao() {
		return qtdDiasNegativacao;
	}

	public void setQtdDiasNegativacao(Integer qtdDiasNegativacao) {
		this.qtdDiasNegativacao = qtdDiasNegativacao;
	}

	public BigDecimal getMulta() {
		return multa;
	}

	public void setMulta(BigDecimal multa) {
		this.multa = multa;
	}

	public BigDecimal getJurosMes() {
		return jurosMes;
	}

	public void setJurosMes(BigDecimal jurosMes) {
		this.jurosMes = jurosMes;
	}

	public String getChaveMestra() {
		return chaveMestra;
	}

	public void setChaveMestra(String chaveMestra) {
		this.chaveMestra = chaveMestra;
	}

	public boolean isUseTls() {
		return useTls;
	}

	public void setUseTls(boolean useTls) {
		this.useTls = useTls;
	}

	public boolean isAuthEmail() {
		return authEmail;
	}

	public void setAuthEmail(boolean authEmail) {
		this.authEmail = authEmail;
	}

	public Integer getQtdEmpregadoContratado() {
		return qtdEmpregadoContratado;
	}

	public void setQtdEmpregadoContratado(Integer qtdEmpregadoContratado) {
		this.qtdEmpregadoContratado = qtdEmpregadoContratado;
	}

	public Integer getQtdEmpregadoTerceirizado() {
		return qtdEmpregadoTerceirizado;
	}

	public void setQtdEmpregadoTerceirizado(Integer qtdEmpregadoTerceirizado) {
		this.qtdEmpregadoTerceirizado = qtdEmpregadoTerceirizado;
	}

	public String getNumeroAtendimentoTelefonico() {
		return numeroAtendimentoTelefonico;
	}

	public void setNumeroAtendimentoTelefonico(String numeroAtendimentoTelefonico) {
		this.numeroAtendimentoTelefonico = numeroAtendimentoTelefonico;
	}

	public BigDecimal getTotalFibraKM() {
		return totalFibraKM;
	}

	public void setTotalFibraKM(BigDecimal totalFibraKM) {
		this.totalFibraKM = totalFibraKM;
	}

	public BigDecimal getTotalFibraTerceiroKM() {
		return totalFibraTerceiroKM;
	}

	public void setTotalFibraTerceiroKM(BigDecimal totalFibraTerceiroKM) {
		this.totalFibraTerceiroKM = totalFibraTerceiroKM;
	}

	public BigDecimal getTotalFibraPrevistaKM() {
		return totalFibraPrevistaKM;
	}

	public void setTotalFibraPrevistaKM(BigDecimal totalFibraPrevistaKM) {
		this.totalFibraPrevistaKM = totalFibraPrevistaKM;
	}

	public BigDecimal getTotalFibraTerceiroPrevistaKM() {
		return totalFibraTerceiroPrevistaKM;
	}

	public void setTotalFibraTerceiroPrevistaKM(BigDecimal totalFibraTerceiroPrevistaKM) {
		this.totalFibraTerceiroPrevistaKM = totalFibraTerceiroPrevistaKM;
	}

	public String getPoolNameBloqueio() {
		return poolNameBloqueio;
	}

	public void setPoolNameBloqueio(String poolNameBloqueio) {
		this.poolNameBloqueio = poolNameBloqueio;
	}

	public BigDecimal getTotalFibraImplantadaKM() {
		return totalFibraImplantadaKM;
	}

	public void setTotalFibraImplantadaKM(BigDecimal totalFibraImplantadaKM) {
		this.totalFibraImplantadaKM = totalFibraImplantadaKM;
	}

	public Integer getUploadBloqueio() {
		return uploadBloqueio;
	}

	public void setUploadBloqueio(Integer uploadBloqueio) {
		this.uploadBloqueio = uploadBloqueio;
	}

	public Integer getDownloadBloqueio() {
		return downloadBloqueio;
	}

	public void setDownloadBloqueio(Integer downloadBloqueio) {
		this.downloadBloqueio = downloadBloqueio;
	}

	public BigDecimal getTotalFibraImplantadaTerceiroKM() {
		return totalFibraImplantadaTerceiroKM;
	}

	public void setTotalFibraImplantadaTerceiroKM(BigDecimal totalFibraImplantadaTerceiroKM) {
		this.totalFibraImplantadaTerceiroKM = totalFibraImplantadaTerceiroKM;
	}

	public BigDecimal getTotalFibraImplantadaPrevistaKM() {
		return totalFibraImplantadaPrevistaKM;
	}

	public void setTotalFibraImplantadaPrevistaKM(BigDecimal totalFibraImplantadaPrevistaKM) {
		this.totalFibraImplantadaPrevistaKM = totalFibraImplantadaPrevistaKM;
	}

	public BigDecimal getTotalFibraImplantadaTerceiroPrevistaKM() {
		return totalFibraImplantadaTerceiroPrevistaKM;
	}

	public void setTotalFibraImplantadaTerceiroPrevistaKM(BigDecimal totalFibraImplantadaTerceiroPrevistaKM) {
		this.totalFibraImplantadaTerceiroPrevistaKM = totalFibraImplantadaTerceiroPrevistaKM;
	}

	public Integer getUploadSuspensao() {
		return uploadSuspensao;
	}

	public String getUrlChamado() {
		return urlChamado;
	}

	public void setUrlChamado(String urlChamado) {
		this.urlChamado = urlChamado;
	}

	public void setUploadSuspensao(Integer uploadSuspensao) {
		this.uploadSuspensao = uploadSuspensao;
	}

	public Integer getDownloadSuspensao() {
		return downloadSuspensao;
	}

	public void setDownloadSuspensao(Integer downloadSuspensao) {
		this.downloadSuspensao = downloadSuspensao;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
