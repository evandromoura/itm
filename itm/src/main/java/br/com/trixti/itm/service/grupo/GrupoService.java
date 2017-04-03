package br.com.trixti.itm.service.grupo;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.grupo.GrupoDAO;
import br.com.trixti.itm.entity.Grupo;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.freeradius.FreeRadiusService;


@Stateless
public class GrupoService extends AbstractService<Grupo> {

	private @Inject GrupoDAO grupoDAO;
	private @Inject FreeRadiusService freeRadiusService;
	
	@Override
	public AbstractDAO<Grupo> getDAO() {
		return grupoDAO;
	}

	public List<Grupo> pesquisar(Grupo grupo) {
		return grupoDAO.pesquisar(grupo);
	}

	@Override
	public void incluir(Grupo entidade) {
		super.incluir(entidade);
		freeRadiusService.sincronizarGrupo(entidade);
	}

	@Override
	public void alterar(Grupo entidade) {
		super.alterar(entidade);
		freeRadiusService.sincronizarGrupo(entidade);
	}
	
}
