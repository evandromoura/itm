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
import br.com.trixti.itm.enums.TipoPessoaEnum;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.to.ClienteTO;


@ViewScoped
@ManagedBean
public class ClienteController extends AbstractController<Cliente> {
	
	private ClienteTO clienteTO;
	
	private @Inject ClienteService clienteService;
	private @Inject ContratoService contratoService;

	
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
			getClienteTO().setClientes(clienteService.pesquisar(getClienteTO().getClientePesquisa()));
		}	
	}
	
	private void inicializarIncluir(){
		getClienteTO().setCliente(new Cliente());
		getClienteTO().getCliente().setTipoPessoa(TipoPessoaEnum.FISICA);
		getClienteTO().getContrato().setContaCorrente(new ContaCorrente());
	}
	
	private void inicializarAlterar(Serializable id){
		getClienteTO().setCliente(clienteService.recuperar(id));
		getClienteTO().getContrato().setContaCorrente(new ContaCorrente());
	}
	
	public void pesquisar(){
		getClienteTO().setClientes(clienteService.pesquisar(getClienteTO().getClientePesquisa()));
		if(getClienteTO().getClientes().isEmpty()){
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhum Registro Encontrado", "Refaça a pesquisa"));
		}
	}
	
	public String gravar(){
		if(getClienteTO().getCliente().getId() == null){
			clienteService.incluir(getClienteTO().getCliente());
			 getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));  
		}else{
			clienteService.alterar(getClienteTO().getCliente());
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alterado com Sucesso", "O Registro foi alterado na base"));
		}
		return "/pages/cliente/cliente_list.xhtml?faces-redirect=true";
	}
	
	public void excluirContrato(Contrato contrato){
		contratoService.excluir(contrato);
		getClienteTO().getCliente().getContratos().remove(contrato);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi excluido na base"));
	}
	
	public String cancelar(){
		getClienteTO().setCliente(null);
		return "/pages/cliente/cliente_list.xhtml?faces-redirect=true";
	}
	
	public void excluir(){
		clienteService.excluir(clienteService.recuperar(getClienteTO().getCliente().getId()));
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi excluido na base"));
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
