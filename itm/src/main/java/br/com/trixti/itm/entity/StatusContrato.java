package br.com.trixti.itm.entity;

public enum StatusContrato {
	
	ATIVO,INATIVO,BLOQUEADO,CANCELADO,SUSPENSO;
	
	public String getName(){
		return name();
	}

}
