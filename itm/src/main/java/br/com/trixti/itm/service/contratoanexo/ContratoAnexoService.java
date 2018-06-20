package br.com.trixti.itm.service.contratoanexo;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.contratoanexo.ContratoAnexoDAO;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoAnexo;
import br.com.trixti.itm.service.AbstractService;

@Stateless
public class ContratoAnexoService extends AbstractService<ContratoAnexo> {

	
	private @Inject ContratoAnexoDAO contratoAnexoDAO;
	
	@Override
	public AbstractDAO<ContratoAnexo> getDAO() {
		return contratoAnexoDAO;
	}
	
	public List<ContratoAnexo> pesquisarPorContrato(Contrato contrato){
		return contratoAnexoDAO.pesquisarPorContrato(contrato);
	}
	

}
