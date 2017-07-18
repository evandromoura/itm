package br.com.trixti.itm.dao.contratonotificacao;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoNotificacao;
import br.com.trixti.itm.entity.TipoContratoNotificacao;
import br.com.trixti.itm.util.UtilData;

public class ContratoNotificacaoDAO extends AbstractDAO<ContratoNotificacao> {

	public ContratoNotificacao recuperarPorContratoDataTipo(Contrato contrato,
			TipoContratoNotificacao tipo, Date date) {
		UtilData utilData = new UtilData();
		CriteriaQuery<ContratoNotificacao> criteria = getCriteriaBuilder().createQuery(ContratoNotificacao.class);
		Root<ContratoNotificacao> root = criteria.from(ContratoNotificacao.class);
		return getManager().createQuery(criteria.select(root).where(
				getCriteriaBuilder().equal(root.get("contrato"), contrato),
				getCriteriaBuilder().equal(root.get("tipo"), tipo),
				getCriteriaBuilder().greaterThanOrEqualTo(root.<Date>get("dataEnvio"), utilData.subtrairDias(date, 30))
				)).setMaxResults(1).getSingleResult();
	}

	public List<ContratoNotificacao> pesquisarUltimasNotificacoes(Contrato contrato) {
		CriteriaQuery<ContratoNotificacao> criteria = getCriteriaBuilder().createQuery(ContratoNotificacao.class);
		Root<ContratoNotificacao> root = criteria.from(ContratoNotificacao.class);
		return getManager().createQuery(criteria.select(root).where(getCriteriaBuilder().equal(root.get("contrato"), contrato))
						.orderBy(getCriteriaBuilder().desc(root.get("dataEnvio")))).setMaxResults(10).getResultList();
	}

}
