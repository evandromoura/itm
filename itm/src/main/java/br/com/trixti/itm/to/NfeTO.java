package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.Nfe;

public class NfeTO {

	private Nfe nfe;
	private List<Nfe> nfes;
	private Nfe nfePesquisa;
	
	
	public Nfe getNfe() {
		if (nfe == null) {
			nfe = new Nfe();
		}
		return nfe;
	}
	
	public void setNfe(Nfe nfe) {
		this.nfe = nfe;
	}
	
	public List<Nfe> getNfes() {
		if (nfes == null) {
			nfes = new ArrayList<Nfe>();
		}

		return nfes;
	}
	
	public void setNfes(List<Nfe> nfes) {
		this.nfes = nfes;
	}

	public Nfe getNfePesquisa() {
		if (nfePesquisa == null) {
			nfePesquisa = new Nfe();
		}
		return nfePesquisa;
	}

	public void setNfePesquisa(Nfe nfePesquisa) {
		this.nfePesquisa = nfePesquisa;
	}

}