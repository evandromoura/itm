package br.com.trixti.itm.service.contratoautenticacao;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.contratoautenticacao.ContratoAutenticacaoDAO;
import br.com.trixti.itm.entity.ContratoAutenticacao;
import br.com.trixti.itm.service.AbstractService;

@Stateless
public class ContratoAutenticacaoService extends AbstractService<ContratoAutenticacao> {

	private @Inject ContratoAutenticacaoDAO contratoAutenticacaoDAO;
	
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
	
}
