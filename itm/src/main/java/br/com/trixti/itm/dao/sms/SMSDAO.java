package br.com.trixti.itm.dao.sms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.SMS;

@Stateless
public class SMSDAO extends AbstractDAO<SMS> {

	public List<SMS> pesquisar(SMS sms) {
		CriteriaQuery<SMS> criteria = getCriteriaBuilder()
					.createQuery(SMS.class);
		Root<SMS> root = criteria.from(SMS.class);
		return getManager().createQuery(criteria.select(root)
				.where(comporFiltro(root, sms))).getResultList();
	}
	
	
	private Predicate[] comporFiltro(Root<SMS> root, SMS sms){
		List<Predicate> predicates = new ArrayList<Predicate>();
		
//		if(sms.getNome() != null){
//			predicates.add(getCriteriaBuilder()
//						.like(getCriteriaBuilder().lower(root.<String>get("nome")),"%"+sms.getNome().toLowerCase()+"%"));
//		}
		return (Predicate[]) predicates.toArray(new Predicate[predicates.size()]);
	}


	@Override
	public SMS recuperar(Serializable id) {
		CriteriaQuery<SMS> criteria = getCriteriaBuilder()
				.createQuery(SMS.class);
		Root<SMS> root = criteria.from(SMS.class);
		return getManager().createQuery(criteria.select(root).where(
				getCriteriaBuilder().equal(root.get("id"), id))).getSingleResult();
		
	}


}
