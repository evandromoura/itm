package br.com.trixti.itm.dao.contratoproduto;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.ClienteTag;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.entity.StatusContrato;
import br.com.trixti.itm.entity.Tag;
import br.com.trixti.itm.util.UtilData;

public class ContratoProdutoDAO extends AbstractDAO<ContratoProduto> {

	public List<ContratoProduto> pesquisarVigentePorContrato(Contrato contrato) {
		CriteriaQuery<ContratoProduto> criteria = getCriteriaBuilder().createQuery(ContratoProduto.class);
		Root<ContratoProduto> root = criteria.from(ContratoProduto.class);
		Date dataAtual = new Date();
		UtilData utilData = new UtilData();
		root.fetch("produto",JoinType.LEFT);
		return getManager().createQuery(criteria.select(root)
				.where(
						getCriteriaBuilder().equal(root.get("contrato"), contrato),
						getCriteriaBuilder().isNull(root.get("dataExclusao")),
						getCriteriaBuilder().and(
						getCriteriaBuilder().greaterThanOrEqualTo(root.<Date>get("dataFim"), utilData.ajustaData(dataAtual, 23, 59, 59)),
						getCriteriaBuilder().lessThanOrEqualTo(root.<Date>get("dataInicio"), utilData.ajustaData(dataAtual, 0, 0, 0)))
						).distinct(true)).getResultList();
	}

	public List<ContratoProduto> listarAtivos() {
		Date dataAtual = new Date();
		UtilData utilData = new UtilData();
		CriteriaQuery<ContratoProduto> criteria = getCriteriaBuilder().createQuery(ContratoProduto.class);
		Root<ContratoProduto> root = criteria.from(ContratoProduto.class);
		return getManager().createQuery(criteria.select(root)
				.where(
				root.get("contrato").get("status").in(StatusContrato.ATIVO,StatusContrato.SUSPENSO),
				getCriteriaBuilder().isNull(root.get("dataExclusao")),
				getCriteriaBuilder().greaterThanOrEqualTo(root.<Date>get("dataFim"), utilData.ajustaData(dataAtual, 23, 59, 59)),
				getCriteriaBuilder().lessThanOrEqualTo(root.<Date>get("dataInicio"), utilData.ajustaData(dataAtual, 0, 0, 0)
				))).getResultList(); 
	}

	public List<ContratoProduto> pesquisarPorTag(Tag tag) {
		UtilData utilData = new UtilData();
		Date dataAtual = new Date();
		CriteriaQuery<ContratoProduto> criteria = getCriteriaBuilder().createQuery(ContratoProduto.class);
		Root<ContratoProduto> root = criteria.from(ContratoProduto.class);
		return getManager().createQuery(criteria.select(root).distinct(true).where(
				getCriteriaBuilder().equal(root.join("contrato",JoinType.LEFT).join("cliente",JoinType.LEFT).join("clienteTags",JoinType.LEFT).get("tag"),tag),
				root.get("contrato").get("status").in(StatusContrato.ATIVO,StatusContrato.SUSPENSO),
				getCriteriaBuilder().isNull(root.get("dataExclusao")),
				getCriteriaBuilder().greaterThanOrEqualTo(root.<Date>get("dataFim"), utilData.ajustaData(dataAtual, 23, 59, 59)),
				getCriteriaBuilder().lessThanOrEqualTo(root.<Date>get("dataInicio"), utilData.ajustaData(dataAtual, 0, 0, 0)
				))).getResultList();
	}

	public List<ContratoProduto> listarSemTag() {
		UtilData utilData = new UtilData();
		Date dataAtual = new Date();
		CriteriaQuery<ContratoProduto> criteria = getCriteriaBuilder().createQuery(ContratoProduto.class);
		Root<ContratoProduto> root = criteria.from(ContratoProduto.class);
		return getManager().createQuery(criteria.select(root).distinct(true).where(
				
				getCriteriaBuilder().isEmpty(root.join("contrato").join("cliente").<List<ClienteTag>>get("clienteTags")),
						root.get("contrato").get("status").in(StatusContrato.ATIVO,StatusContrato.SUSPENSO),
						getCriteriaBuilder().isNull(root.get("dataExclusao")),
						getCriteriaBuilder().greaterThanOrEqualTo(root.<Date>get("dataFim"), utilData.ajustaData(dataAtual, 23, 59, 59)),
						getCriteriaBuilder().lessThanOrEqualTo(root.<Date>get("dataInicio"), utilData.ajustaData(dataAtual, 0, 0, 0)
						))).getResultList();
		
	}

}
