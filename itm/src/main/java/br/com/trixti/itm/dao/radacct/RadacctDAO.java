package br.com.trixti.itm.dao.radacct;

import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Radacct;
import br.com.trixti.itm.util.UtilData;

public class RadacctDAO extends AbstractDAO<Radacct> {

	
	public List<Radacct> pesquisarUltimosPorUsername(String username){
		UtilData utilData = new UtilData();
		CriteriaQuery<Radacct> criteria = getCriteriaBuilder().createQuery(Radacct.class);
		Root<Radacct> root = criteria.from(Radacct.class);
		return getManager().createQuery(criteria.select(root)
				.where(
						getCriteriaBuilder().equal(root.get("username"), username)
						)
//				.orderBy(getCriteriaBuilder().desc(root.get("radacctid")))
				
				).setMaxResults(10).getResultList();
	}
	
}
