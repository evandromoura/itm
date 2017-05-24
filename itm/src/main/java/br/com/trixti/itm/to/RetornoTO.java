package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

import br.com.trixti.itm.entity.Retorno;

public class RetornoTO {

	private Retorno retorno;
	private List<Retorno> retornos;
	private Retorno retornoPesquisa;
	private Part upload;
	
	
	public Retorno getRetorno() {
		if (retorno == null) {
			retorno = new Retorno();
		}
		return retorno;
	}
	
	public void setRetorno(Retorno retorno) {
		this.retorno = retorno;
	}
	
	public List<Retorno> getRetornos() {
		if (retornos == null) {
			retornos = new ArrayList<Retorno>();
		}

		return retornos;
	}
	
	public void setRetornos(List<Retorno> retornos) {
		this.retornos = retornos;
	}

	public Retorno getRetornoPesquisa() {
		if (retornoPesquisa == null) {
			retornoPesquisa = new Retorno();
		}
		return retornoPesquisa;
	}

	public void setRetornoPesquisa(Retorno retornoPesquisa) {
		this.retornoPesquisa = retornoPesquisa;
	}

	public Part getUpload() {
		return upload;
	}

	public void setUpload(Part upload) {
		this.upload = upload;
	}

	

}