package br.com.trixti.itm.service.contratoproduto;

import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.contratoproduto.ContratoProdutoDAO;
import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.service.AbstractService;

public class ContratoProdutoService extends AbstractService<ContratoProduto> {

	
	private @Inject ContratoProdutoDAO contratoProdutoDAO;
	
	@Override
	public AbstractDAO<ContratoProduto> getDAO() {
		return contratoProdutoDAO;
	}

}
