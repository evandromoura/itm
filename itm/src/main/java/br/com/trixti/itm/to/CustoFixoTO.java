package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.CustoFixo;

public class CustoFixoTO {

	private CustoFixo custoFixo;
	private List<CustoFixo> custoFixos;
	private CustoFixo custoFixoPesquisa;
	
	
	public CustoFixo getCustoFixo() {
		if (custoFixo == null) {
			custoFixo = new CustoFixo();
		}
		return custoFixo;
	}
	
	public void setCustoFixo(CustoFixo custoFixo) {
		this.custoFixo = custoFixo;
	}
	
	public List<CustoFixo> getCustoFixos() {
		if (custoFixos == null) {
			custoFixos = new ArrayList<CustoFixo>();
		}

		return custoFixos;
	}
	
	public void setCustoFixos(List<CustoFixo> custoFixos) {
		this.custoFixos = custoFixos;
	}

	public CustoFixo getCustoFixoPesquisa() {
		if (custoFixoPesquisa == null) {
			custoFixoPesquisa = new CustoFixo();
		}
		return custoFixoPesquisa;
	}

	public void setCustoFixoPesquisa(CustoFixo custoFixoPesquisa) {
		this.custoFixoPesquisa = custoFixoPesquisa;
	}

}