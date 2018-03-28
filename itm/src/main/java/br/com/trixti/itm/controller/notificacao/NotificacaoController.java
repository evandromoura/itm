package br.com.trixti.itm.controller.notificacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Notificacao;
import br.com.trixti.itm.entity.NotificacaoTag;
import br.com.trixti.itm.entity.Tag;
import br.com.trixti.itm.infra.security.annotations.SuporteNivel1;
import br.com.trixti.itm.service.notificacao.NotificacaoService;
import br.com.trixti.itm.service.tag.TagService;
import br.com.trixti.itm.to.NotificacaoTO;
import br.com.trixti.itm.util.UtilData;



@Named
@ViewScoped
@SuporteNivel1
public class NotificacaoController extends AbstractController<Notificacao> implements Serializable {
	
	private static final long serialVersionUID = -3430900005102330317L;
	private @Inject NotificacaoService notificacaoService;
	private NotificacaoTO notificacaoTO;
	private @Inject TagService tagService;
	
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
		getNotificacaoTO().setNotificacoes(notificacaoService.pesquisar(getNotificacaoTO().getNotificacaoPesquisa()));
	}
	
	public String gravar(){
		if(getNotificacaoTO().getNotificacao().getId() == null){
			notificacaoService.incluir(getNotificacaoTO().getNotificacao());
		}else{
			notificacaoService.alterar(getNotificacaoTO().getNotificacao());
		}
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));
		return "sucesso";
	}
	
	private void inicializarAlterar(Integer id){
		getNotificacaoTO().setNotificacao(notificacaoService.recuperar(id));
		getNotificacaoTO().setTags(tagService.listar());
	}
	
	private void inicializarIncluir(){
		getNotificacaoTO().setNotificacao(new Notificacao());
		getNotificacaoTO().setTags(tagService.listar());
		getNotificacaoTO().getNotificacao().setTags(new ArrayList<NotificacaoTag>());
		UtilData utilData = new UtilData();
		getNotificacaoTO().getNotificacao().setDataInicio(new Date());
		getNotificacaoTO().getNotificacao().setDataFim(utilData.adicionaDias(new Date(), 2));
	}
	

	public NotificacaoTO getNotificacaoTO() {
		if (notificacaoTO == null) {
			notificacaoTO = new NotificacaoTO();
		}
		return notificacaoTO;
	}

	public void setNotificacaoTO(NotificacaoTO notificacaoTO) {
		this.notificacaoTO = notificacaoTO;
	}
	
	
	
	public void excluir(Notificacao notificacao){
		notificacaoService.excluir(notificacao);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi incluido na base"));
		pesquisar();
	}
	
	
	public void excluirTag(NotificacaoTag notificacaoTag){
		getNotificacaoTO().getNotificacao().getTags().remove(notificacaoTag);
	}
	
	
	public void adicionarTag(){
		for(Tag tag:getNotificacaoTO().getTags()){
			if(tag.isSelecionado()){
				getNotificacaoTO().getNotificacao().getTags().add(new NotificacaoTag(getNotificacaoTO().getNotificacao(), tag));
			}
		}
	}
	
}


