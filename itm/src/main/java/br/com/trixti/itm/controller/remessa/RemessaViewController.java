package br.com.trixti.itm.controller.remessa;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Remessa;
import br.com.trixti.itm.infra.security.annotations.Financeiro;
import br.com.trixti.itm.service.remessa.RemessaService;
import br.com.trixti.itm.to.RemessaTO;



@Named
@ViewScoped
@Financeiro
public class RemessaViewController extends AbstractController<Remessa> implements Serializable{
	
	
	private static final long serialVersionUID = -8623463153047135139L;
	private @Inject RemessaService remessaService;
	private RemessaTO remessaTO;
	
	
	@PostConstruct
	private void init(){
		String parametro = getRequest().getParameter("parametro");
		inicializarView(Integer.valueOf(parametro));
	}
	
	
	public void notificarEmAtraso(){
		remessaService.notificarBoletoEmAtraso(getRemessaTO().getRemessa());
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enviado com Sucesso", "O Registro foi incluido na base"));
	}
	
	
	public void notificarTodos(){
		remessaService.notificarTodosBoleto(getRemessaTO().getRemessa());
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enviado com Sucesso", "O Registro foi incluido na base"));
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


