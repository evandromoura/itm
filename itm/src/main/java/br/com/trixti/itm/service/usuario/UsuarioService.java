package br.com.trixti.itm.service.usuario;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.usuario.UsuarioDAO;
import br.com.trixti.itm.entity.Usuario;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class UsuarioService extends AbstractService<Usuario> {

	private @Inject UsuarioDAO usuarioDAO;
	
	
	@Override
	public AbstractDAO<Usuario> getDAO() {
		return usuarioDAO;
	}

	public List<Usuario> pesquisar(Usuario usuario) {
		return usuarioDAO.pesquisar(usuario);
	}
	
	public Usuario login(String login,String senha){
		try{
			return usuarioDAO.login(login,senha);
		}catch(Exception e){
			return null;
		}	
	}
	

}






