package br.com.trixti.itm.service.contratogrupo;

import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.contratogrupo.ContratoGrupoDAO;
import br.com.trixti.itm.entity.ContratoGrupo;
import br.com.trixti.itm.service.AbstractService;

public class ContratoGrupoService extends AbstractService<ContratoGrupo> {

	
	private @Inject ContratoGrupoDAO contratoGrupoDAO;
	
	@Override
	public AbstractDAO<ContratoGrupo> getDAO() {
		return contratoGrupoDAO;
	}

}
