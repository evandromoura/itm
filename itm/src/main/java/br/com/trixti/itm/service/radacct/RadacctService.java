package br.com.trixti.itm.service.radacct;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.radacct.RadacctDAO;
import br.com.trixti.itm.entity.Radacct;
import br.com.trixti.itm.service.AbstractService;

@Stateless
public class RadacctService extends AbstractService<Radacct> {
	
	private @Inject RadacctDAO radacctDAO;
	
	@Override
	public AbstractDAO<Radacct> getDAO() {
		return radacctDAO;
	}
	
	public List<Radacct> pesquisarUltimosPorUsername(String username){
		return radacctDAO.pesquisarUltimosPorUsername(username);
	}

}
