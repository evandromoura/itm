package br.com.trixti.itm.dao.radreply;

import javax.persistence.Query;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.Radreply;

public class RadreplyDAO extends AbstractDAO<Radreply> {

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

	public void excluirPorUsernameAttributeValue(String username, String attribute, String value) {
		StringBuilder jql = new StringBuilder("DELETE FROM ");
		jql.append(this.getEntityType().getName());
		jql.append(" entity ");
		jql.append(" WHERE ");
		jql.append(" entity.username =:username and ");
		jql.append(" entity.attribute =:attribute and ");
		jql.append(" entity.value =:value ");
		Query query = this.getManager().createQuery(jql.toString());
		query.setParameter("username", username);
		query.setParameter("attribute", attribute);
		query.setParameter("value", value);
		query.executeUpdate();
		
	}
	
	
	public void excluirPorUsernameAttribute(String username, String attribute) {
		StringBuilder jql = new StringBuilder("DELETE FROM ");
		jql.append(this.getEntityType().getName());
		jql.append(" entity ");
		jql.append(" WHERE ");
		jql.append(" entity.username =:username and ");
		jql.append(" entity.attribute =:attribute ");
		Query query = this.getManager().createQuery(jql.toString());
		query.setParameter("username", username);
		query.setParameter("attribute", attribute);
		query.executeUpdate();
		
	}

}
