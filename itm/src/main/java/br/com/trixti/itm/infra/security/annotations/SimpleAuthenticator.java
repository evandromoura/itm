package br.com.trixti.itm.infra.security.annotations;

import javax.inject.Inject;
import javax.inject.Named;

import org.picketlink.Identity;
import org.picketlink.annotations.PicketLink;
import org.picketlink.authentication.BaseAuthenticator;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.idm.model.basic.User;

import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.TipoLog;
import br.com.trixti.itm.entity.Usuario;
import br.com.trixti.itm.enums.PerfilEnum;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.service.log.LogService;
import br.com.trixti.itm.service.usuario.UsuarioService;

@Named
@PicketLink
public class SimpleAuthenticator extends BaseAuthenticator {
	private @Inject DefaultLoginCredentials credentials;
	private @Inject Identity identity;
	private @Inject ClienteService clienteService;
	private @Inject UsuarioService usuarioService;
	private @Inject CustomIdentity customIdentity;
	private @Inject LogService logService;


	@Override
	public void authenticate() {
		Usuario usuarioLogin = usuarioService.login(credentials.getUserId(), credentials.getPassword());
		if(usuarioLogin != null){
			User usuario = new User(credentials.getUserId());
			setAccount(usuario);
			setStatus(AuthenticationStatus.SUCCESS);
			customIdentity.setUsuario(usuarioLogin);
			customIdentity.setPerfil(usuarioLogin.getPerfil());
			logService.log(usuarioLogin.getLogin(), TipoLog.LOGIN_USUARIO);
		}else{
			Cliente cliente = clienteService.recuperarPorAutenticacao(credentials.getUserId(), credentials.getPassword());
			if(!identity.isLoggedIn() &&  cliente != null && credentials.getUserId() != null){
				User usuario = new User(credentials.getUserId());
				setAccount(usuario);
				setStatus(AuthenticationStatus.SUCCESS);
				customIdentity.setCliente(cliente);
				customIdentity.setPerfil(PerfilEnum.CLIENTE);
				logService.log(cliente.getEmail(), TipoLog.LOGIN_CLIENTE);
			}else{
				setStatus(AuthenticationStatus.FAILURE);
			}
		}
	}

	public String logout() {
		if(customIdentity.getCliente() != null){
			logService.log(customIdentity.getCliente().getEmail(), TipoLog.LOGOUT_CLIENTE);
		}
		
		if(customIdentity.getUsuario() != null){
			logService.log(customIdentity.getUsuario().getLogin(), TipoLog.LOGOUT_USUARIO);
		}
		customIdentity.setCliente(null);
		customIdentity.setPerfil(null);
		identity.logout();
		return "/login?faces-redirect=true";
	}
}