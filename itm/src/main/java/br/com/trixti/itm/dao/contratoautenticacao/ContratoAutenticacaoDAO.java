package br.com.trixti.itm.dao.contratoautenticacao;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.ContratoAutenticacao;

public class ContratoAutenticacaoDAO extends AbstractDAO<ContratoAutenticacao> {

	public ContratoAutenticacao recuperarPorUsername(String username) {
		CriteriaQuery<ContratoAutenticacao> criteria = getCriteriaBuilder().createQuery(ContratoAutenticacao.class);
		Root<ContratoAutenticacao> root = criteria.from(ContratoAutenticacao.class);
		return getManager().createQuery(
					criteria.select(root).where(getCriteriaBuilder().equal(root.get("username"), username))).setMaxResults(1).getSingleResult();
	}

}
