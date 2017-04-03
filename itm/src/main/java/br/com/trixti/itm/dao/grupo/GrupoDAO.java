package br.com.trixti.itm.dao.grupo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Grupo;

@Stateless
public class GrupoDAO extends AbstractDAO<Grupo> {

	public List<Grupo> pesquisar(Grupo grupo) {
		CriteriaQuery<Grupo> criteria = getCriteriaBuilder()
					.createQuery(Grupo.class);
		Root<Grupo> root = criteria.from(Grupo.class);
		return getManager().createQuery(criteria.select(root)
				.where(comporFiltro(root, grupo))).getResultList();
	}
	
	
	private Predicate[] comporFiltro(Root<Grupo> root, Grupo grupo){
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(grupo.getNome() != null){
			predicates.add(getCriteriaBuilder()
						.like(getCriteriaBuilder().lower(root.<String>get("nome")),"%"+grupo.getNome().toLowerCase()+"%"));
		}
		return (Predicate[]) predicates.toArray(new Predicate[predicates.size()]);
	}


	@Override
	public Grupo recuperar(Serializable id) {
		CriteriaQuery<Grupo> criteria = getCriteriaBuilder()
				.createQuery(Grupo.class);
		Root<Grupo> root = criteria.from(Grupo.class);
		root.fetch("grupoParametros",JoinType.LEFT);
		return getManager().createQuery(criteria.select(root).where(
				getCriteriaBuilder().equal(root.get("id"), id))).getSingleResult();
		
		
	}
	
	
	

}
