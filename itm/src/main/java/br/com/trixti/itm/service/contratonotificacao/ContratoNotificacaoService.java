package br.com.trixti.itm.service.contratonotificacao;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.contratonotificacao.ContratoNotificacaoDAO;
import br.com.trixti.itm.entity.ContratoNotificacao;
import br.com.trixti.itm.service.AbstractService;

@Stateless
public class ContratoNotificacaoService extends AbstractService<ContratoNotificacao> {

	private @Inject ContratoNotificacaoDAO contratoNotificacaoDAO;
	
	@Override
	public AbstractDAO<ContratoNotificacao> getDAO() {
		return contratoNotificacaoDAO;
	}
}
