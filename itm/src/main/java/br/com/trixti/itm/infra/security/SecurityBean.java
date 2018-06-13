package br.com.trixti.itm.infra.security;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.infra.security.annotations.CustomIdentity;

@Named
public class SecurityBean {
	
	private @Inject CustomIdentity customIdentity;
	
	public boolean isPermissao(String perfil){
		if(customIdentity.getPerfil() != null && customIdentity.getPerfil().name().equals(perfil)){
			return true;
		}else{
			return false;
		}
	}

}
