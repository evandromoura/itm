package br.com.trixti.itm.entity;

public enum MeioEnvioContratoNotificacao {
	
	EMAIL,SMS,EMAIL_E_SMS;
	
	public String getName(){
		return name();
	}

}
