package br.com.trixti.itm.controller.cliente;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.google.gson.Gson;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Cidade;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.ClienteTag;
import br.com.trixti.itm.entity.ContaCorrente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.entity.Produto;
import br.com.trixti.itm.entity.Tag;
import br.com.trixti.itm.entity.Uf;
import br.com.trixti.itm.enums.TipoPessoaEnum;
import br.com.trixti.itm.infra.security.annotations.CustomIdentity;
import br.com.trixti.itm.infra.security.annotations.SuporteNivel1;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.service.tag.TagService;
import br.com.trixti.itm.to.ClienteTO;

@Named
@ViewScoped
@SuporteNivel1
public class ClienteController extends AbstractController<Cliente> implements Serializable{
	
	private static final long serialVersionUID = -5045680464698139930L;

	private ClienteTO clienteTO;
	
	private @Inject ClienteService clienteService;
	private @Inject ContratoService contratoService;
	private @Inject CustomIdentity customIdentity;
	private @Inject TagService tagService;
	
	@PostConstruct
	
	private void init(){
//		BigDecimal big = BigDecimal.ZERO;
//		big.intValue()
		
		String acao = getRequest().getParameter("acao");
		String parametro =getRequest().getParameter("parametro"); 
		String filtro = getRequest().getParameter("filtro");
		Cliente clientePesquisa = new Gson().fromJson(filtro,Cliente.class);
		getClienteTO().setClientePesquisa(clientePesquisa);
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
		getClienteTO().getCliente().setClienteTags(new ArrayList<ClienteTag>());
		getClienteTO().getCliente().setUf(new Uf());
		getClienteTO().getCliente().setCidade(new Cidade());
		getClienteTO().setTags(tagService.listar());
	}
	
	private void inicializarAlterar(Serializable id){
		getClienteTO().setCliente(clienteService.recuperar(id));
		getClienteTO().getContrato().setContaCorrente(new ContaCorrente());
		getClienteTO().getContrato().setGeraBoleto(true);
		getClienteTO().getContratoAcao().setGeraMultaCancelamento(true);
		getClienteTO().setTags(tagService.listar());
	}
	
//	@Admin
	public void pesquisar(){
		System.out.println(new Gson().toJson(getClienteTO().getClientePesquisa()));
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
			Contrato contrato = contratoService.recuperarCompleto(getClienteTO().getContratoAcao().getId());
			contrato.setGeraMultaCancelamento(getClienteTO().getContratoAcao().isGeraMultaCancelamento());
			contratoService.cancelar(contrato);
			String mensagem = getMessage("label.global.cancelarsucesso");
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem));
			init();
		}	
	}
	
	
	public void desbloquearContrato(){
		if(getClienteTO().getContratoAcao() != null){
			Contrato contrato = contratoService.recuperarCompleto(getClienteTO().getContratoAcao().getId());
			contratoService.desbloquear(contrato);
			String mensagem = getMessage("label.global.desbloquearsucesso");
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem));
			init();
		}	
	}
	
	public String cancelar(){
		getClienteTO().setCliente(null);
		return "/pages/cliente/cliente_list.xhtml?faces-redirect=true";
	}
	
	public void excluir(){
		Cliente cliente =  clienteService.recuperar(getClienteTO().getCliente().getId());
		cliente.setUsuarioUltimaAtualizacao(customIdentity.getUsuario());
		clienteService.excluir(cliente);
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
	
	public void adicionarTag(){
		for(Tag tag:getClienteTO().getTags()){
			if(tag.isSelecionado()){
				getClienteTO().getCliente().getClienteTags().add(new ClienteTag(getClienteTO().getCliente(), tag));
			}
		}
	}
	
	public void excluirTag(ClienteTag clienteTag){
		getClienteTO().getCliente().getClienteTags().remove(clienteTag);
	}
	
	public static void main(String[] args) {
		String valor = "{nome:Evandro,email:evandromoura@gmail.com,clienteTags:[ABERTO]}";
		
		new Gson().fromJson(valor, Object.class);
	}
	
	
}
