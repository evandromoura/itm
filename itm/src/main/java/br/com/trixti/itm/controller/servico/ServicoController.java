package br.com.trixti.itm.controller.servico;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Cidade;
import br.com.trixti.itm.entity.Servico;
import br.com.trixti.itm.entity.ServicoLocal;
import br.com.trixti.itm.entity.Uf;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.service.cidade.CidadeService;
import br.com.trixti.itm.service.servico.ServicoService;
import br.com.trixti.itm.to.ServicoTO;



@Named
@ViewScoped
@Admin
public class ServicoController extends AbstractController<Servico> implements Serializable {
	
	
	private static final long serialVersionUID = -3430900005102330317L;
	private @Inject ServicoService servicoService;
	private ServicoTO servicoTO;
	private @Inject CidadeService cidadeService;
	
	
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
//		getServicoTO().setServicos(
//				servicoService.pesquisar(getServicoTO().getServicoPesquisa()));
		getServicoTO().setServicos(
				servicoService.listar());
	}
	
	public String gravar(){
		if(getServicoTO().getServico().getId() == null){
			servicoService.incluir(getServicoTO().getServico());
		}else{
			servicoService.alterar(getServicoTO().getServico());
		}
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));
		return "sucesso";
	}
	
	private void inicializarAlterar(Integer id){
		getServicoTO().setServico(servicoService.recuperar(id));
		getServicoTO().setServicoLocal(new ServicoLocal());
		getServicoTO().getServicoLocal().setUf(new Uf());
		getServicoTO().getServicoLocal().setCidade(new Cidade());
	}
	
	private void inicializarIncluir(){
		getServicoTO().setServico(new Servico());
		getServicoTO().setServicoLocal(new ServicoLocal());
		getServicoTO().getServicoLocal().setUf(new Uf());
		getServicoTO().getServicoLocal().setCidade(new Cidade());
		getServicoTO().getServico().setLocais(new ArrayList<ServicoLocal>());
	}
	
	public void adicionarServicoLocal(){
		getServicoTO().getServicoLocal().setCidade(cidadeService.recuperar(getServicoTO().getServicoLocal().getCidade().getId()));
		getServicoTO().getServicoLocal().setServico(getServicoTO().getServico());
		getServicoTO().getServico().getLocais().add(getServicoTO().getServicoLocal());
		
		getServicoTO().setServicoLocal(null);
		getServicoTO().getServicoLocal().setUf(new Uf());
		getServicoTO().getServicoLocal().setCidade(new Cidade());
	}

	public ServicoTO getServicoTO() {
		if (servicoTO == null) {
			servicoTO = new ServicoTO();
		}
		return servicoTO;
	}

	public void setServicoTO(ServicoTO servicoTO) {
		this.servicoTO = servicoTO;
	}
	
	public void removerServicoLocal(ServicoLocal servicoLocal){
		getServicoTO().getServico().getLocais().remove(servicoLocal);
		
	}
	
	
	
	public void excluir(Servico servico){
		servicoService.excluir(servico);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi incluido na base"));
		pesquisar();
	}

	
	
}


