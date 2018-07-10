package br.com.trixti.itm.dao.equipamento.historico;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.entity.EquipamentoHistorico;

@Stateless
public class EquipamentoHistoricoDAO extends AbstractDAO<EquipamentoHistorico> {

	public List<EquipamentoHistorico> pesquisarPorEquipamento(Equipamento equipamento){
		CriteriaQuery<EquipamentoHistorico> criteria = getCriteriaBuilder().createQuery(EquipamentoHistorico.class);
		Root<EquipamentoHistorico> root = criteria.from(EquipamentoHistorico.class);
		return getManager().createQuery(criteria.select(root)
				.where(getCriteriaBuilder().equal(root.get("pk").get("id"), equipamento.getId()))
//				.orderBy(getCriteriaBuilder().desc(root.get("dataUltimaAtualizacao")))
				).getResultList();
	}
}
