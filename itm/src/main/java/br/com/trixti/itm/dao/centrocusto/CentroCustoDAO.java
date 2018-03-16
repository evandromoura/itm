package br.com.trixti.itm.dao.centrocusto;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.CentroCusto;
import br.com.trixti.itm.entity.TipoCentroCusto;

@Stateless
public class CentroCustoDAO extends AbstractDAO<CentroCusto> {

	public CentroCusto recuperarPrincipal(TipoCentroCusto tipo) {
		CriteriaQuery<CentroCusto> criteria = getCriteriaBuilder().createQuery(CentroCusto.class);
		Root<CentroCusto> root = criteria.from(CentroCusto.class);
		return getManager().createQuery(criteria.select(root).where(getCriteriaBuilder().equal(root.get("tipo"), tipo),
				getCriteriaBuilder().equal(root.get("principal"), true))).setMaxResults(1).getSingleResult();
	}

}

