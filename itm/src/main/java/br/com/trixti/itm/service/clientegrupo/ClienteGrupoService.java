package br.com.trixti.itm.service.clientegrupo;

import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.clientegrupo.ClienteGrupoDAO;
import br.com.trixti.itm.entity.ClienteGrupo;
import br.com.trixti.itm.service.AbstractService;

public class ClienteGrupoService extends AbstractService<ClienteGrupo> {

	
	private @Inject ClienteGrupoDAO clienteGrupoDAO;
	
	@Override
	public AbstractDAO<ClienteGrupo> getDAO() {
		return clienteGrupoDAO;
	}

}
