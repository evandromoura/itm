package br.com.trixti.itm.dao.log;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Log;

@Stateless
public class LogDAO extends AbstractDAO<Log> {

	@Override
	public List<Log> listar() {
		CriteriaQuery<Log> criteria = getCriteriaBuilder().createQuery(Log.class);
		Root<Log> root = criteria.from(Log.class);
		return getManager().createQuery(criteria.select(root).orderBy(getCriteriaBuilder().desc(root.get("id")))).setMaxResults(50).getResultList();
	}

}

