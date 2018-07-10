package br.com.trixti.itm.dao.equipamentomarca;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.EquipamentoMarca;
import br.com.trixti.itm.entity.EquipamentoTipo;

@Stateless
public class EquipamentoMarcaDAO extends AbstractDAO<EquipamentoMarca> {

	public List<EquipamentoMarca> pesquisarPorTipo(EquipamentoTipo tipo) {
		CriteriaQuery<EquipamentoMarca> criteria = getCriteriaBuilder().createQuery(EquipamentoMarca.class);
		Root<EquipamentoMarca> root = criteria.from(EquipamentoMarca.class);
		return getManager().createQuery(criteria.select(root).where(getCriteriaBuilder().equal(root.get("tipo"), tipo))).getResultList();
	}


}
