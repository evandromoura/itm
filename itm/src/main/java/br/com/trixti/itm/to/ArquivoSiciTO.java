package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.ArquivoSici;

public class ArquivoSiciTO {

	private ArquivoSici arquivoSici;
	private List<ArquivoSici> arquivoSicis;
	private ArquivoSici arquivoSiciPesquisa;
	
	
	public ArquivoSici getArquivoSici() {
		if (arquivoSici == null) {
			arquivoSici = new ArquivoSici();
		}
		return arquivoSici;
	}
	
	public void setArquivoSici(ArquivoSici arquivoSici) {
		this.arquivoSici = arquivoSici;
	}
	
	public List<ArquivoSici> getArquivoSicis() {
		if (arquivoSicis == null) {
			arquivoSicis = new ArrayList<ArquivoSici>();
		}

		return arquivoSicis;
	}
	
	public void setArquivoSicis(List<ArquivoSici> arquivoSicis) {
		this.arquivoSicis = arquivoSicis;
	}

	public ArquivoSici getArquivoSiciPesquisa() {
		if (arquivoSiciPesquisa == null) {
			arquivoSiciPesquisa = new ArquivoSici();
		}
		return arquivoSiciPesquisa;
	}

	public void setArquivoSiciPesquisa(ArquivoSici arquivoSiciPesquisa) {
		this.arquivoSiciPesquisa = arquivoSiciPesquisa;
	}

}