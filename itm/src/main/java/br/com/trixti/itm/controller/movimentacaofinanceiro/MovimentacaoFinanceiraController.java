package br.com.trixti.itm.controller.movimentacaofinanceiro;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.CentroCusto;
import br.com.trixti.itm.entity.MovimentacaoFinanceira;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.service.movimentacaofinanceira.MovimentacaoFinanceiraService;
import br.com.trixti.itm.to.MovimentacaoFinanceiraTO;



@Named
@ViewScoped
@Admin
public class MovimentacaoFinanceiraController extends AbstractController<MovimentacaoFinanceira> implements Serializable {
	
	
	private static final long serialVersionUID = -3430900005102330317L;
	private @Inject MovimentacaoFinanceiraService movimentacaoFinanceiraService;
	private MovimentacaoFinanceiraTO movimentacaoFinanceiraTO;
	
	
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
		getMovimentacaoFinanceiraTO().setMovimentacaoFinanceiras(
				movimentacaoFinanceiraService.listar());
	}
	
	public String gravar(){
		if(getMovimentacaoFinanceiraTO().getMovimentacaoFinanceira().getId() == null){
			movimentacaoFinanceiraService.incluir(getMovimentacaoFinanceiraTO().getMovimentacaoFinanceira());
		}else{
			movimentacaoFinanceiraService.alterar(getMovimentacaoFinanceiraTO().getMovimentacaoFinanceira());
		}
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));
		return "sucesso";
	}
	
	private void inicializarAlterar(Integer id){
		getMovimentacaoFinanceiraTO().setMovimentacaoFinanceira(movimentacaoFinanceiraService.recuperar(id));
	}
	
	private void inicializarIncluir(){
		getMovimentacaoFinanceiraTO().setMovimentacaoFinanceira(new MovimentacaoFinanceira());
		getMovimentacaoFinanceiraTO().getMovimentacaoFinanceira().setCentroCusto(new CentroCusto());
	}
	

	public MovimentacaoFinanceiraTO getMovimentacaoFinanceiraTO() {
		if (movimentacaoFinanceiraTO == null) {
			movimentacaoFinanceiraTO = new MovimentacaoFinanceiraTO();
		}
		return movimentacaoFinanceiraTO;
	}

	public void setMovimentacaoFinanceiraTO(MovimentacaoFinanceiraTO movimentacaoFinanceiraTO) {
		this.movimentacaoFinanceiraTO = movimentacaoFinanceiraTO;
	}
	
	
	
	public void excluir(MovimentacaoFinanceira movimentacaoFinanceira){
		movimentacaoFinanceiraService.excluir(movimentacaoFinanceira);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi incluido na base"));
		pesquisar();
	}

	
	
}


