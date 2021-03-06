package br.com.trixti.itm.dao.movimentacaofinanceira;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.MovimentacaoFinanceira;
import br.com.trixti.itm.entity.TipoCentroCusto;
import br.com.trixti.itm.entity.TipoMovimentacaoFinanceira;
import br.com.trixti.itm.util.UtilData;

@Stateless
public class MovimentacaoFinanceiraDAO extends AbstractDAO<MovimentacaoFinanceira> {

	public List<MovimentacaoFinanceira> listarSemArquivoSici() {
		CriteriaQuery<MovimentacaoFinanceira> criteria = getCriteriaBuilder().createQuery(MovimentacaoFinanceira.class);
		Root<MovimentacaoFinanceira> root = criteria.from(MovimentacaoFinanceira.class);
		return getManager()
				.createQuery(criteria.select(root).where(getCriteriaBuilder().isNull(root.get("arquivoSici"))))
				.getResultList();
	}

	public BigDecimal somar(TipoMovimentacaoFinanceira tipo, Date data, boolean anual,TipoCentroCusto... tipoCentroCusto) {
			Date dataAtual = new Date();
			UtilData utilData = new UtilData();
			CriteriaQuery<BigDecimal> criteria = getCriteriaBuilder().createQuery(BigDecimal.class);
			Root<MovimentacaoFinanceira> root = criteria.from(MovimentacaoFinanceira.class);
			Date dataInicio = null;
			Date dataFim = null;
			if(anual){
				dataInicio = utilData.ajustarPrimeiroDiaAno(dataAtual);
				dataFim = utilData.ajustarUltimoDiaAno(dataAtual);
			}else{
				dataInicio = utilData.ajustarPrimeiroDiaMes(dataAtual);
				dataFim = utilData.ajustarUltimoDiaMes(dataAtual);
			}
			criteria.select(getCriteriaBuilder().sum(root.<BigDecimal>get("valor"))).where(root.join("centroCusto").get("tipo").in(tipoCentroCusto),
					getCriteriaBuilder().greaterThanOrEqualTo(root.<Date>get("data"), dataInicio),
					getCriteriaBuilder().lessThanOrEqualTo(root.<Date>get("data"), dataFim));
			return getManager().createQuery(criteria).getSingleResult();
	}

	@Override
	public List<MovimentacaoFinanceira> listar() {
		CriteriaQuery<MovimentacaoFinanceira> criteria = getCriteriaBuilder().createQuery(MovimentacaoFinanceira.class);
		Root<MovimentacaoFinanceira> root = criteria.from(MovimentacaoFinanceira.class);
		return getManager().createQuery(criteria.select(root).orderBy(getCriteriaBuilder().desc(root.get("data")))).getResultList();
	
	}
	
	

}
