package br.com.trixti.itm.controller.cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.ClienteEquipamento;
import br.com.trixti.itm.entity.ClienteGrupo;
import br.com.trixti.itm.entity.ClienteProduto;
import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.entity.Produto;
import br.com.trixti.itm.enums.TipoPessoaEnum;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.service.equipamento.EquipamentoService;
import br.com.trixti.itm.service.grupo.GrupoService;
import br.com.trixti.itm.service.produto.ProdutoService;
import br.com.trixti.itm.to.ClienteTO;
import br.com.trixti.itm.util.UtilData;


@ViewScoped
@ManagedBean
public class ClienteController extends AbstractController<Cliente> {
	
	private ClienteTO clienteTO;
	
	private @Inject ClienteService clienteService;
	private @Inject ProdutoService produtoService;
	private @Inject EquipamentoService equipamentoService;
	private @Inject GrupoService grupoService;

	
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
		getClienteTO().getCliente().setClienteProdutos(new ArrayList<ClienteProduto>());
		getClienteTO().setProduto(new Produto());
		
		getClienteTO().setEquipamento(new Equipamento());
		getClienteTO().getCliente().setClienteEquipamentos(new ArrayList<ClienteEquipamento>());
		
		getClienteTO().setGrupo(new Grupo());
		getClienteTO().getCliente().setClienteGrupos(new ArrayList<ClienteGrupo>());
	}
	
	private void inicializarAlterar(Serializable id){
		getClienteTO().setCliente(clienteService.recuperar(id));
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
		pesquisar();
		return "sucesso";
	}
	
	public String cancelar(){
		getClienteTO().setCliente(null);
		pesquisar();
		return "cancelar";
	}
	
	public void excluir(Cliente cliente){
		clienteService.excluir(cliente);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi excluido na base"));
		pesquisar();
	}
	
	public void adicionarProduto(){
		ClienteProduto clienteProduto = new ClienteProduto();
		clienteProduto.setCliente(getClienteTO().getCliente());
		Produto produto = produtoService.recuperar(getClienteTO().getProduto().getId());
		clienteProduto.setValor(produto.getValor());
		clienteProduto.setValorBase(produto.getValor());
		clienteProduto.setProduto(produto);
		
		Date dataLocal = new Date();
		clienteProduto.setDataCriacao(dataLocal);
		clienteProduto.setDataInicio(dataLocal);
		UtilData utilData = new UtilData();
		clienteProduto.setDataFim(utilData.adicionarAnos(dataLocal, 1));
		getClienteTO().getCliente().getClienteProdutos().add(clienteProduto);
	}
	
	public void removerProduto(ClienteProduto clienteProduto){
		getClienteTO().getCliente().getClienteProdutos().remove(clienteProduto);
	}
	
	
	public void adicionarEquipamento(){
		ClienteEquipamento clienteEquipamento = new ClienteEquipamento();
		clienteEquipamento.setCliente(getClienteTO().getCliente());
		Equipamento equipamento = equipamentoService.recuperar(getClienteTO().getEquipamento().getId());
		Date dataLocal = new Date();
		clienteEquipamento.setDataCriacao(dataLocal);
		clienteEquipamento.setEquipamento(equipamento);
		getClienteTO().getCliente().getClienteEquipamentos().add(clienteEquipamento);
	}
	
	public void removerEquipamento(ClienteEquipamento clienteEquipamento){
		getClienteTO().getCliente().getClienteEquipamentos().remove(clienteEquipamento);
	}
	
	
	public void adicionarGrupo(){
		ClienteGrupo clienteGrupo = new ClienteGrupo();
		clienteGrupo.setCliente(getClienteTO().getCliente());
		Grupo grupo = grupoService.recuperar(getClienteTO().getGrupo().getId());
		Date dataLocal = new Date();
		clienteGrupo.setDataCriacao(dataLocal);
		clienteGrupo.setGrupo(grupo);
		getClienteTO().getCliente().getClienteGrupos().add(clienteGrupo);
	}
	
	public void removerGrupo(ClienteGrupo clienteGrupo){
		getClienteTO().getCliente().getClienteGrupos().remove(clienteGrupo);
	}
	
	public void ativarDesativarClienteProduto(ClienteProduto clienteProduto){
		if(clienteProduto.getDataExclusao() != null){
			clienteProduto.setDataExclusao(null);
		}else{
			clienteProduto.setDataExclusao(new Date());
		}	
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
