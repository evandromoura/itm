package br.com.trixti.itm.dao.arquivosici;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.ArquivoSici;

@Stateless
public class ArquivoSiciDAO extends AbstractDAO<ArquivoSici> {

	
	
	public List<ArquivoSici> recuperarPorMesAno(String mes,String ano) {
		CriteriaQuery<ArquivoSici> criteria = getCriteriaBuilder().createQuery(ArquivoSici.class);
		Root<ArquivoSici> root = criteria.from(ArquivoSici.class);
		return getManager().createQuery(criteria.select(root).where(
				getCriteriaBuilder().equal(root.get("mes"), mes),
				getCriteriaBuilder().equal(root.get("ano"), ano)
				)).getResultList();
	}

	
	

}

