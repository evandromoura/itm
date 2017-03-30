package br.com.trixti.itm.dao.contacorrente;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.ContaCorrente;

@Stateless
public class ContaCorrenteDAO extends AbstractDAO<ContaCorrente> {

	public List<ContaCorrente> pesquisar(ContaCorrente contaCorrente) {

		CriteriaQuery<ContaCorrente> criteria = 
					getCriteriaBuilder().createQuery(ContaCorrente.class);
		Root<ContaCorrente> root = criteria.from(ContaCorrente.class);
		
		return getManager().createQuery(
				criteria.select(root)
				.where(comporFiltro(root, contaCorrente)))
				.getResultList();
	}
	
	private Predicate[] comporFiltro(Root<ContaCorrente> root, 
			ContaCorrente contaCorrente){
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		if(contaCorrente.getNome() != null){
			predicados.add(getCriteriaBuilder().like(getCriteriaBuilder().lower(root.<String>get("nome")), "%"+contaCorrente.getNome().toLowerCase()+"%"));
		}
		if(contaCorrente.getBanco() != null){
			predicados.add(getCriteriaBuilder().like(getCriteriaBuilder().lower(root.<String>get("banco")), "%"+contaCorrente.getBanco().toLowerCase()+"%"));
		}
		return (Predicate[]) predicados.toArray(new Predicate[predicados.size()]);
	}
	

}
