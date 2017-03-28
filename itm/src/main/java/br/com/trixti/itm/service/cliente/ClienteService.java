package br.com.trixti.itm.service.cliente;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.cliente.ClienteDAO;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.ClienteEquipamento;
import br.com.trixti.itm.entity.ClienteGrupo;
import br.com.trixti.itm.entity.ClienteProduto;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.clienteequipamento.ClienteEquipamentoService;
import br.com.trixti.itm.service.clientegrupo.ClienteGrupoService;
import br.com.trixti.itm.service.clientelancamento.ClienteLancamentoService;
import br.com.trixti.itm.service.clienteproduto.ClienteProdutoService;

/**
 * Classe que aplica a regra de negocio do caso de uso (Cliente)
 * @author evandro
 *
 *  RN01 - PESSOA FISICA : 
 *  	 - Ao criar um cliente, cadastrar nas respectivas tabelas do Free Radius (radcheck, radgroupcheck)
 */
@Stateless
public class ClienteService extends AbstractService<Cliente> {
	
	private @Inject ClienteDAO clienteDAO;
	private @Inject ClienteProdutoService clienteProdutoService;
	private @Inject ClienteGrupoService clienteGrupoService;
	private @Inject ClienteEquipamentoService clienteEquipamentoService;
	private @Inject ClienteLancamentoService clienteLancamentoService;
	
	@Override
	public AbstractDAO<Cliente> getDAO() {
		return clienteDAO;
	}
 
	public List<Cliente> pesquisar(Cliente clientePesquisa) {
		return clienteDAO.pesquisar(clientePesquisa);
	}

	@Override
	public void incluir(Cliente entidade) {
		entidade.setDataCriacao(new Date());
		super.incluir(entidade);
		clienteProdutoService.incluirLista(entidade.getClienteProdutos());
		clienteGrupoService.incluirLista(entidade.getClienteGrupos());
		clienteEquipamentoService.incluirLista(entidade.getClienteEquipamentos());
		/**
		 * TODO: Desenvolver a ligação com o Radius  (radcheck, radgroupcheck)
		 */
	}

	@Override
	public void alterar(Cliente entidade) {
		super.alterar(entidade);
		for(ClienteProduto clienteProduto:entidade.getClienteProdutos()){
			if(clienteProduto.getId() == null){
				clienteProdutoService.incluir(clienteProduto);
			}else{
				clienteProdutoService.alterar(clienteProduto);
				
			}	
		}
		for(ClienteGrupo clienteGrupo:entidade.getClienteGrupos()){
			if(clienteGrupo.getId() == null){
				clienteGrupoService.incluir(clienteGrupo);
			}else{
				clienteGrupoService.alterar(clienteGrupo);
			}	
		}
		
		for(ClienteEquipamento clienteEquipamento:entidade.getClienteEquipamentos()){
			if(clienteEquipamento.getId() == null){
				clienteEquipamentoService.incluir(clienteEquipamento);
			}else{
				clienteEquipamentoService.alterar(clienteEquipamento);
			}	
		}
		
		
	}

	public List<Cliente> listarAtivo() {
		return clienteDAO.listarAtivo();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public Cliente recuperar(Serializable id) {
		Cliente cliente =  super.recuperar(id);
		cliente.setLancamentos(clienteLancamentoService.pesquisarLancamentoAberto(cliente));
		return cliente;
	}
	

}
