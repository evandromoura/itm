package br.com.trixti.itm.entity;

public enum StatusRetorno {
	PENDENTE,PROCESSADO,ERRO_PROCESSAMENTO;
	
	
	public String getName(){
		return name();
	}
}
