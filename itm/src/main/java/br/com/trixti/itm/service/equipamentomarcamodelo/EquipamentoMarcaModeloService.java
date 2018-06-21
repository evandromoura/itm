package br.com.trixti.itm.service.equipamentomarcamodelo;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.equipamentomarcamodelo.EquipamentoMarcaModeloDAO;
import br.com.trixti.itm.entity.EquipamentoMarca;
import br.com.trixti.itm.entity.EquipamentoMarcaModelo;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class EquipamentoMarcaModeloService extends AbstractService<EquipamentoMarcaModelo> {

	private @Inject EquipamentoMarcaModeloDAO equipamentoMarcaModeloDAO;
	
	@Override
	public AbstractDAO<EquipamentoMarcaModelo> getDAO() {
		return equipamentoMarcaModeloDAO;
	}

	public List<EquipamentoMarcaModelo> pesquisarPorMarca(EquipamentoMarca marca) {
		return equipamentoMarcaModeloDAO.pesquisarPorMarca(marca);
	}
	
}
