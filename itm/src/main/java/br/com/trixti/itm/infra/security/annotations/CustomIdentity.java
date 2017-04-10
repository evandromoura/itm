package br.com.trixti.itm.infra.security.annotations;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.trixti.itm.entity.Cliente;

@Named
@SessionScoped
public class CustomIdentity implements Serializable {
	
	private static final long serialVersionUID = -6423720011859010353L;
	
	private Cliente cliente;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
}
