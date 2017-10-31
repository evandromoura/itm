package br.com.trixti.itm.service.tag;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.tag.TagDAO;
import br.com.trixti.itm.entity.Tag;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class TagService extends AbstractService<Tag> {

	private @Inject TagDAO tagDAO;
	
	
	@Override
	public AbstractDAO<Tag> getDAO() {
		return tagDAO;
	}


	public List<Tag> pesquisar(Tag tag) {
		return tagDAO.pesquisar(tag);
	}
	

}






