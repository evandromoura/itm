package br.com.trixti.itm.service.parametro;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.parametro.ParametroDAO;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.service.AbstractService;

@Stateless
public class ParametroService extends AbstractService<Parametro> {

	private @Inject ParametroDAO parametroDAO;
	
	@Override
	public AbstractDAO<Parametro> getDAO() {
		return parametroDAO;
	}

	public Parametro recuperarParametro() {

		return parametroDAO.recuperarParametro();
	}

	
	
}
