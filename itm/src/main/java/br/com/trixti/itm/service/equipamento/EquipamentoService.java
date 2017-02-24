package br.com.trixti.itm.service.equipamento;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.equipamento.EquipamentoDAO;
import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class EquipamentoService extends AbstractService<Equipamento> {

	private @Inject EquipamentoDAO equipamentoDAO;
	
	@Override
	public AbstractDAO<Equipamento> getDAO() {
		return equipamentoDAO;
	}
	
}
