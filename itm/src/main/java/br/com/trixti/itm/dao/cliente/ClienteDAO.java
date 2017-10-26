package br.com.trixti.itm.dao.cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.entity.StatusBoletoEnum;


@Stateless
public class ClienteDAO extends AbstractDAO<Cliente> {

	public List<Cliente> pesquisar(Cliente clientePesquisa) {
		CriteriaQuery<Cliente> criteria = getCriteriaBuilder().createQuery(Cliente.class);
		Root<Cliente> root = criteria.from(Cliente.class);
		Predicate[] predicates = comporFiltroPesquisa(root, clientePesquisa);
		return getManager().createQuery(criteria.select(root).distinct(true).where(predicates).orderBy(getCriteriaBuilder().desc(root.get("dataCriacao")))).getResultList();
	}
	
	private Predicate[] comporFiltroPesquisa(Root<Cliente> root,Cliente clientePesquisa){
		List<Predicate> predicateList = new ArrayList<Predicate>();
		
		if(clientePesquisa.getGrupo() != null && clientePesquisa.getGrupo().getId() != null){
			predicateList.add(getCriteriaBuilder().equal(root.join("contratos", JoinType.LEFT).join("contratoGrupos",JoinType.LEFT).get("grupo"),clientePesquisa.getGrupo()));
		}
		
		if(clientePesquisa.getProduto() != null && clientePesquisa.getProduto().getId() != null){
			predicateList.add(getCriteriaBuilder().equal(root.join("contratos", JoinType.LEFT).join("contratoProdutos",JoinType.LEFT).get("produto"),clientePesquisa.getProduto()));
		}
		
		if(clientePesquisa.getNome() != null && !clientePesquisa.getNome().equals("")){
			predicateList.add(getCriteriaBuilder().like(getCriteriaBuilder().lower(root.<String>get("nome")), "%"+ clientePesquisa.getNome().toLowerCase()+"%"));
		}
		if(clientePesquisa.getEmail() != null && !clientePesquisa.getEmail().equals("")){
			predicateList.add(getCriteriaBuilder().like(getCriteriaBuilder().lower(root.<String>get("email")), "%"+ clientePesquisa.getEmail().toLowerCase()+"%"));
		}
		
		if(clientePesquisa.getTelefoneCelular() != null && !clientePesquisa.getTelefoneCelular().equals("")){
			predicateList.add(getCriteriaBuilder().like(getCriteriaBuilder().lower(root.<String>get("telefoneCelular")), "%"+ clientePesquisa.getTelefoneCelular().toLowerCase()+"%"));
		}
		
		if(clientePesquisa.getLogin() != null && !clientePesquisa.getLogin().equals("")){
			predicateList.add(getCriteriaBuilder().like(getCriteriaBuilder().lower(root.join("contratos", JoinType.LEFT).join("autenticacoes",JoinType.LEFT).<String>get("username")) ,"%"+clientePesquisa.getLogin().toLowerCase()+"%"));
		}
		
		if(clientePesquisa.getCpfCnpj() != null && !clientePesquisa.getCpfCnpj().equals("")){
			predicateList.add(getCriteriaBuilder().like(getCriteriaBuilder().lower(root.<String>get("cpfCnpj")), "%"+ clientePesquisa.getCpfCnpj().toLowerCase()+"%"));
		}
		
		if(clientePesquisa.getStatusContrato() != null){
			predicateList.add(getCriteriaBuilder().equal(root.join("contratos", JoinType.LEFT).get("status"),clientePesquisa.getStatusContrato()));
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
					if(contrato.getContratoProdutos() != null){
						for(ContratoProduto contratoProduto:contrato.getContratoProdutos()){
							if(contratoProduto.getProduto() != null){
								contratoProduto.getProduto().getId();
							}
						}
					}
//					if(contrato.getBoletos() != null){
//						for(Boleto boleto:contrato.getBoletos()){
//							if(boleto.getLancamentos() != null){ 
//								boleto.getLancamentos().size();
//							}	
//						}
//					}
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
				.where( getCriteriaBuilder().equal(root.get("email"),username),
						getCriteriaBuilder().equal(root.get("senha"), senha))
				).setMaxResults(1).getSingleResult());
	}

	public Cliente recuperarPorEmail(String email) {
			CriteriaQuery<Cliente> criteria = getCriteriaBuilder().createQuery(Cliente.class);
			Root<Cliente> root = criteria.from(Cliente.class);
			return inicializarCliente(getManager().createQuery(criteria.select(root)
					.where(getCriteriaBuilder().equal(root.get("email"),email))
					).setMaxResults(1).getSingleResult());
	}

	public List<Cliente> listarPorBoletoAtrasado() {
		CriteriaQuery<Cliente> criteria = getCriteriaBuilder().createQuery(Cliente.class);
		Root<Cliente> root = criteria.from(Cliente.class);
		Join<Contrato, Boleto> join = root.join("contratos", JoinType.LEFT).join("boletos", JoinType.LEFT);
		return getManager()
				.createQuery(criteria.select(root).distinct(true).where(
						getCriteriaBuilder().lessThan(join.<Date>get("dataVencimento"), new Date()),
						getCriteriaBuilder().equal(join.get("status"), StatusBoletoEnum.ABERTO)))
				.getResultList();
	}
	

}

