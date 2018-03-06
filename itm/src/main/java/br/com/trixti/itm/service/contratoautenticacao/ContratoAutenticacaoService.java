package br.com.trixti.itm.service.contratoautenticacao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.contratoautenticacao.ContratoAutenticacaoDAO;
import br.com.trixti.itm.entity.ContratoAutenticacao;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.freeradius.FreeRadiusService;

@Stateless
public class ContratoAutenticacaoService extends AbstractService<ContratoAutenticacao> {

	private @Inject ContratoAutenticacaoDAO contratoAutenticacaoDAO;
	
	private @Inject FreeRadiusService freeRadiusService;
	
	@Override
	public AbstractDAO<ContratoAutenticacao> getDAO() {
		return contratoAutenticacaoDAO;
	}
	
	public ContratoAutenticacao recuperarPorUsername(String username){
		try{
			return contratoAutenticacaoDAO.recuperarPorUsername(username);
		}catch(Exception e){
			return null;
		}	
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void excluir(ContratoAutenticacao entidade) {
		freeRadiusService.excluirPorUsername(entidade.getUsername());
		super.excluir(entidade);
	}
	
	
	
}
