package br.com.trixti.itm.dao.clientelancamento;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.ClienteLancamento;
import br.com.trixti.itm.util.UtilData;

public class ClienteLancamentoDAO extends AbstractDAO<ClienteLancamento> {

	public List<ClienteLancamento> pesquisarLancamentoCliente(Cliente cliente, Date data) {
			UtilData utilData = new UtilData();
			Date dataPrimeiroDia = utilData.ajustaData(data,1,0,0,0);
			Date dataUltimoDia = utilData.adicionaDias(dataPrimeiroDia, 30);
			CriteriaQuery<ClienteLancamento> criteria = getCriteriaBuilder().createQuery(ClienteLancamento.class);
			Root<ClienteLancamento> root = criteria.from(ClienteLancamento.class);
			return getManager().createQuery(criteria.select(root).
					where(	getCriteriaBuilder().equal(root.get("cliente"), cliente),
							getCriteriaBuilder().and(
									getCriteriaBuilder().lessThanOrEqualTo(root.<Date>get("dataLancamento"),dataUltimoDia),
									getCriteriaBuilder().greaterThanOrEqualTo(root.<Date>get("dataLancamento"), dataPrimeiroDia)
									)
							)).getResultList();
	}

	public List<ClienteLancamento> pesquisarLancamentoAberto(Cliente cliente) {
		CriteriaQuery<ClienteLancamento> criteria = getCriteriaBuilder().createQuery(ClienteLancamento.class);
		Root<ClienteLancamento> root = criteria.from(ClienteLancamento.class);
	    return getManager().createQuery(criteria.select(root)
	    		.where(getCriteriaBuilder().equal(root.get("cliente"), cliente),
	    				getCriteriaBuilder().isEmpty(root.<List<BoletoLancamento>>get("boletoLancamentos"))
	    				)).getResultList();
	    		
	    		
	}

	
	
	
}
