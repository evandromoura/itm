package br.com.trixti.itm.dao.cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Contrato;


@Stateless
public class ClienteDAO extends AbstractDAO<Cliente> {

	public List<Cliente> pesquisar(Cliente clientePesquisa) {
		CriteriaQuery<Cliente> criteria = getCriteriaBuilder().createQuery(Cliente.class);
		Root<Cliente> root = criteria.from(Cliente.class);
		Predicate[] predicates = comporFiltroPesquisa(root, clientePesquisa);
		return getManager().createQuery(criteria.select(root).where(predicates)).getResultList();
	}
	
	private Predicate[] comporFiltroPesquisa(Root<Cliente> root,Cliente clientePesquisa){
		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		if(clientePesquisa.getNome() != null && !clientePesquisa.getNome().equals("")){
			predicateList.add(getCriteriaBuilder().like(getCriteriaBuilder().lower(root.<String>get("nome")), "%"+ clientePesquisa.getNome().toLowerCase()+"%"));
		}
		
		if(clientePesquisa.getCpfCnpj() != null && !clientePesquisa.getCpfCnpj().equals("")){
			predicateList.add(getCriteriaBuilder().like(getCriteriaBuilder().lower(root.<String>get("cpfCnpj")), "%"+ clientePesquisa.getCpfCnpj().toLowerCase()+"%"));
		}
		
		return (Predicate[]) predicateList.toArray(new Predicate[predicateList.size()]);
	}

	@Override
	public Cliente recuperar(Serializable id) {
		Cliente cliente = super.recuperar(id);
		inicializarCliente(cliente);
		return cliente;
	}
	
	private Cliente inicializarCliente(Cliente cliente){
		if(cliente != null){
			if(cliente.getContratos() != null){
				for(Contrato contrato:cliente.getContratos()){
					if(contrato.getAutenticacoes() != null){
						contrato.getAutenticacoes().size();
					}	
				}
			}
		}	
		return cliente;
	}

	/**
	 * Criteria JPA2 
	 * @return
	 */
	public List<Cliente> listarAtivo() {
		CriteriaQuery<Cliente> criteria = getCriteriaBuilder().createQuery(Cliente.class);
		Root<Cliente> root = criteria.from(Cliente.class);
		return getManager().createQuery(
				criteria.select(root).where(getCriteriaBuilder().isNull(root.get("dataExclusao")))).getResultList();
	}
	
	public Cliente recuperarPorAutenticacao(String username,String senha){
		CriteriaQuery<Cliente> criteria = getCriteriaBuilder().createQuery(Cliente.class);
		Root<Cliente> root = criteria.from(Cliente.class);
		
		return inicializarCliente(getManager().createQuery(criteria.select(root)
				.where(getCriteriaBuilder().equal(root.join("contratos",JoinType.LEFT).join("autenticacoes",JoinType.LEFT).get("username"), username),
						getCriteriaBuilder().equal(root.join("contratos",JoinType.LEFT).join("autenticacoes",JoinType.LEFT).get("senha"), senha))
				).setMaxResults(1).getSingleResult());
	}
	

}

