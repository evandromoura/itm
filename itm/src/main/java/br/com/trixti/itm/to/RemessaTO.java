package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.Remessa;

public class RemessaTO {

	private Remessa remessa;
	private List<Remessa> remessas;
	private Remessa remessaPesquisa;
	
	
	public Remessa getRemessa() {
		if (remessa == null) {
			remessa = new Remessa();
		}
		return remessa;
	}
	
	public void setRemessa(Remessa remessa) {
		this.remessa = remessa;
	}
	
	public List<Remessa> getRemessas() {
		if (remessas == null) {
			remessas = new ArrayList<Remessa>();
		}

		return remessas;
	}
	
	public void setRemessas(List<Remessa> remessas) {
		this.remessas = remessas;
	}

	public Remessa getRemessaPesquisa() {
		if (remessaPesquisa == null) {
			remessaPesquisa = new Remessa();
		}
		return remessaPesquisa;
	}

	public void setRemessaPesquisa(Remessa remessaPesquisa) {
		this.remessaPesquisa = remessaPesquisa;
	}

}