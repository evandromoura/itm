package br.com.trixti.itm.dao.radgroupreply;

import javax.persistence.Query;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Radgroupreply;

public class RadgroupreplyDAO extends AbstractDAO<Radgroupreply> {

	public void excluirPorGroupname(String groupname) {
		StringBuilder jql = new StringBuilder("DELETE FROM ");
		jql.append(this.getEntityType().getName());
		jql.append(" entity ");
		jql.append(" WHERE ");
		jql.append(" entity.groupname =:groupname ");
		Query query = this.getManager().createQuery(jql.toString());
		query.setParameter("groupname", groupname);
		query.executeUpdate();
	}

}
