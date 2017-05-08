package br.com.trixti.itm.dao.produto;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Produto;

@Stateless
public class ProdutoDAO extends AbstractDAO<Produto> {

	public List<Produto> pesquisar(Produto produto) {

		CriteriaQuery<Produto> criteria = 
					getCriteriaBuilder().createQuery(Produto.class);
		Root<Produto> root = criteria.from(Produto.class);
		
		return getManager().createQuery(
				criteria.select(root)
				.where(comporFiltro(root, produto)))
				.getResultList();
	}
	
	private Predicate[] comporFiltro(Root<Produto> root, 
			Produto produto){
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		if(produto.getNome() != null){
			predicados.add(getCriteriaBuilder().like(getCriteriaBuilder().lower(root.<String>get("nome")), "%"+produto.getNome().toLowerCase()+"%"));
		}
		if(produto.getTipo() != null){
			predicados.add(getCriteriaBuilder().like(getCriteriaBuilder().lower(root.<String>get("tipo")), "%"+produto.getTipo().toLowerCase()+"%"));
		}
		return (Predicate[]) predicados.toArray(new Predicate[predicados.size()]);
	}
	

}

