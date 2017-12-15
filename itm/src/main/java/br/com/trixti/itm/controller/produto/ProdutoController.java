package br.com.trixti.itm.controller.produto;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Produto;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.service.produto.ProdutoService;
import br.com.trixti.itm.to.ProdutoTO;



@Named
@ViewScoped
@Admin
public class ProdutoController extends AbstractController<Produto> implements Serializable {
	
	
	private static final long serialVersionUID = -3430900005102330317L;
	private @Inject ProdutoService produtoService;
	private ProdutoTO produtoTO;
	
	
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
		getProdutoTO().setProdutos(
				produtoService.pesquisar(getProdutoTO().getProdutoPesquisa()));
	}
	
		
	
	public void gravar(){
		if(getProdutoTO().getProduto().getId() == null){
			produtoService.incluir(getProdutoTO().getProduto());
		}else{
			produtoService.alterar(getProdutoTO().getProduto());
		}
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));
	}
	
	private void inicializarAlterar(Integer id){
		getProdutoTO().setProduto(produtoService.recuperar(id));
	}
	
	private void inicializarIncluir(){
		getProdutoTO().setProduto(new Produto());
	}
	
	
	

	public ProdutoTO getProdutoTO() {
		if (produtoTO == null) {
			produtoTO = new ProdutoTO();
		}
		return produtoTO;
	}

	public void setProdutoTO(ProdutoTO produtoTO) {
		this.produtoTO = produtoTO;
	}
	
	
	
	public void excluir(Produto produto){
		produtoService.excluir(produto);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi incluido na base"));
		pesquisar();
	}

	
	
}


