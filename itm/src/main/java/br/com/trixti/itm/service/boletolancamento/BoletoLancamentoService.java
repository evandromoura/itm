package br.com.trixti.itm.service.boletolancamento;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.boletolancamento.BoletoLancamentoDAO;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.service.AbstractService;

@Stateless
public class BoletoLancamentoService extends AbstractService<BoletoLancamento>{

	private @Inject BoletoLancamentoDAO boletoLancamentoDAO;

	@Override
	public AbstractDAO<BoletoLancamento> getDAO() {
		return boletoLancamentoDAO;
	}


	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void excluirPorContrato(Contrato contrato){
		boletoLancamentoDAO.excluirPorContrato(contrato);
	}

}
