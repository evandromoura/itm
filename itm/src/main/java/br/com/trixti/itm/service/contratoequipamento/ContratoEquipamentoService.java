package br.com.trixti.itm.service.contratoequipamento;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.contratoequipamento.ContratoEquipamentoDAO;
import br.com.trixti.itm.entity.ContratoEquipamento;
import br.com.trixti.itm.service.AbstractService;

@Stateless
public class ContratoEquipamentoService extends AbstractService<ContratoEquipamento> {

	
	private @Inject ContratoEquipamentoDAO contratoEquipamentoDAO;
	
	@Override
	public AbstractDAO<ContratoEquipamento> getDAO() {
		return contratoEquipamentoDAO;
	}
	

}
