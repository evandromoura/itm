package br.com.trixti.itm.service.contratoautenticacao;

import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.contratoautenticacao.ContratoAutenticacaoDAO;
import br.com.trixti.itm.entity.ContratoAutenticacao;
import br.com.trixti.itm.service.AbstractService;

public class ContratoAutenticacaoService extends AbstractService<ContratoAutenticacao> {

	private @Inject ContratoAutenticacaoDAO contratoAutenticacaoDAO;
	
	@Override
	public AbstractDAO<ContratoAutenticacao> getDAO() {
		return contratoAutenticacaoDAO;
	}
	
	
}
