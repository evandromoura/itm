package br.com.trixti.itm.dao.tag;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Tag;

@Stateless
public class TagDAO extends AbstractDAO<Tag> {

	public List<Tag> pesquisar(Tag tag) {
		CriteriaQuery<Tag> criteria =getCriteriaBuilder().createQuery(Tag.class);
		Root<Tag> root = criteria.from(Tag.class);
		return getManager().createQuery(criteria.select(root).where(comporFiltro(root, tag))).getResultList();
	}
	
	private Predicate[] comporFiltro(Root<Tag> root, Tag tag){
		List<Predicate> predicados = new ArrayList<Predicate>();
		if(tag.getNome() != null){
			predicados.add(getCriteriaBuilder().like(getCriteriaBuilder().lower(root.<String>get("nome")), "%"+tag.getNome().toLowerCase()+"%"));
		}
		return (Predicate[]) predicados.toArray(new Predicate[predicados.size()]);
	}
	

}

