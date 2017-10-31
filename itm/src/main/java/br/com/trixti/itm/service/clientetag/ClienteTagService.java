package br.com.trixti.itm.service.clientetag;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.clientetag.ClienteTagDAO;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.ClienteTag;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class ClienteTagService extends AbstractService<ClienteTag> {

	private @Inject ClienteTagDAO clienteTagDAO;
	
	
	@Override
	public AbstractDAO<ClienteTag> getDAO() {
		return clienteTagDAO;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void excluirPorCliente(Cliente cliente){
		clienteTagDAO.excluirPorCliente(cliente);
	}

}






