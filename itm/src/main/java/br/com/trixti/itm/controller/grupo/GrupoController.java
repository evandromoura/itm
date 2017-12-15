package br.com.trixti.itm.controller.grupo;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.entity.GrupoParametro;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.service.grupo.GrupoService;
import br.com.trixti.itm.to.GrupoTO;


@Named
@ViewScoped
@Admin
public class GrupoController extends AbstractController<Cliente> implements Serializable{
	
	private static final long serialVersionUID = -205864340129872714L;
	private GrupoTO grupoTO;
	private @Inject GrupoService grupoService;
	
	@PostConstruct
	private void init(){
		String acao = getRequest().getParameter("acao");
		String parametro = getRequest().getParameter("parametro");
		
		if(acao != null && parametro != null){
			if(acao.equals("editar")){
				inicializarEditar(Integer.valueOf(parametro));
			}
		}else if(acao != null && parametro == null){
				inicializarIncluir();
		}else{
			pesquisar();
		}
	}
	
	private void inicializarEditar(Integer id) {
		getGrupoTO().setGrupo(grupoService.recuperar(id));
	}
	
	private void inicializarIncluir(){
		getGrupoTO().setGrupo(new Grupo());
		getGrupoTO().getGrupo().setGrupoParametros(new ArrayList<GrupoParametro>());
	}

	public void pesquisar(){
		getGrupoTO().setGrupos(grupoService.pesquisar(getGrupoTO().getGrupoPesquisa()));
		if(getGrupoTO().getGrupos() == null || getGrupoTO().getGrupos().isEmpty()){
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhum registro encontrado!", "O Registro foi incluido na base"));
		}
	}
	
	//TODO Alterar para resources properties
	public String gravar(){
		if(getGrupoTO().getGrupo().getId() == null){
			grupoService.incluir(getGrupoTO().getGrupo());
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));
			getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
		}else{
			grupoService.alterar(getGrupoTO().getGrupo());
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alterado com Sucesso", "O Registro foi incluido na base"));
			getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
		}
		return "sucesso";
	}
	
	public void excluir(Grupo grupo){
		grupoService.excluir(grupo);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi incluido na base"));
		pesquisar();
	}
	
	
	public void adicionarGrupoParametro(){
		getGrupoTO().getGrupoParametro().setGrupo(getGrupoTO().getGrupo());
		getGrupoTO().getGrupo().getGrupoParametros().add(getGrupoTO().getGrupoParametro());
		getGrupoTO().setGrupoParametro(null);
	}
	
	
	public void excluirGrupoParametro(GrupoParametro grupoParametro){
		getGrupoTO().getGrupo().getGrupoParametros().remove(grupoParametro);
	}

	public GrupoTO getGrupoTO() {
		if (grupoTO == null) {
			grupoTO = new GrupoTO();
		}

		return grupoTO;
	}

	public void setGrupoTO(GrupoTO grupoTO) {
		this.grupoTO = grupoTO;
	}
	
	

	
	
}
