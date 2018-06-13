package br.com.trixti.itm.dao.nfe;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Nfe;

@Stateless
public class NfeDAO extends AbstractDAO<Nfe> {

	
	
	public List<Nfe> recuperarPorMesAno(String mes,String ano) {
		CriteriaQuery<Nfe> criteria = getCriteriaBuilder().createQuery(Nfe.class);
		Root<Nfe> root = criteria.from(Nfe.class);
		return getManager().createQuery(criteria.select(root).where(
				getCriteriaBuilder().equal(root.get("mes"), mes),
				getCriteriaBuilder().equal(root.get("ano"), ano)
				)).getResultList();
	}

	
	

}

