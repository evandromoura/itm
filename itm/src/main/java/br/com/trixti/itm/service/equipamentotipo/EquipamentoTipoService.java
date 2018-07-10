package br.com.trixti.itm.service.equipamentotipo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.equipamentotipo.EquipamentoTipoDAO;
import br.com.trixti.itm.entity.EquipamentoTipo;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class EquipamentoTipoService extends AbstractService<EquipamentoTipo> {

	private @Inject EquipamentoTipoDAO equipamentoTipoDAO;
	
	@Override
	public AbstractDAO<EquipamentoTipo> getDAO() {
		return equipamentoTipoDAO;
	}


	
}
