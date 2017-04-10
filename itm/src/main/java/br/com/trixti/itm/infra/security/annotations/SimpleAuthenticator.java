package br.com.trixti.itm.infra.security.annotations;

import static org.picketlink.idm.model.basic.BasicModel.grantRole;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.picketlink.Identity;
import org.picketlink.annotations.PicketLink;
import org.picketlink.authentication.BaseAuthenticator;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.basic.Role;
import org.picketlink.idm.model.basic.User;

import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.enums.PerfilEnum;
import br.com.trixti.itm.service.cliente.ClienteService;

@Named
@PicketLink
public class SimpleAuthenticator extends BaseAuthenticator {
	private @Inject DefaultLoginCredentials credentials;
	private @Inject Identity identity;
	private @Inject ClienteService clienteService;
	private @Inject CustomIdentity customIdentity;
	private @Inject IdentityManager identityManager;
	private @Inject RelationshipManager relationshipManager;

	@Override
	public void authenticate() {
		
		Cliente cliente = clienteService.recuperarPorAutenticacao(credentials.getUserId(), credentials.getPassword());
		if(!identity.isLoggedIn() &&  cliente != null && credentials.getUserId() != null){
			User usuario = new User(credentials.getUserId());
			setAccount(usuario);
	        identityManager.add(usuario);
	        identityManager.updateCredential(usuario, new Password(credentials.getPassword()));
	        Role role = new Role(PerfilEnum.CLIENTE.name());
	        identityManager.add(role);
	        grantRole(relationshipManager, usuario, role);
			setStatus(AuthenticationStatus.SUCCESS);
			customIdentity.setCliente(cliente);
		}else if (credentials.getUserId() != null && credentials.getUserId().equals("admin") && credentials.getPassword().equals("itrixti@!")){
			User usuario = new User(credentials.getUserId());
			setAccount(usuario);
			identityManager.add(usuario);
			identityManager.updateCredential(usuario, new Password(credentials.getPassword()));
			Role role = new Role(PerfilEnum.ADMIN.name());
			identityManager.add(role);
			grantRole(relationshipManager, usuario, role);
			setStatus(AuthenticationStatus.SUCCESS);
		}else{
			setStatus(AuthenticationStatus.FAILURE);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Authentication Failure - The username or password you provided were invalid."));
		}
	}

	public String logout() {
		identity.logout();
		return "/login?faces-redirect=true";
	}
}