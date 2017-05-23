package br.com.trixti.itm.service.remessa;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.remessa.RemessaDAO;
import br.com.trixti.itm.entity.Remessa;
import br.com.trixti.itm.service.AbstractService;

@Stateless
public class RemessaService extends AbstractService<Remessa>{

	private @Inject RemessaDAO remessaDAO;

	@Override
	public AbstractDAO<Remessa> getDAO() {
		return remessaDAO;
	}
	
	public List<Remessa> pesquisar(Remessa remessa){
		return remessaDAO.pesquisar(remessa);
	}

	public Remessa recuperarCompleto(Integer id) {
		return remessaDAO.recuperarCompleto(id);
	} 

	
}
