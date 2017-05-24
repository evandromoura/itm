package br.com.trixti.itm.service.retorno;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.retorno.RetornoDAO;
import br.com.trixti.itm.entity.Retorno;
import br.com.trixti.itm.service.AbstractService;

@Stateless
public class RetornoService extends AbstractService<Retorno>{

	private @Inject RetornoDAO retornoDAO;

	@Override
	public AbstractDAO<Retorno> getDAO() {
		return retornoDAO;
	}
	
	public List<Retorno> pesquisar(Retorno retorno){
		return retornoDAO.pesquisar(retorno);
	}

	public Retorno recuperarCompleto(Integer id) {
		return retornoDAO.recuperarCompleto(id);
	} 
	
	public List<Retorno> listarPendentes(){
		return retornoDAO.listarPendentes();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void incluir(Retorno entidade) {
		super.incluir(entidade);
	}
	
	

	
}
