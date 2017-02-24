package br.com.trixti.itm.service.grupo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.grupo.GrupoDAO;
import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class GrupoService extends AbstractService<Grupo> {

	private @Inject GrupoDAO grupoDAO;
	
	@Override
	public AbstractDAO<Grupo> getDAO() {
		return grupoDAO;
	}
	
}
