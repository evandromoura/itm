package br.com.trixti.itm.entity;

public enum TipoContratoNotificacao {
	
	ENVIO_BOLETO,
	SEGUNDA_VIDA,
	AVISO_ATRASO_INICIAL,
	AVISO_ANTES_VENCIMENTO,
	AVISO_ANTES_BLOQUEIO_PRIMEIRO,
	AVISO_ANTES_BLOQUEIO_SEGUNDO,
	AVISO_ANTES_BLOQUEIO_TERCEIRO,
	AVISO_BLOQUEIO,
	AVISO_INATIVACAO,
	AVISO_NEGATIVACAO;
	
	
	public String getName(){
		return name();
	}

}
