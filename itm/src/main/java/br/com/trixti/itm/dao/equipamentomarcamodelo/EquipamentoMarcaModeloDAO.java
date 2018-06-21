package br.com.trixti.itm.dao.equipamentomarcamodelo;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.EquipamentoMarca;
import br.com.trixti.itm.entity.EquipamentoMarcaModelo;

@Stateless
public class EquipamentoMarcaModeloDAO extends AbstractDAO<EquipamentoMarcaModelo> {

	public List<EquipamentoMarcaModelo> pesquisarPorMarca(EquipamentoMarca marca) {
		CriteriaQuery<EquipamentoMarcaModelo> criteria = getCriteriaBuilder().createQuery(EquipamentoMarcaModelo.class);
		Root<EquipamentoMarcaModelo> root = criteria.from(EquipamentoMarcaModelo.class);
		return getManager()
				.createQuery(criteria.select(root).where(getCriteriaBuilder().equal(root.get("marca"), marca)))
				.getResultList();
	}

}
