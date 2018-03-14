package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.CentroCusto;

public class CentroCustoTO {

	private CentroCusto centroCusto;
	private List<CentroCusto> centroCustos;
	private CentroCusto centroCustoPesquisa;
	
	
	public CentroCusto getCentroCusto() {
		if (centroCusto == null) {
			centroCusto = new CentroCusto();
		}
		return centroCusto;
	}
	
	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}
	
	public List<CentroCusto> getCentroCustos() {
		if (centroCustos == null) {
			centroCustos = new ArrayList<CentroCusto>();
		}

		return centroCustos;
	}
	
	public void setCentroCustos(List<CentroCusto> centroCustos) {
		this.centroCustos = centroCustos;
	}

	public CentroCusto getCentroCustoPesquisa() {
		if (centroCustoPesquisa == null) {
			centroCustoPesquisa = new CentroCusto();
		}
		return centroCustoPesquisa;
	}

	public void setCentroCustoPesquisa(CentroCusto centroCustoPesquisa) {
		this.centroCustoPesquisa = centroCustoPesquisa;
	}

}