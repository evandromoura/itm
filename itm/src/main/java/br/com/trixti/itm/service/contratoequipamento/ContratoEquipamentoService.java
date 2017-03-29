package br.com.trixti.itm.service.contratoequipamento;

import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.contratoequipamento.ContratoEquipamentoDAO;
import br.com.trixti.itm.entity.ContratoEquipamento;
import br.com.trixti.itm.service.AbstractService;

public class ContratoEquipamentoService extends AbstractService<ContratoEquipamento> {

	
	private @Inject ContratoEquipamentoDAO contratoEquipamentoDAO;
	
	@Override
	public AbstractDAO<ContratoEquipamento> getDAO() {
		return contratoEquipamentoDAO;
	}

}
