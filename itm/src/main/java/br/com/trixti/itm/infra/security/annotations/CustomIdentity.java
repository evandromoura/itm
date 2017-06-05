package br.com.trixti.itm.infra.security.annotations;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.Usuario;
import br.com.trixti.itm.enums.PerfilEnum;

@Named
@SessionScoped
public class CustomIdentity implements Serializable {
	
	private static final long serialVersionUID = -6423720011859010353L;
	
	private Cliente cliente;
	private PerfilEnum perfil;
	private Contrato contratoSelecionado;
	private Usuario usuario;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public PerfilEnum getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}

	public Contrato getContratoSelecionado() {
		return contratoSelecionado;
	}

	public void setContratoSelecionado(Contrato contratoSelecionado) {
		this.contratoSelecionado = contratoSelecionado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
}
