package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.Parametro;

public class ParametroTO {

	private Parametro parametro;
	private List<Parametro> parametros;
	private Parametro parametroPesquisa;
	
	
	public Parametro getParametro() {
		if (parametro == null) {
			parametro = new Parametro();
		}
		return parametro;
	}
	
	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}
	
	public List<Parametro> getParametros() {
		if (parametros == null) {
			parametros = new ArrayList<Parametro>();
		}

		return parametros;
	}
	
	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}

	public Parametro getParametroPesquisa() {
		if (parametroPesquisa == null) {
			parametroPesquisa = new Parametro();
		}
		return parametroPesquisa;
	}

	public void setParametroPesquisa(Parametro parametroPesquisa) {
		this.parametroPesquisa = parametroPesquisa;
	}

}