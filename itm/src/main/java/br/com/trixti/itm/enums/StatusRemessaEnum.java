package br.com.trixti.itm.enums;

public enum StatusRemessaEnum {
	
	GERADO,ENVIADO,PROCESSADO,A_ENVIAR,FECHADO;
	
	public String getName(){
		return name();
	}

}
