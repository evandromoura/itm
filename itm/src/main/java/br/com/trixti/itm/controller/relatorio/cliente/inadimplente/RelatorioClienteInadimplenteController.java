package br.com.trixti.itm.controller.relatorio.cliente.inadimplente;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.trixti.itm.service.relatorio.cliente.inadimplente.RelatorioClienteInadimplenteService;
import br.com.trixti.itm.to.RelatorioClienteInadimplenteTO;

@ViewScoped
@ManagedBean
public class RelatorioClienteInadimplenteController{
	
	private @Inject RelatorioClienteInadimplenteService relatorioClienteInadimplenteService;
	private RelatorioClienteInadimplenteTO relatorioClienteInadimplenteTO ;
	
	@PostConstruct
	private void init(){
		RelatorioClienteInadimplenteTO relatorioTO =  relatorioClienteInadimplenteService.gerar();
		getRelatorioClienteInadimplenteTO().setBoletos15Dias(relatorioTO.getBoletos15Dias());
		getRelatorioClienteInadimplenteTO().setBoletos30Dias(relatorioTO.getBoletos30Dias());
		getRelatorioClienteInadimplenteTO().setBoletos60Dias(relatorioTO.getBoletos60Dias());
		getRelatorioClienteInadimplenteTO().setBoletos90Dias(relatorioTO.getBoletos90Dias());
		getRelatorioClienteInadimplenteTO().setParametro(relatorioTO.getParametro());
	}

	public RelatorioClienteInadimplenteTO getRelatorioClienteInadimplenteTO() {
		if (relatorioClienteInadimplenteTO == null) {
			relatorioClienteInadimplenteTO = new RelatorioClienteInadimplenteTO();
		}
		return relatorioClienteInadimplenteTO;
	}

	public void setRelatorioClienteInadimplenteTO(RelatorioClienteInadimplenteTO relatorioClienteInadimplenteTO) {
		this.relatorioClienteInadimplenteTO = relatorioClienteInadimplenteTO;
	}

}
