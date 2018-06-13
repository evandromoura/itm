package br.com.trixti.itm.controller;

import br.com.trixti.itm.to.FiltroTO;

public class PaginacaoController<T> extends AbstractController<T> {

	
	public void paginar(){
	}
	
	private FiltroTO<T> filtroTO;

	public FiltroTO<T> getFiltroTO() {
		if (filtroTO == null) {
			filtroTO = new FiltroTO<T>();
		}
		return filtroTO;
	}

	public void setFiltroTO(FiltroTO<T> filtroTO) {
		this.filtroTO = filtroTO;
	}

	
}
