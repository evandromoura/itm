package br.com.trixti.itm.service.uf;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.uf.UfDAO;
import br.com.trixti.itm.entity.Uf;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class UfService extends AbstractService<Uf> {

	private @Inject UfDAO ufDAO;
	
	@Override
	public AbstractDAO<Uf> getDAO() {
		return ufDAO;
	}

}






