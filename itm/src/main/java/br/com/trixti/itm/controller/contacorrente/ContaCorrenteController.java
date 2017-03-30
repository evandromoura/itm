package br.com.trixti.itm.controller.contacorrente;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.ContaCorrente;
import br.com.trixti.itm.to.ClienteTO;
import br.com.trixti.itm.to.ContaCorrenteTO;



@ViewScoped
@ManagedBean

public class ContaCorrenteController extends AbstractController<ContaCorrente> {
	
	private @Inject ContaCorrente contaCorrente;
	private ContaCorrenteTO contaCorrenteTO;
	
	

	public ContaCorrente getContaCorrente() {
		return contaCorrente;
	}
	

	public void setContaCorrente(ContaCorrente contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	
	
}


