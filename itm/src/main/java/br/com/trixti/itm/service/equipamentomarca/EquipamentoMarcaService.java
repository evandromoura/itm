package br.com.trixti.itm.service.equipamentomarca;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.equipamentomarca.EquipamentoMarcaDAO;
import br.com.trixti.itm.entity.EquipamentoMarca;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class EquipamentoMarcaService extends AbstractService<EquipamentoMarca> {

	private @Inject EquipamentoMarcaDAO equipamentoMarcaDAO;
	
	@Override
	public AbstractDAO<EquipamentoMarca> getDAO() {
		return equipamentoMarcaDAO;
	}
	
}
