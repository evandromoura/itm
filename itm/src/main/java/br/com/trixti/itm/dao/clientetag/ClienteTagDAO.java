package br.com.trixti.itm.dao.clientetag;

import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.ClienteTag;

@Stateless
public class ClienteTagDAO extends AbstractDAO<ClienteTag> {
	
	
	public void excluirPorCliente(Cliente cliente){
		StringBuilder jql = new StringBuilder("delete from ClienteTag ct where ct.cliente = :cliente)");
		Query query = this.getManager().createQuery(jql.toString());
		query.setParameter("cliente", cliente);
		query.executeUpdate();
	}

}

