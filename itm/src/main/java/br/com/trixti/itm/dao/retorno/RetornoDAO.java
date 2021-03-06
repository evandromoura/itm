package br.com.trixti.itm.dao.retorno;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Retorno;
import br.com.trixti.itm.util.UtilString;

@Stateless
public class RetornoDAO extends AbstractDAO<Retorno> {

	public List<Retorno> pesquisar(Retorno retorno) {
		CriteriaQuery<Retorno> criteria = getCriteriaBuilder().createQuery(Retorno.class);
		Root<Retorno> root = criteria.from(Retorno.class);
		return getManager().createQuery(criteria.multiselect(root.get("id"),root.get("dataCriacao"),root.get("banco"),root.get("dataProcessamento"),root.get("nomeArquivo"),root.get("status"),root.get("mensagem"))
				.where(comporFiltro(retorno, root)).orderBy(getCriteriaBuilder().desc(root.get("dataCriacao")))).getResultList();
	}
	
	@Override
	public Retorno recuperar(Serializable id) {
		CriteriaQuery<Retorno> criteria = getCriteriaBuilder().createQuery(Retorno.class);
		Root<Retorno> root = criteria.from(Retorno.class);
//		root.fetch("boletos",JoinType.LEFT);
		Retorno retorno = getManager().createQuery(criteria.select(root).distinct(true)
				.where(getCriteriaBuilder().equal(root.get("id"), id))).getSingleResult();
		return retorno;
	}
	
	public Retorno recuperarCompleto(Serializable id) {
		CriteriaQuery<Retorno> criteria = getCriteriaBuilder().createQuery(Retorno.class);
		Root<Retorno> root = criteria.from(Retorno.class);
		return getManager().createQuery(criteria.select(root)
				.where(getCriteriaBuilder().equal(root.get("id"), id))).getSingleResult();
	}
	
	private Predicate[] comporFiltro(Retorno retorno, Root<Retorno> root){
		UtilString us = new UtilString();
		List<Predicate> listaPredicado = new ArrayList<Predicate>();
		if(!us.vazio(retorno.getBanco())){
			listaPredicado.add(getCriteriaBuilder().equal(root.get("banco"), retorno.getBanco()));
		}
		if(retorno.getPeriodoTO().getDataInicio() != null && retorno.getPeriodoTO().getDataFim() != null){
			listaPredicado.add(getCriteriaBuilder().between(root.<Date>get("dataCriacao"), retorno.getPeriodoTO().getDataInicio(), retorno.getPeriodoTO().getDataFim()));
		}
		return (Predicate[]) listaPredicado.toArray(new Predicate[listaPredicado.size()]);
	}

	public List<Retorno> listarPendentes() {
		CriteriaQuery<Retorno> criteria = getCriteriaBuilder().createQuery(Retorno.class);
		Root<Retorno> root = criteria.from(Retorno.class);
		return getManager().createQuery(criteria.select(root).where(getCriteriaBuilder().isNull(root.get("dataProcessamento")))).getResultList();
	}

}
