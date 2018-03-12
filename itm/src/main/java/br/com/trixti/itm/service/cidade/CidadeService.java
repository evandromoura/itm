package br.com.trixti.itm.service.cidade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.cidade.CidadeDAO;
import br.com.trixti.itm.entity.Cidade;
import br.com.trixti.itm.entity.Uf;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class CidadeService extends AbstractService<Cidade> {

	private @Inject CidadeDAO cidadeDAO;
	
	@Override
	public AbstractDAO<Cidade> getDAO() {
		return cidadeDAO;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Cidade> pesquisarPorUf(Uf uf){
		if(uf != null && uf.getSigla() != null){
			return cidadeDAO.pesquisarPorUf(uf);
		}else{
			return null;
		}	
	}

}






