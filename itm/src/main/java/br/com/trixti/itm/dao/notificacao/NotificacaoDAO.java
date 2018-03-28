package br.com.trixti.itm.dao.notificacao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Notificacao;
import br.com.trixti.itm.entity.Tag;

@Stateless
public class NotificacaoDAO extends AbstractDAO<Notificacao> {

	@Override
	public Notificacao recuperar(Serializable id) {
		return inicializar(super.recuperar(id));
	}
	
	private Notificacao inicializar(Notificacao notificacao){
		if(notificacao.getTags() != null){
			notificacao.getTags().size();
		}
		return notificacao;
	}

	public List<Notificacao> pesquisarVigentePorTag(Tag tag) {
		CriteriaQuery<Notificacao> criteria = getCriteriaBuilder().createQuery(Notificacao.class);
		Root<Notificacao> root = criteria.from(Notificacao.class);
		return getManager().createQuery(criteria.select(root).where(getCriteriaBuilder().lessThan(root.<Date>get("dataInicio"), new Date()),
				getCriteriaBuilder().greaterThan(root.<Date>get("dataFim"), new Date()),
				getCriteriaBuilder().equal(root.join("tags",JoinType.LEFT).get("tag"), tag))).getResultList();
	}
	
	
	public List<Notificacao> pesquisar(Notificacao notificacao){
		CriteriaQuery<Notificacao> criteria = getCriteriaBuilder().createQuery(Notificacao.class);
		Root<Notificacao> root = criteria.from(Notificacao.class);
		root.fetch("tags",JoinType.LEFT);
		return getManager().createQuery(criteria.select(root).distinct(true)).getResultList();
	}
	
}

