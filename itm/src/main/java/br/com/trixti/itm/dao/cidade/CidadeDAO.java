package br.com.trixti.itm.dao.cidade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Cidade;
import br.com.trixti.itm.entity.Uf;

@Stateless
public class CidadeDAO extends AbstractDAO<Cidade> {

	public List<Cidade> pesquisarPorUf(Uf uf) {
		CriteriaQuery<Cidade> criteria = getCriteriaBuilder().createQuery(Cidade.class);
		Root<Cidade> root = criteria.from(Cidade.class);
		return getManager().createQuery(criteria.select(root).where(getCriteriaBuilder().equal(root.get("uf"),uf))).getResultList();
	}

}

