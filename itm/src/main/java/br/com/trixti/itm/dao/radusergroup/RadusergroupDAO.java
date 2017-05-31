package br.com.trixti.itm.dao.radusergroup;

import javax.persistence.Query;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Radusergroup;

public class RadusergroupDAO extends AbstractDAO<Radusergroup> {

	public void excluirPorUsername(String username) {
		StringBuilder jql = new StringBuilder("DELETE FROM ");
		jql.append(this.getEntityType().getName());
		jql.append(" entity ");
		jql.append(" WHERE ");
		jql.append(" entity.username =:username ");
		Query query = this.getManager().createQuery(jql.toString());
		query.setParameter("username", username);
		query.executeUpdate();
	}

}
