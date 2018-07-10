package br.com.trixti.itm.dao.contratoequipamento;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.ContratoEquipamento;
import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.entity.StatusBoletoEnum;

@Stateless
public class ContratoEquipamentoDAO extends AbstractDAO<ContratoEquipamento> {


	public List<ContratoEquipamento> pesquisarPorEquipamento(Equipamento equipamento) {
		CriteriaQuery<ContratoEquipamento> criteria = getCriteriaBuilder().createQuery(ContratoEquipamento.class);
		Root<ContratoEquipamento> root = criteria.from(ContratoEquipamento.class);
		root.fetch("equipamento",JoinType.LEFT);
		root.fetch("contrato",JoinType.LEFT);
		return getManager().createQuery(criteria.select(root).where(getCriteriaBuilder().equal(root.get("equipamento"), equipamento))).getResultList();
	}
	
	
	public BigDecimal qtdEquipamentoEmUso(Equipamento equipamento) {
		CriteriaQuery<BigDecimal> criteria = getCriteriaBuilder().createQuery(BigDecimal.class);
		Root<ContratoEquipamento> root = criteria.from(ContratoEquipamento.class);
		criteria.select(getCriteriaBuilder().sum(root.<BigDecimal>get("quantidade")))
			.where(getCriteriaBuilder().equal(root.get("equipamento"), equipamento),getCriteriaBuilder().isNull(root.get("dataRetirada")));
		return getManager().createQuery(criteria).getSingleResult();
	}


}
