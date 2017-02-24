package br.com.trixti.itm.service.clienteequipamento;

import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.clienteequipamento.ClienteEquipamentoDAO;
import br.com.trixti.itm.entity.ClienteEquipamento;
import br.com.trixti.itm.service.AbstractService;

public class ClienteEquipamentoService extends AbstractService<ClienteEquipamento> {

	
	private @Inject ClienteEquipamentoDAO clienteEquipamentoDAO;
	
	@Override
	public AbstractDAO<ClienteEquipamento> getDAO() {
		return clienteEquipamentoDAO;
	}

}
