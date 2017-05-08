package br.com.trixti.itm.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "itm_sms")
public class SMS implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ITM_SMS_ID_GENERATOR", sequenceName="itm_sms_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ITM_SMS_ID_GENERATOR")
	private Long id;

	@Enumerated(EnumType.STRING)
	private SituacaoSMSEnum situacao;

	@Column(name="data_criacao")
	private LocalDateTime dataCriacao;

	@Column(name="data_envio")
	private LocalDateTime dataEnvio;


	@Column(name="ddd_telefone")
	private String dddTelefone;

	@Column(name="numero_telefone")
	private String numeroTelefone;

	private String mensagem;



	public SMS() {
	}

	public SMS(SMSBuilder builder) {
		this.id = builder.id;
		this.dddTelefone = builder.dddTelefone;
		this.numeroTelefone = builder.numeroTelefone;
		this.mensagem = builder.mensagem;
		
	}

	public static class SMSBuilder {
		private Long id;
		private String dddTelefone;
		private String numeroTelefone;
		private String mensagem;
	
	
		public SMSBuilder dddTelefone(String dddTelefone) {
			this.dddTelefone = dddTelefone;
			return this;
		}

		public SMSBuilder numeroTelefone(String numeroTelefone) {
			this.numeroTelefone = numeroTelefone;
			return this;
		}

		public SMSBuilder mensagem(String mensagem) {
			this.mensagem = mensagem;
			return this;
		}
		
		public SMSBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public SMS build() {
			return new SMS(this);
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SituacaoSMSEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoSMSEnum situacao) {
		this.situacao = situacao;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(LocalDateTime dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public String getDddTelefone() {
		return dddTelefone;
	}

	public void setDddTelefone(String dddTelefone) {
		this.dddTelefone = dddTelefone;
	}

	public String getNumeroTelefone() {
		return numeroTelefone;
	}

	public void setNumeroTelefone(String numeroTelefone) {
		this.numeroTelefone = numeroTelefone;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}