package br.com.trixti.itm.dao.cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.ClienteEquipamento;
import br.com.trixti.itm.entity.ClienteGrupo;
import br.com.trixti.itm.entity.ClienteProduto;


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
		
		if(clientePesquisa.getUsername() != null && !clientePesquisa.getUsername().equals("")){
			predicateList.add(getCriteriaBuilder().like(getCriteriaBuilder().lower(root.<String>get("username")), "%"+ clientePesquisa.getUsername().toLowerCase()+"%"));
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
		if(cliente.getClienteEquipamentos() != null){
			for(ClienteEquipamento clienteEquipamento:cliente.getClienteEquipamentos()){
				clienteEquipamento.getEquipamento().getId();
			}
		}
		if(cliente.getClienteProdutos() != null){
			for(ClienteProduto clienteProduto:cliente.getClienteProdutos()){
				System.out.println(clienteProduto.getProduto().getId());
				System.out.println(clienteProduto.getProduto().getNome());
			}
		}
		
		if(cliente.getClienteGrupos() != null){
			for(ClienteGrupo clienteGrupo:cliente.getClienteGrupos()){
				clienteGrupo.getGrupo().getId();
			}
		}
		
		if(cliente.getBoletos() != null){
			cliente.getBoletos().size();
			for(Boleto boleto:cliente.getBoletos()){
				if(boleto.getLancamentos() != null){
					for(BoletoLancamento boletoLancamento:boleto.getLancamentos()){
						boletoLancamento.getClienteLancamento().getDescricao();
					}
				}
			}
		}
		
		if(cliente.getContaCorrenteBoleto() != null){
			cliente.getContaCorrenteBoleto().getId();
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

}

