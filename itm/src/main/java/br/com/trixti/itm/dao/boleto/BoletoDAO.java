package br.com.trixti.itm.dao.boleto;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.util.UtilData;

public class BoletoDAO extends AbstractDAO<Boleto> {

	
	public List<Boleto> pesquisarBoletoCliente(Cliente cliente,Date data){
		UtilData utilData = new UtilData();
		Date dataPrimeiroDia = utilData.ajustaData(data,1,0,0,0);
		Date dataUltimoDia = utilData.adicionaDias(dataPrimeiroDia, 30);
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		return getManager().createQuery(criteria.select(root).
				where(	getCriteriaBuilder().equal(root.get("cliente"), cliente),
						getCriteriaBuilder().and(
								getCriteriaBuilder().lessThanOrEqualTo(root.<Date>get("dataCriacao"),dataUltimoDia),
								getCriteriaBuilder().greaterThanOrEqualTo(root.<Date>get("dataCriacao"), dataPrimeiroDia)
								)
						)).getResultList();
	}
	
}
