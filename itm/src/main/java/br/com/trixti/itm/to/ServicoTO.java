package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.Servico;
import br.com.trixti.itm.entity.ServicoLocal;

public class ServicoTO {

	private Servico servico;
	private List<Servico> servicos;
	private Servico servicoPesquisa;
	private ServicoLocal servicoLocal;
	
	
	public Servico getServico() {
		if (servico == null) {
			servico = new Servico();
		}
		return servico;
	}
	
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	
	public List<Servico> getServicos() {
		if (servicos == null) {
			servicos = new ArrayList<Servico>();
		}

		return servicos;
	}
	
	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public Servico getServicoPesquisa() {
		if (servicoPesquisa == null) {
			servicoPesquisa = new Servico();
		}
		return servicoPesquisa;
	}

	public void setServicoPesquisa(Servico servicoPesquisa) {
		this.servicoPesquisa = servicoPesquisa;
	}

	public ServicoLocal getServicoLocal() {
		if (servicoLocal == null) {
			servicoLocal = new ServicoLocal();
		}
		return servicoLocal;
	}

	public void setServicoLocal(ServicoLocal servicoLocal) {
		this.servicoLocal = servicoLocal;
	}

}