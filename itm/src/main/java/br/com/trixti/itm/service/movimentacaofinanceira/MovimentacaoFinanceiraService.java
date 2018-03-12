package br.com.trixti.itm.service.movimentacaofinanceira;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.movimentacaofinanceira.MovimentacaoFinanceiraDAO;
import br.com.trixti.itm.entity.MovimentacaoFinanceira;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class MovimentacaoFinanceiraService extends AbstractService<MovimentacaoFinanceira> {

	private @Inject MovimentacaoFinanceiraDAO movimentacaoFinanceiraDAO;
	
	@Override
	public AbstractDAO<MovimentacaoFinanceira> getDAO() {
		return movimentacaoFinanceiraDAO;
	}

	@Override
	public void incluir(MovimentacaoFinanceira entidade) {
		entidade.setData(new Date());
		super.incluir(entidade);
	}
	
	
	public List<MovimentacaoFinanceira> listarSemArquivoSici(){
		return movimentacaoFinanceiraDAO.listarSemArquivoSici();
	}
	
	

}






