package br.com.trixti.itm.controller.custofixo;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.CentroCusto;
import br.com.trixti.itm.entity.CustoFixo;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.service.custofixo.CustoFixoService;
import br.com.trixti.itm.to.CustoFixoTO;



@Named
@ViewScoped
@Admin
public class CustoFixoController extends AbstractController<CustoFixo> implements Serializable {
	
	
	private static final long serialVersionUID = -3430900005102330317L;
	private @Inject CustoFixoService custoFixoService;
	private CustoFixoTO custoFixoTO;
	
	
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
		getCustoFixoTO().setCustoFixos(
				custoFixoService.listar());
	}
	
	public String gravar(){
		if(getCustoFixoTO().getCustoFixo().getId() == null){
			custoFixoService.incluir(getCustoFixoTO().getCustoFixo());
		}else{
			custoFixoService.alterar(getCustoFixoTO().getCustoFixo());
		}
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));
		return "sucesso";
	}
	
	private void inicializarAlterar(Integer id){
		getCustoFixoTO().setCustoFixo(custoFixoService.recuperar(id));
	}
	
	private void inicializarIncluir(){
		getCustoFixoTO().setCustoFixo(new CustoFixo());
		getCustoFixoTO().getCustoFixo().setCentroCusto(new CentroCusto());
	}
	

	public CustoFixoTO getCustoFixoTO() {
		if (custoFixoTO == null) {
			custoFixoTO = new CustoFixoTO();
		}
		return custoFixoTO;
	}

	public void setCustoFixoTO(CustoFixoTO custoFixoTO) {
		this.custoFixoTO = custoFixoTO;
	}
	
	
	
	public void excluir(CustoFixo custoFixo){
		custoFixoService.excluir(custoFixo);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi incluido na base"));
		pesquisar();
	}

	
	
}


