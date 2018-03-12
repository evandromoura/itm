package br.com.trixti.itm.controller.centrocusto;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.CentroCusto;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.service.centrocusto.CentroCustoService;
import br.com.trixti.itm.to.CentroCustoTO;



@Named
@ViewScoped
@Admin
public class CentroCustoController extends AbstractController<CentroCusto> implements Serializable {
	
	
	private static final long serialVersionUID = -3430900005102330317L;
	private @Inject CentroCustoService centroCustoService;
	private CentroCustoTO centroCustoTO;
	
	
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
		getCentroCustoTO().setCentroCustos(
				centroCustoService.listar());
	}
	
	public String gravar(){
		if(getCentroCustoTO().getCentroCusto().getId() == null){
			centroCustoService.incluir(getCentroCustoTO().getCentroCusto());
		}else{
			centroCustoService.alterar(getCentroCustoTO().getCentroCusto());
		}
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));
		return "sucesso";
	}
	
	private void inicializarAlterar(Integer id){
		getCentroCustoTO().setCentroCusto(centroCustoService.recuperar(id));
	}
	
	private void inicializarIncluir(){
		getCentroCustoTO().setCentroCusto(new CentroCusto());
	}
	
	
	

	public CentroCustoTO getCentroCustoTO() {
		if (centroCustoTO == null) {
			centroCustoTO = new CentroCustoTO();
		}
		return centroCustoTO;
	}

	public void setCentroCustoTO(CentroCustoTO centroCustoTO) {
		this.centroCustoTO = centroCustoTO;
	}
	
	
	
	public void excluir(CentroCusto centroCusto){
		centroCustoService.excluir(centroCusto);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi incluido na base"));
		pesquisar();
	}

	
	
}


