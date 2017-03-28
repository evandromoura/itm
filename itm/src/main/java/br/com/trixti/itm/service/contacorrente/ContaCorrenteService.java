package br.com.trixti.itm.service.contacorrente;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.contacorrente.ContaCorrenteDAO;
import br.com.trixti.itm.entity.ContaCorrente;
import br.com.trixti.itm.service.AbstractService;

@Stateless
public class ContaCorrenteService extends AbstractService<ContaCorrente> {

	private @Inject ContaCorrenteDAO contaCorrenteDAO;

	@Override
	public AbstractDAO<ContaCorrente> getDAO() {
		return contaCorrenteDAO;
	}
	
	
}
