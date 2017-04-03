package br.com.trixti.itm.service.radcheck;

import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.radcheck.RadcheckDAO;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Radcheck;
import br.com.trixti.itm.service.AbstractService;

public class RadcheckService extends AbstractService<Radcheck> {

	
	private @Inject RadcheckDAO radcheckDAO;
	
	@Override
	public AbstractDAO<Radcheck> getDAO() {
		return radcheckDAO;
	}
	
	public void excluirPorUsername(String username){
		radcheckDAO.excluirPorUsername(username);
	}

}
