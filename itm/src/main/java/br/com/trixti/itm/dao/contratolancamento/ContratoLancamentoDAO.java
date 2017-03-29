package br.com.trixti.itm.dao.contratolancamento;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoLancamento;
import br.com.trixti.itm.util.UtilData;

public class ContratoLancamentoDAO extends AbstractDAO<ContratoLancamento> {

	public List<ContratoLancamento> pesquisarLancamentoContrato(Contrato contrato, Date data) {
			UtilData utilData = new UtilData();
			Date dataPrimeiroDia = utilData.ajustaData(data,1,0,0,0);
			Date dataUltimoDia = utilData.adicionaDias(dataPrimeiroDia, 30);
			CriteriaQuery<ContratoLancamento> criteria = getCriteriaBuilder().createQuery(ContratoLancamento.class);
			Root<ContratoLancamento> root = criteria.from(ContratoLancamento.class);
			return getManager().createQuery(criteria.select(root).
					where(	getCriteriaBuilder().equal(root.get("contrato"), contrato),
							getCriteriaBuilder().and(
									getCriteriaBuilder().lessThanOrEqualTo(root.<Date>get("dataLancamento"),dataUltimoDia),
									getCriteriaBuilder().greaterThanOrEqualTo(root.<Date>get("dataLancamento"), dataPrimeiroDia)
									)
							)).getResultList();
	}

	public List<ContratoLancamento> pesquisarLancamentoAberto(Contrato contrato) {
		CriteriaQuery<ContratoLancamento> criteria = getCriteriaBuilder().createQuery(ContratoLancamento.class);
		Root<ContratoLancamento> root = criteria.from(ContratoLancamento.class);
	    return getManager().createQuery(criteria.select(root)
	    		.where(getCriteriaBuilder().equal(root.get("contrato"), contrato),
	    				getCriteriaBuilder().isEmpty(root.<List<BoletoLancamento>>get("boletoLancamentos"))
	    				)).getResultList();
	    		
	    		
	}

	
	
	
}
