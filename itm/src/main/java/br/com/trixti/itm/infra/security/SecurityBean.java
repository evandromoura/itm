package br.com.trixti.itm.infra.security;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.infra.security.annotations.CustomIdentity;

@Named
public class SecurityBean {
	
	private @Inject CustomIdentity customIdentity;
	
	public boolean isPermissao(String perfil){
		
		String teste = "{\"nome\":\"\",\"email\":\"\",\"telefoneCelular\":\"\",\"cpfCnpj\":\"\",\"login\":\"\",\"grupo\":{},\"produto\":{\"contratoProdutos\":[]},\"tags\":[\"BLEND\"],\"semPagamento\":false,\"comPagamento\":true,\"semTag\":false,\"periodoCadastroContrato\":{}}";
		return customIdentity.getPerfil().name().equals(perfil);
	}
	

}
