package br.com.trixti.itm.service.centrocusto;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.centrocusto.CentroCustoDAO;
import br.com.trixti.itm.entity.CentroCusto;
import br.com.trixti.itm.entity.TipoCentroCusto;
import br.com.trixti.itm.service.AbstractService;

@Stateless
public class CentroCustoService extends AbstractService<CentroCusto> {

	private @Inject CentroCustoDAO centroCustoDAO;

	@Override
	public AbstractDAO<CentroCusto> getDAO() {
		return centroCustoDAO;
	}

	public CentroCusto recuperarPrincipal(TipoCentroCusto mensalidade) {
		return centroCustoDAO.recuperarPrincipal(mensalidade);
	}

}
