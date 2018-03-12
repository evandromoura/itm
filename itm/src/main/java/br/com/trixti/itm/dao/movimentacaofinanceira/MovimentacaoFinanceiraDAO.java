package br.com.trixti.itm.dao.movimentacaofinanceira;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.MovimentacaoFinanceira;

@Stateless
public class MovimentacaoFinanceiraDAO extends AbstractDAO<MovimentacaoFinanceira> {

	public List<MovimentacaoFinanceira> listarSemArquivoSici() {
		CriteriaQuery<MovimentacaoFinanceira> criteria = getCriteriaBuilder().createQuery(MovimentacaoFinanceira.class);
		Root<MovimentacaoFinanceira> root = criteria.from(MovimentacaoFinanceira.class);
		return getManager()
				.createQuery(criteria.select(root).where(getCriteriaBuilder().isNull(root.get("arquivoSici"))))
				.getResultList();
	}

}
