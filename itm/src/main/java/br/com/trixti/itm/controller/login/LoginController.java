package br.com.trixti.itm.controller.login;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.picketlink.Identity;
import org.picketlink.credential.DefaultLoginCredentials;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.to.LoginTO;

@ManagedBean
@ViewScoped
public class LoginController extends AbstractController<Object>{

	private LoginTO loginTO;
	
	private @Inject ClienteService clienteService;
	private @Inject DefaultLoginCredentials credentials;
	private @Inject Identity identity;
	
	
	@PostConstruct
	private void init(){
		getLoginTO().setPrimeiroAcesso(false);
	}
	
	public void recuperarCliente(){
		Cliente cliente = clienteService.recuperarPorEmail(getLoginTO().getCliente().getEmail());
		if(cliente != null){
			getLoginTO().setCliente(cliente);
		}else{
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario nao existe", "O Registro foi excluido na base"));
		}	
	}
	
	public void gravarCliente(){
		clienteService.alterar(getLoginTO().getCliente());
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Primeiro acesso cadastrado com sucesso!", "O Registro foi excluido na base"));
		credentials.setUserId(getLoginTO().getCliente().getEmail());
		credentials.setPassword(getLoginTO().getCliente().getSenha());
		identity.login();
		getLoginTO().setCliente(null);
		getLoginTO().setPrimeiroAcesso(false);
	}

	public LoginTO getLoginTO() {
		if (loginTO == null) {
			loginTO = new LoginTO();
		}
		return loginTO;
	}


	public void setLoginTO(LoginTO loginTO) {
		this.loginTO = loginTO;
	}
	
}
