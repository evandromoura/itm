package br.com.trixti.itm.controller.remessa;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Remessa;
import br.com.trixti.itm.service.remessa.RemessaService;
import br.com.trixti.itm.to.RemessaTO;



@ViewScoped
@ManagedBean
public class RemessaViewController extends AbstractController<Remessa> {
	
	
	private @Inject RemessaService remessaService;
	private RemessaTO remessaTO;
	
	
	@PostConstruct
	private void init(){
		String parametro = getRequest().getParameter("parametro");
		inicializarView(Integer.valueOf(parametro));
	}
	
	
	
	private void inicializarView(Integer id){
		getRemessaTO().setRemessa(remessaService.recuperar(id));
	}
	

	public RemessaTO getRemessaTO() {
		if (remessaTO == null) {
			remessaTO = new RemessaTO();
		}
		return remessaTO;
	}

	public void setRemessaTO(RemessaTO remessaTO) {
		this.remessaTO = remessaTO;
	}
	
	
}


