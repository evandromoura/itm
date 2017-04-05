package br.com.trixti.itm.dao.contratoproduto;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.util.UtilData;

public class ContratoProdutoDAO extends AbstractDAO<ContratoProduto> {

	public List<ContratoProduto> pesquisarVigentePorContrato(Contrato contrato) {
		CriteriaQuery<ContratoProduto> criteria = getCriteriaBuilder().createQuery(ContratoProduto.class);
		Root<ContratoProduto> root = criteria.from(ContratoProduto.class);
		Date dataAtual = new Date();
		UtilData utilData = new UtilData();
		return getManager().createQuery(criteria.select(root)
				.where(
						getCriteriaBuilder().equal(root.get("contrato"), contrato),
						getCriteriaBuilder().and(
						getCriteriaBuilder().greaterThanOrEqualTo(root.<Date>get("dataFim"), utilData.ajustaData(dataAtual, 23, 59, 59)),
						getCriteriaBuilder().lessThanOrEqualTo(root.<Date>get("dataInicio"), utilData.ajustaData(dataAtual, 0, 0, 0)))
						)).getResultList();
	}

}
