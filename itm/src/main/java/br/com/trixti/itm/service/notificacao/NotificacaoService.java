package br.com.trixti.itm.service.notificacao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.notificacao.NotificacaoDAO;
import br.com.trixti.itm.entity.Notificacao;
import br.com.trixti.itm.entity.Tag;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class NotificacaoService extends AbstractService<Notificacao> {

	private @Inject NotificacaoDAO notificacaoDAO;
	
	
	@Override
	public AbstractDAO<Notificacao> getDAO() {
		return notificacaoDAO;
	}
	
	public List<Notificacao> pesquisarVigentePorTag(Tag tag){
		return notificacaoDAO.pesquisarVigentePorTag(tag);
	}

	@Override
	public void incluir(Notificacao entidade) {
		entidade.setDataCadastro(new Date());
		super.incluir(entidade);
	}
	
	public List<Notificacao> pesquisar(Notificacao notificacao){
		return notificacaoDAO.pesquisar(notificacao);
	}

}