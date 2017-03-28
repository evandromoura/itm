package br.com.trixti.itm.dao.parametro;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Parametro;

@Stateless
public class ParametroDAO extends AbstractDAO<Parametro> {

	public Parametro recuperarParametro() {
		CriteriaQuery<Parametro> criteria = getCriteriaBuilder().createQuery(Parametro.class);
		Root<Parametro> root = criteria.from(Parametro.class);
		return getManager().createQuery(criteria.select(root)
				.orderBy(getCriteriaBuilder().desc(root.get("id"))))
				.setMaxResults(1).getSingleResult();
	}

}
