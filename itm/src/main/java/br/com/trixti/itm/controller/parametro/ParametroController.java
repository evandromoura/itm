package br.com.trixti.itm.controller.parametro;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.to.ParametroTO;


@ViewScoped
@ManagedBean
public class ParametroController extends AbstractController<Parametro> {
	
	
	private @Inject ParametroService parametroService;
	private ParametroTO parametroTO;
	
	
	@PostConstruct
	private void init(){
		String acao = getRequest().getParameter("acao");
		String parametro = getRequest().getParameter("parametro");
		
		if(acao != null && parametro != null){
			if(acao.equals("editar")){
				inicializarAlterar(Integer.valueOf(parametro));
			}
		}else if(acao != null && parametro == null){
				inicializarIncluir();
		}else{
			pesquisar();
		}
	}

	
	
	
	public void pesquisar(){

	}
	
	
	
	
	private void inicializarAlterar(Integer id){
		getParametroTO().setParametro(parametroService.recuperar(id));
	}
	
	private void inicializarIncluir(){
		getParametroTO().setParametro(new Parametro());
	}
	
	
	
	
	
	
	
	
	
		
	
	public void gravar(){
		if(getParametroTO().getParametro().getId() == null){
			parametroService.incluir(getParametroTO().getParametro());
		}else{
			parametroService.alterar(getParametroTO().getParametro());
		}
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));
	}
	

	
	
	

	public ParametroTO getParametroTO() {
		if (parametroTO == null) {
			parametroTO = new ParametroTO();
		}
		return parametroTO;
	}

	public void setParametroTO(ParametroTO parametroTO) {
		this.parametroTO = parametroTO;
	}
	
	
	


	
	
}


