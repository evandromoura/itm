package br.com.trixti.itm.controller.usuario;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Usuario;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.service.usuario.UsuarioService;
import br.com.trixti.itm.to.UsuarioTO;



@ViewScoped
@ManagedBean
@Admin
public class UsuarioController extends AbstractController<Usuario> {
	
	
	private @Inject UsuarioService usuarioService;
	private UsuarioTO usuarioTO;
	
	
	
	
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
		getUsuarioTO().setUsuarios(
				usuarioService.pesquisar(getUsuarioTO().getUsuarioPesquisa()));
	}
	
		
	
	public String gravar(){
		if(getUsuarioTO().getUsuario().getId() == null){
			usuarioService.incluir(getUsuarioTO().getUsuario());
		}else{
			usuarioService.alterar(getUsuarioTO().getUsuario());
		}
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));
		return "sucesso";
	}
	
	private void inicializarAlterar(Integer id){
		getUsuarioTO().setUsuario(usuarioService.recuperar(id));
	}
	
	private void inicializarIncluir(){
		getUsuarioTO().setUsuario(new Usuario());
	}
	
	
	

	public UsuarioTO getUsuarioTO() {
		if (usuarioTO == null) {
			usuarioTO = new UsuarioTO();
		}
		return usuarioTO;
	}

	public void setUsuarioTO(UsuarioTO usuarioTO) {
		this.usuarioTO = usuarioTO;
	}
	
	
	
	public void excluir(Usuario usuario){
		usuarioService.excluir(usuario);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi incluido na base"));
		pesquisar();
	}

	
	
}


