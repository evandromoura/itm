package br.com.trixti.itm.service.radusergroup;

import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.radusergroup.RadusergroupDAO;
import br.com.trixti.itm.entity.Radusergroup;
import br.com.trixti.itm.service.AbstractService;

public class RadusergroupService extends AbstractService<Radusergroup> {
	
	private @Inject RadusergroupDAO radusergroupDAO;
	
	@Override
	public AbstractDAO<Radusergroup> getDAO() {
		return radusergroupDAO;
	}
	
	public void excluirPorUsername(String username){
		radusergroupDAO.excluirPorUsername(username);
	}

}
