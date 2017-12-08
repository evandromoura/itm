package br.com.trixti.itm.service.radreply;

import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.radreply.RadreplyDAO;
import br.com.trixti.itm.entity.Radreply;
import br.com.trixti.itm.service.AbstractService;

public class RadreplyService extends AbstractService<Radreply> {

	
	private @Inject RadreplyDAO radcheckDAO;
	
	@Override
	public AbstractDAO<Radreply> getDAO() {
		return radcheckDAO;
	}
	
	public void excluirPorUsername(String username){
		radcheckDAO.excluirPorUsername(username);
	}

	public void excluirPorUsernameAttributeValue(String username, String attribute, String value) {
		radcheckDAO.excluirPorUsernameAttributeValue(username,attribute,value);
	}

}
