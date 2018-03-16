package br.com.trixti.itm.service.custofixo;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.custofixo.CustoFixoDAO;
import br.com.trixti.itm.entity.CustoFixo;
import br.com.trixti.itm.entity.TipoCentroCusto;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class CustoFixoService extends AbstractService<CustoFixo> {

	private @Inject CustoFixoDAO custoFixoDAO;
	
	@Override
	public AbstractDAO<CustoFixo> getDAO() {
		return custoFixoDAO;
	}

	public BigDecimal somarTodos() {
		return custoFixoDAO.somarTodos();
	}

	public BigDecimal somar(TipoCentroCusto tipoCentroCusto) {
		BigDecimal total = custoFixoDAO.somar(tipoCentroCusto);
		if(total != null){
			return total;
		}else{
			return BigDecimal.ZERO;
		}
	}

}






