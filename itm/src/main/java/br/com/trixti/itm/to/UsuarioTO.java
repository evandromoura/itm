package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.Usuario;

public class UsuarioTO {

	private Usuario usuario;
	private List<Usuario> usuarios;
	private Usuario usuarioPesquisa;
	
	
	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Usuario> getUsuarios() {
		if (usuarios == null) {
			usuarios = new ArrayList<Usuario>();
		}

		return usuarios;
	}
	
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuarioPesquisa() {
		if (usuarioPesquisa == null) {
			usuarioPesquisa = new Usuario();
		}
		return usuarioPesquisa;
	}

	public void setUsuarioPesquisa(Usuario usuarioPesquisa) {
		this.usuarioPesquisa = usuarioPesquisa;
	}

}