package br.com.trixti.itm.dao.boleto;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.StatusBoletoEnum;
import br.com.trixti.itm.util.UtilData;

public class BoletoDAO extends AbstractDAO<Boleto> {

	public List<Boleto> pesquisarBoletoCliente(Cliente cliente, Date data) {
		UtilData utilData = new UtilData();
		Date dataPrimeiroDia = utilData.ajustaData(data, 1, 0, 0, 0);
		Date dataUltimoDia = utilData.adicionaDias(dataPrimeiroDia, 30);
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		return getManager().createQuery(criteria.select(root)
				.where(getCriteriaBuilder().equal(root.get("cliente"), cliente), getCriteriaBuilder().and(
						getCriteriaBuilder().lessThanOrEqualTo(root.<Date>get("dataCriacao"), dataUltimoDia),
						getCriteriaBuilder().greaterThanOrEqualTo(root.<Date>get("dataCriacao"), dataPrimeiroDia))))
				.getResultList();
	}

	public Boleto recuperarBoletoContrato(Contrato contrato, Date dataVencimento) {
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		return getManager()
				.createQuery(criteria.select(root).where(getCriteriaBuilder().equal(root.get("contrato"), contrato),
						getCriteriaBuilder().equal(root.get("dataVencimento"), dataVencimento)))
				.getSingleResult();
	}

	public List<Boleto> pesquisarBoletoEmAbertoContrato(Contrato contrato) {
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		return getManager()
				.createQuery(criteria.select(root).where(getCriteriaBuilder().equal(root.get("contrato"), contrato),
						getCriteriaBuilder().equal(root.get("status"), StatusBoletoEnum.ABERTO)))
				.getResultList();
	}

	public void excluirPorContrato(Contrato contrato) {
		StringBuilder jql = new StringBuilder("DELETE FROM ");
		jql.append(this.getEntityType().getName());
		jql.append(" entity ");
		jql.append(" WHERE ");
		jql.append(" entity.contrato =:contrato ");
		Query query = this.getManager().createQuery(jql.toString());
		query.setParameter("contrato", contrato);
		query.executeUpdate();
	}
	
	public List<Boleto> pesquisarUltimosBoletosCliente(Cliente cliente){
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		return inicializarBoletos(getManager().createQuery(criteria.select(root).
				where(getCriteriaBuilder().equal(root.join("contrato",JoinType.LEFT).get("cliente"),cliente))
				.orderBy(getCriteriaBuilder().desc(root.get("id")))).setMaxResults(12).getResultList());
	}
	
	private List<Boleto> inicializarBoletos(List<Boleto> listaBoleto){
		for(Boleto boleto:listaBoleto){
			if(boleto.getLancamentos() != null){
				for(BoletoLancamento boletoLancamento:boleto.getLancamentos()){
					if(boletoLancamento.getContratoLancamento() != null){
						boletoLancamento.getContratoLancamento().getDescricao();
					}
				}
			}
		}
		return listaBoleto;
	}

	public List<Boleto> pesquisarBoletoSemRemessa() {
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		return getManager().createQuery(criteria.select(root).where(getCriteriaBuilder().isNull(root.get("remessa")))).getResultList();
	}
	
	public Long recuperarNossoNumero(){
		Query q = getManager().createNativeQuery("select nextval('public.itm_nosso_numero_seq')");
		return (Long)q.getSingleResult();
	}

}
