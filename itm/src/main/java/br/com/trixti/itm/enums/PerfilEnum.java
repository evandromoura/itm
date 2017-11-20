package br.com.trixti.itm.enums;

public enum PerfilEnum {
	ADMIN,
	FINANCEIRO,
	SUPORTE_NIVEL2,
	SUPORTE_NIVEL1,
	CLIENTE;
	
	public String getName(){
		return name();
	}
}
