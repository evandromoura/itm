package br.com.trixti.itm.dao.boleto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.Remessa;
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
	
	public Boleto recuperarBoletoContratoMes(Contrato contrato,Date data) {
		UtilData utilData = new UtilData();
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		Boleto boletoRetorno = getManager()
				.createQuery(criteria.select(root).where(getCriteriaBuilder().equal(root.get("contrato"), contrato),
						getCriteriaBuilder().greaterThanOrEqualTo(root.<Date>get("dataVencimento"),utilData.ajustaData(data, 1, 0, 0, 0)),
						getCriteriaBuilder().lessThan(root.<Date>get("dataVencimento"),utilData.ajustaData(utilData.adicionarMeses(data,1), 1, 0, 0, 0))
						)).setMaxResults(1).getSingleResult();
		return boletoRetorno;
	}

	public Boleto recuperarBoletoContrato(Contrato contrato, Date dataVencimento) {
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		return getManager()
				.createQuery(criteria.select(root).where(getCriteriaBuilder().equal(root.get("contrato"), contrato),
						
						getCriteriaBuilder().equal(root.get("dataVencimento"), dataVencimento))
						
						).getSingleResult();
	}

	public List<Boleto> pesquisarBoletoEmAbertoContrato(Contrato contrato) {
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		root.fetch("lancamentos", JoinType.LEFT).fetch("contratoLancamento",JoinType.LEFT);
		return inicializarBoletos(getManager()
				.createQuery(criteria.select(root).where(getCriteriaBuilder().equal(root.get("contrato"), contrato),
						getCriteriaBuilder().equal(root.get("status"), StatusBoletoEnum.ABERTO)))
				.getResultList());
	}
	
	public List<Boleto> pesquisarBoletoEmAberto() {
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		return getManager().createQuery(criteria.select(root)
						.where(getCriteriaBuilder().equal(root.get("status"), StatusBoletoEnum.ABERTO)))
				.getResultList();
	}
	
	public List<Boleto> pesquisarBoletoEmAberto(Remessa remessa) {
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		return getManager().createQuery(criteria.select(root).distinct(true)
						.where(getCriteriaBuilder().equal(root.get("status"), StatusBoletoEnum.ABERTO),
							   getCriteriaBuilder().equal(root.get("remessa"), remessa)))
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
		root.fetch("lancamentos", JoinType.LEFT).fetch("contratoLancamento",JoinType.LEFT);
		return inicializarBoletos(getManager().createQuery(criteria.select(root).
				where(
						getCriteriaBuilder().equal(root.join("contrato",JoinType.LEFT).get("cliente"),cliente),
						getCriteriaBuilder().isNotNull(root.get("dataNotificacao"))
						
						)
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
	
	public void pesquisarNomeFelipe(String nome){
		
		
	}
	
	public BigInteger recuperarNossoNumero(){
		Query q = getManager().createNativeQuery("select nextval('public.itm_nosso_numero_seq')");
		return (BigInteger)q.getSingleResult();
	}

	public Boleto recuperarPorNossoNumero(String nossoNumero,StatusBoletoEnum... status) {
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(getCriteriaBuilder().equal(root.get("nossoNumero"),nossoNumero));
		if(status != null && status.length > 0){
			predicates.add(root.get("status").in(status));
		}
		return getManager().createQuery(criteria.select(root).where(
				(Predicate[]) predicates.toArray(new Predicate[predicates.size()])
				)).getSingleResult();
	}

	public List<Boleto> pesquisarBoletoNaoNotificado() {
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		return getManager().createQuery(criteria.select(root).where(
				
				getCriteriaBuilder().or(getCriteriaBuilder().isNull(root.get("dataNotificacao")),
							getCriteriaBuilder().isNull(root.get("dataSms"))),
				
				getCriteriaBuilder().isNotNull(root.join("remessa",JoinType.LEFT).get("dataEnvio"))
				)).getResultList();
	}

	public List<Boleto> pesquisarBoletoEmAbertoContratoComAviso(Contrato contrato) {
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		root.fetch("lancamentos", JoinType.LEFT).fetch("contratoLancamento",JoinType.LEFT);
		return inicializarBoletos(getManager()
				.createQuery(criteria.select(root).where(
						getCriteriaBuilder().equal(root.get("contrato"), contrato),
						getCriteriaBuilder().equal(root.get("status"), StatusBoletoEnum.ABERTO),
						getCriteriaBuilder().isNotNull(root.<Date>get("dataNotificacao")))
						)
				.getResultList());
	}
	
	public List<Boleto> pesquisarBoletoEmAtraso(Contrato contrato) {
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		root.fetch("lancamentos", JoinType.LEFT).fetch("contratoLancamento",JoinType.LEFT);
		return inicializarBoletos(getManager()
				.createQuery(criteria.select(root).where(
						getCriteriaBuilder().equal(root.get("contrato"), contrato),
						getCriteriaBuilder().lessThan(root.<Date>get("dataVencimento"), new Date()),
						getCriteriaBuilder().equal(root.get("status"), StatusBoletoEnum.ABERTO),
						getCriteriaBuilder().isNotNull(root.<Date>get("dataNotificacao")))
						)
				.getResultList());
	}

	public List<Boleto> pesquisarBoletoRemessa(Remessa remessa) {
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		return getManager()
				.createQuery(criteria.select(root).where(getCriteriaBuilder().equal(root.get("remessa"), remessa)))
				.getResultList();
	}

	public List<Boleto> listarBoletoEmAtraso() {
		CriteriaQuery<Boleto> criteria = getCriteriaBuilder().createQuery(Boleto.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		return getManager().createQuery(criteria.select(root).where(
						getCriteriaBuilder().lessThan(root.<Date>get("dataVencimento"), new Date()),
						getCriteriaBuilder().equal(root.get("status"), StatusBoletoEnum.ABERTO))
					.orderBy(getCriteriaBuilder().asc(root.get("contrato").get("cliente").get("nome"))))
				.getResultList();
	}
	
	
	
	public BigDecimal somarBoletoEmAtraso() {
		CriteriaQuery<BigDecimal> criteria = getCriteriaBuilder().createQuery(BigDecimal.class);
		Root<Boleto> root = criteria.from(Boleto.class);
		criteria.select(getCriteriaBuilder().sum(root.<BigDecimal>get("valor"))).where(
				getCriteriaBuilder().lessThan(root.<Date>get("dataVencimento"), new Date()),
				getCriteriaBuilder().equal(root.get("status"), StatusBoletoEnum.ABERTO));
		return getManager().createQuery(criteria).getSingleResult();
	}

}
