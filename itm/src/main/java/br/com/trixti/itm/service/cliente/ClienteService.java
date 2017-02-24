package br.com.trixti.itm.service.cliente;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.cliente.ClienteDAO;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.clienteequipamento.ClienteEquipamentoService;
import br.com.trixti.itm.service.clientegrupo.ClienteGrupoService;
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
	
	
	

}
