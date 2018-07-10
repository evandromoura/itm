package br.com.trixti.itm.entity;

public enum StatusEquipamentoEnum {
	NOVO,USADO,QUEBRADO,INUTILIZAVEL;
	
	public String getName(){
		return name();
	}
}
