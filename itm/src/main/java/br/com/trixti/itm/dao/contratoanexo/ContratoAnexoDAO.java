package br.com.trixti.itm.dao.contratoanexo;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoAnexo;

@Stateless
public class ContratoAnexoDAO extends AbstractDAO<ContratoAnexo> {

	public List<ContratoAnexo> pesquisarPorContrato(Contrato contrato) {
		CriteriaQuery<ContratoAnexo> criteria = getCriteriaBuilder().createQuery(ContratoAnexo.class);
		Root<ContratoAnexo> root = criteria.from(ContratoAnexo.class);
		return getManager().createQuery(criteria.multiselect(root.get("id"), root.get("nome"), root.get("dataCriacao"))
				.where(getCriteriaBuilder().equal(root.get("contrato"), contrato))).getResultList();
	}

}
