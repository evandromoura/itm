package br.com.trixti.itm.service.servico;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.servico.ServicoDAO;
import br.com.trixti.itm.entity.Servico;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class ServicoService extends AbstractService<Servico> {

	private @Inject ServicoDAO servicoDAO;
	
	@Override
	public AbstractDAO<Servico> getDAO() {
		return servicoDAO;
	}

}






