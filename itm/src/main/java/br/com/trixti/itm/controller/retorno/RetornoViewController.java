package br.com.trixti.itm.controller.retorno;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Retorno;
import br.com.trixti.itm.infra.financeiro.IntegracaoFinanceiraItau;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.infra.security.annotations.Financeiro;
import br.com.trixti.itm.service.retorno.RetornoService;
import br.com.trixti.itm.to.RetornoTO;

@Model
@ViewScoped
@Financeiro
public class RetornoViewController  extends AbstractController<Retorno> implements Serializable{
	
	private static final long serialVersionUID = 8607007655516127618L;
	private RetornoTO retornoTO;
	private @Inject RetornoService retornoService;
	private @Inject IntegracaoFinanceiraItau integracaoFinanceiraItau;
	
	@PostConstruct
	public void init(){
		inicializar();
	}
	
	private void inicializar(){
		String parametro = getRequest().getParameter("parametro");
		getRetornoTO().setRetorno(retornoService.recuperar(new Integer(parametro)));
		getRetornoTO().setRecords(integracaoFinanceiraItau.comporRetorno(getRetornoTO().getRetorno()));
	}
	
	

	public RetornoTO getRetornoTO() {
		if (retornoTO == null) {
			retornoTO = new RetornoTO();
		}
		return retornoTO;
	}


	public void setRetornoTO(RetornoTO retornoTO) {
		this.retornoTO = retornoTO;
	}
	

}
