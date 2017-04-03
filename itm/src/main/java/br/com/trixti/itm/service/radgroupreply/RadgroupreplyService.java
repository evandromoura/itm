package br.com.trixti.itm.service.radgroupreply;

import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.radgroupreply.RadgroupreplyDAO;
import br.com.trixti.itm.entity.Radgroupreply;
import br.com.trixti.itm.service.AbstractService;

public class RadgroupreplyService extends AbstractService<Radgroupreply> {

	
	private @Inject RadgroupreplyDAO radgroupreplyDAO;
	
	@Override
	public AbstractDAO<Radgroupreply> getDAO() {
		return radgroupreplyDAO;
	}
	
	public void excluirPorGroupname(String groupname){
		radgroupreplyDAO.excluirPorGroupname(groupname);
	}

}
