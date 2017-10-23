package br.com.trixti.itm.dao.usuario;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Usuario;

@Stateless
public class UsuarioDAO extends AbstractDAO<Usuario> {

	public List<Usuario> pesquisar(Usuario usuario) {

		CriteriaQuery<Usuario> criteria = 
					getCriteriaBuilder().createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);
		
		return getManager().createQuery(
				criteria.select(root)
				.where(comporFiltro(root, usuario)))
				.getResultList();
	}
	
	private Predicate[] comporFiltro(Root<Usuario> root, 
			Usuario usuario){
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		if(usuario.getNome() != null){
			predicados.add(getCriteriaBuilder().like(getCriteriaBuilder().lower(root.<String>get("nome")), "%"+usuario.getNome().toLowerCase()+"%"));
		}
		return (Predicate[]) predicados.toArray(new Predicate[predicados.size()]);
	}
	
	public Usuario login(String login, String senha) {
		CriteriaQuery<Usuario> criteria = getCriteriaBuilder().createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);
		return getManager().createQuery(criteria.select(root).where(getCriteriaBuilder().equal(root.get("login"), login)
					,getCriteriaBuilder().equal(root.get("senha"), senha)
						)).getSingleResult();
	}
	

}

