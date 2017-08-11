package br.com.trixti.itm.controller.cliente;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.ContaCorrente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.entity.Produto;
import br.com.trixti.itm.enums.TipoPessoaEnum;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.infra.security.annotations.CustomIdentity;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.to.ClienteTO;


@ManagedBean
@ViewScoped
@Admin
public class ClienteController extends AbstractController<Cliente> {
	
	private ClienteTO clienteTO;
	
	private @Inject ClienteService clienteService;
	private @Inject ContratoService contratoService;
	private @Inject CustomIdentity customIdentity;
	
	@PostConstruct
	private void init(){
		
		String acao = getRequest().getParameter("acao");
		String parametro =getRequest().getParameter("parametro"); 
		
		if(acao != null && acao.equals("novo")){
			inicializarIncluir();
		}else if(acao != null && parametro != null && acao.equals("editar")){
			inicializarAlterar(Integer.valueOf(parametro));
		}
		else{
			getClienteTO().getClientePesquisa().setGrupo(new Grupo());
			getClienteTO().getClientePesquisa().setProduto(new Produto());
			getClienteTO().setClientes(clienteService.pesquisar(getClienteTO().getClientePesquisa()));
		}	
	}
	
	private void inicializarIncluir(){
		getClienteTO().setCliente(new Cliente());
		getClienteTO().getCliente().setTipoPessoa(TipoPessoaEnum.FISICA);
		getClienteTO().getContrato().setContaCorrente(new ContaCorrente());
		getClienteTO().getContrato().setGeraBoleto(true);
	}
	
	private void inicializarAlterar(Serializable id){
		getClienteTO().setCliente(clienteService.recuperar(id));
		getClienteTO().getContrato().setContaCorrente(new ContaCorrente());
		getClienteTO().getContrato().setGeraBoleto(true);
	}
	
//	@Admin
	public void pesquisar(){
		getClienteTO().setClientes(clienteService.pesquisar(getClienteTO().getClientePesquisa()));
		if(getClienteTO().getClientes().isEmpty()){
			String mensagem = getMessage("label.global.nenhumregistroencontrado");
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem));
		}
	}
	
	public String gravar(){
		getClienteTO().getCliente().setUsuarioUltimaAtualizacao(customIdentity.getUsuario());
		if(getClienteTO().getCliente().getId() == null){
			clienteService.incluir(getClienteTO().getCliente());
			 String mensagem = getMessage("label.global.cadastrosucesso");
			 getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem));
			 getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
		}else{
			String mensagem = getMessage("label.global.alterarsucesso");
			clienteService.alterar(getClienteTO().getCliente());
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem));
			getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
		}
		return "/pages/cliente/cliente_list.xhtml?faces-redirect=true";
	}
	
	public void excluirContrato(){
		if(getClienteTO().getContratoAcao() != null){
			contratoService.excluir(contratoService.recuperarCompleto(getClienteTO().getContratoAcao().getId()));
			getClienteTO().getCliente().getContratos().remove(getClienteTO().getContratoAcao());
			String mensagem = getMessage("label.global.excluirsucesso");
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem));
		}	
	}
	
	public void cancelarContrato(){
		if(getClienteTO().getContratoAcao() != null){
			contratoService.cancelar(contratoService.recuperarCompleto(getClienteTO().getContratoAcao().getId()));
			String mensagem = getMessage("label.global.cancelarsucesso");
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem));
			init();
		}	
	}
	
	public String cancelar(){
		getClienteTO().setCliente(null);
		return "/pages/cliente/cliente_list.xhtml?faces-redirect=true";
	}
	
	public void excluir(){
		clienteService.excluir(clienteService.recuperar(getClienteTO().getCliente().getId()));
		String mensagem = getMessage("label.global.excluirsucesso");
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem));
		pesquisar();
	}
	
	public String adicionarContrato(){
		if(getClienteTO().getCliente().getId() == null){
			clienteService.incluir(getClienteTO().getCliente());
		}else{
			clienteService.alterar(getClienteTO().getCliente());
		}
		getClienteTO().getContrato().setCliente(getClienteTO().getCliente());
		contratoService.incluir(getClienteTO().getContrato());
		return "/pages/contrato/contrato_form.xhtml?faces-redirect=true&id="+getClienteTO().getContrato().getId();
		
	}
	

	public ClienteTO getClienteTO() {
		if (clienteTO == null) {
			clienteTO = new ClienteTO();
		}
		return clienteTO;
	}

	public void setClienteTO(ClienteTO clienteTO) {
		this.clienteTO = clienteTO;
	}
	
	
}
