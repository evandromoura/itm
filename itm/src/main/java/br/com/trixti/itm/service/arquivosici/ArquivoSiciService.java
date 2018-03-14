package br.com.trixti.itm.service.arquivosici;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.arquivosici.ArquivoSiciDAO;
import br.com.trixti.itm.entity.ArquivoSici;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class ArquivoSiciService extends AbstractService<ArquivoSici> {

	private @Inject ArquivoSiciDAO centroCustoDAO;
	
	@Override
	public AbstractDAO<ArquivoSici> getDAO() {
		return centroCustoDAO;
	}

}






