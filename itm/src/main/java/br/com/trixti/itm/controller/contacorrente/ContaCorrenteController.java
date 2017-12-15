package br.com.trixti.itm.controller.contacorrente;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.ContaCorrente;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.infra.security.annotations.Financeiro;
import br.com.trixti.itm.service.contacorrente.ContaCorrenteService;
import br.com.trixti.itm.to.ContaCorrenteTO;


@Named
@ViewScoped
@Admin
public class ContaCorrenteController extends AbstractController<ContaCorrente> implements Serializable{
	
	private static final long serialVersionUID = 6591103680400220607L;
	private @Inject ContaCorrenteService contaCorrenteService;
	private ContaCorrenteTO contaCorrenteTO;
	
	
	@PostConstruct
	private void init(){
		String acao = getRequest().getParameter("acao");
		String parametro = getRequest().getParameter("parametro");
		if((acao != null && acao.equals("editar"))&&(parametro != null)){
			inicializarAlterar(Integer.valueOf(parametro));
		}else if(acao != null && acao.equals("new")){
			inicializarIncluir();
		}else{
			getContaCorrenteTO().setContas(contaCorrenteService.listar());
		}
		
	}
	
	@Financeiro
	public void pesquisar(){
		getContaCorrenteTO().setContas(
				contaCorrenteService.pesquisar(getContaCorrenteTO().getContaPesquisa()));
	}
	
	
	public void gravar(){
		if(getContaCorrenteTO().getConta().getId() == null){
			contaCorrenteService.incluir(getContaCorrenteTO().getConta());
		}else{
			contaCorrenteService.alterar(getContaCorrenteTO().getConta());
		}
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));
	}
	
	private void inicializarAlterar(Integer id){
		getContaCorrenteTO().setConta(contaCorrenteService.recuperar(id));
	}
	
	private void inicializarIncluir(){
		getContaCorrenteTO().setConta(new ContaCorrente());
	}
	
	
	

	public ContaCorrenteTO getContaCorrenteTO() {
		if (contaCorrenteTO == null) {
			contaCorrenteTO = new ContaCorrenteTO();
		}
		return contaCorrenteTO;
	}

	public void setContaCorrenteTO(ContaCorrenteTO contaCorrenteTO) {
		this.contaCorrenteTO = contaCorrenteTO;
	}
	
	

	
	
}


