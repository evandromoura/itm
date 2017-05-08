package br.com.trixti.itm.entity;

public enum SituacaoSMSEnum {

	CADASTRADO("label.uc.sms.situacao.cadastrado"),
	ENVIADO("label.uc.sms.situacao.enviado"),
	AGENDADO("label.uc.sms.situacao.agendado"),
	ERRO_DESCONHECIDO("label.uc.sms.situacao.erroDesconhecido"),
	NUMERO_INVALIDO("label.uc.sms.situacao.numeroInvalido"),
	LIMITE_EXCEDIDO("label.uc.sms.situacao.limiteExcedido"),
	ERRO_AUTENTICACAO("label.uc.sms.situacao.erroAutenticacao"),
	NAO_RECEBIDO("label.uc.sms.situacao.naoRecebido");

	private String key;

	private SituacaoSMSEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}