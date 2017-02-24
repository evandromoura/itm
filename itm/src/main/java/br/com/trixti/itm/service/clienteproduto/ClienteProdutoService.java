package br.com.trixti.itm.service.clienteproduto;

import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.clienteproduto.ClienteProdutoDAO;
import br.com.trixti.itm.entity.ClienteProduto;
import br.com.trixti.itm.service.AbstractService;

public class ClienteProdutoService extends AbstractService<ClienteProduto> {

	
	private @Inject ClienteProdutoDAO clienteProdutoDAO;
	
	@Override
	public AbstractDAO<ClienteProduto> getDAO() {
		return clienteProdutoDAO;
	}

}
