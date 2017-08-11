package br.com.trixti.itm.entity;

public enum StatusContrato {
	
	ATIVO,INATIVO,BLOQUEADO,CANCELADO;
	
	public String getName(){
		return name();
	}

}
