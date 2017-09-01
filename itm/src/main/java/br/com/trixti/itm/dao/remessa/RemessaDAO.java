package br.com.trixti.itm.dao.remessa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Remessa;
import br.com.trixti.itm.util.UtilString;

@Stateless
public class RemessaDAO extends AbstractDAO<Remessa> {

	public List<Remessa> pesquisar(Remessa remessa) {
		CriteriaQuery<Remessa> criteria = getCriteriaBuilder().createQuery(Remessa.class);
		Root<Remessa> root = criteria.from(Remessa.class);
		return getManager().createQuery(criteria.multiselect(root.get("id"),root.get("dataCriacao"),root.get("dataEnvio"),root.get("codigo"),
				root.get("banco"),root.get("status"),root.get("valor"),root.get("qtdBoletoAberto"),root.get("qtdBoletoFechado"),root.get("valorRecebido"))
				.where(comporFiltro(remessa, root)).orderBy(getCriteriaBuilder().desc(root.get("dataCriacao")))).getResultList();
	}
	
	
	@Override
	public Remessa recuperar(Serializable id) {
		CriteriaQuery<Remessa> criteria = getCriteriaBuilder().createQuery(Remessa.class);
		Root<Remessa> root = criteria.from(Remessa.class);
		root.fetch("boletos",JoinType.LEFT);
		Remessa retorno = getManager().createQuery(criteria.select(root).distinct(true)
				.where(getCriteriaBuilder().equal(root.get("id"), id))).getSingleResult();
		return retorno;
	}
	
	public Remessa recuperarCompleto(Serializable id) {
		CriteriaQuery<Remessa> criteria = getCriteriaBuilder().createQuery(Remessa.class);
		Root<Remessa> root = criteria.from(Remessa.class);
		return getManager().createQuery(criteria.select(root)
				.where(getCriteriaBuilder().equal(root.get("id"), id))).getSingleResult();
	}
	
	private Predicate[] comporFiltro(Remessa remessa, Root<Remessa> root){
		UtilString us = new UtilString();
		List<Predicate> listaPredicado = new ArrayList<Predicate>();
		
		
		if(!us.vazio(remessa.getBanco())){
			listaPredicado.add(getCriteriaBuilder().equal(root.get("banco"), remessa.getBanco()));
		}
		
		if(!us.vazio(remessa.getCodigo())){
			listaPredicado.add(getCriteriaBuilder().equal(root.get("codigo"), remessa.getCodigo()));
		}
		
		if(remessa.getStatus() != null){
			listaPredicado.add(getCriteriaBuilder().equal(root.get("status"), remessa.getStatus()));
		}
		
		if(remessa.getPeriodoTO().getDataInicio() != null && remessa.getPeriodoTO().getDataFim() != null){
			listaPredicado.add(getCriteriaBuilder().between(root.<Date>get("dataCriacao"), remessa.getPeriodoTO().getDataInicio(), remessa.getPeriodoTO().getDataFim()));
		}
		return (Predicate[]) listaPredicado.toArray(new Predicate[listaPredicado.size()]);
	}
	
	public List<Remessa> listarNaoEnviadas(){
		CriteriaQuery<Remessa> criteria = getCriteriaBuilder().createQuery(Remessa.class);
		Root<Remessa> root = criteria.from(Remessa.class);
		return getManager().createQuery(criteria.select(root).where(getCriteriaBuilder().isNull(root.get("dataEnvio")))).getResultList();
	}

}
