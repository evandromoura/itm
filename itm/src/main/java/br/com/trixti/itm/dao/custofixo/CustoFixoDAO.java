package br.com.trixti.itm.dao.custofixo;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.CustoFixo;
import br.com.trixti.itm.entity.TipoCentroCusto;
import br.com.trixti.itm.util.UtilData;

@Stateless
public class CustoFixoDAO extends AbstractDAO<CustoFixo> {

	public BigDecimal somarTodos() {
			Date dataAtual = new Date();
			UtilData utilData = new UtilData();
			CriteriaQuery<BigDecimal> criteria = getCriteriaBuilder().createQuery(BigDecimal.class);
			Root<CustoFixo> root = criteria.from(CustoFixo.class);
			criteria.select(getCriteriaBuilder().sum(root.<BigDecimal>get("valor"))).where(
					getCriteriaBuilder().greaterThanOrEqualTo(root.<Date>get("dataFim"), utilData.ajustaData(dataAtual, 23, 59, 59)),
					getCriteriaBuilder().lessThanOrEqualTo(root.<Date>get("dataInicio"), utilData.ajustaData(dataAtual, 0, 0, 0)));
			return getManager().createQuery(criteria).getSingleResult();
	}
	
	public BigDecimal somar(TipoCentroCusto tipoCentroCusto) {
		Date dataAtual = new Date();
		UtilData utilData = new UtilData();
		CriteriaQuery<BigDecimal> criteria = getCriteriaBuilder().createQuery(BigDecimal.class);
		Root<CustoFixo> root = criteria.from(CustoFixo.class);
		criteria.select(getCriteriaBuilder().sum(root.<BigDecimal>get("valor"))).where(
				getCriteriaBuilder().equal(root.get("centroCusto").get("tipo"), tipoCentroCusto),
				getCriteriaBuilder().greaterThanOrEqualTo(root.<Date>get("dataFim"), utilData.ajustaData(dataAtual, 23, 59, 59)),
				getCriteriaBuilder().lessThanOrEqualTo(root.<Date>get("dataInicio"), utilData.ajustaData(dataAtual, 0, 0, 0)));
		return getManager().createQuery(criteria).getSingleResult();
}


}

