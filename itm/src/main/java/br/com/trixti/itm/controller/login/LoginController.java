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
			String messageText = getMessage("label.global.usuarionaoexiste");
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageText, messageText));
		}	
	}
	
	public void gravarCliente(){
		clienteService.alterar(getLoginTO().getCliente());
		String messageText = getMessage("label.global.primeiroacessocadastrosucesso");
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, messageText, messageText));
		credentials.setUserId(getLoginTO().getCliente().getEmail());
		credentials.setPassword(getLoginTO().getCliente().getSenha());
		identity.login();
		getLoginTO().setCliente(null);
		getLoginTO().setPrimeiroAcesso(false);
	}
	
	public String esqueciSenha(){
		try{
			clienteService.esqueciSenha(getLoginTO().getCliente().getEmail());
			String messageText = getMessage("label.global.senhaenviadasucesso");
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, messageText, messageText));
			return "sucesso";
		}catch(Exception e){
			String messageText = getMessage("label.global.erro");
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageText, messageText));
			return "erro";
		}	
	}
	
	public String recuperarNovoAcesso(){
		String senha = getLoginTO().getCliente().getSenha();
		Cliente cliente = clienteService.recuperarPorEmailCpf(getLoginTO().getCliente().getEmail(), getLoginTO().getCliente().getCpfCnpj());
		if(cliente != null){
			getLoginTO().setCliente(cliente);
			cliente.setSenha(senha);
			gravarCliente();
			return "sucesso";
		}else{
			String messageText = getMessage("label.global.clientenaoencontrado");
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageText, messageText));
			return "erro";
		}	
				
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
