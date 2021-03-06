package br.com.trixti.itm.controller.tag;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Tag;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.service.tag.TagService;
import br.com.trixti.itm.to.TagTO;



@Named
@ViewScoped
@Admin
public class TagController extends AbstractController<Tag> implements Serializable{
	
	
	private static final long serialVersionUID = 7126475577875716222L;
	private @Inject TagService tagService;
	private TagTO tagTO;
	
	
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
		getTagTO().setTags(
				tagService.pesquisar(getTagTO().getTagPesquisa()));
	}
	
		
	
	public String gravar(){
		if(getTagTO().getTag().getId() == null){
			tagService.incluir(getTagTO().getTag());
		}else{
			tagService.alterar(getTagTO().getTag());
		}
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));
		return "sucesso";
	}
	
	private void inicializarAlterar(Integer id){
		getTagTO().setTag(tagService.recuperar(id));
	}
	
	private void inicializarIncluir(){
		getTagTO().setTag(new Tag());
	}
	
	
	

	public TagTO getTagTO() {
		if (tagTO == null) {
			tagTO = new TagTO();
		}
		return tagTO;
	}

	public void setTagTO(TagTO tagTO) {
		this.tagTO = tagTO;
	}
	
	
	
	public void excluir(Tag tag){
		tagService.excluir(tag);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi incluido na base"));
		pesquisar();
	}

	
	
}


