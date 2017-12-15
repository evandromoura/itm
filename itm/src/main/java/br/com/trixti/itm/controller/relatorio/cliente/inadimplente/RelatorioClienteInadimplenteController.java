package br.com.trixti.itm.controller.relatorio.cliente.inadimplente;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.service.relatorio.cliente.inadimplente.RelatorioClienteInadimplenteService;
import br.com.trixti.itm.to.RelatorioClienteInadimplenteTO;

@Named
@ViewScoped
@Admin
public class RelatorioClienteInadimplenteController implements Serializable{
	
	private static final long serialVersionUID = 3091023177323349325L;
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
