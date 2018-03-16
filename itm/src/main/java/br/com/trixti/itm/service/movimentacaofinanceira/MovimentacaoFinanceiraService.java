package br.com.trixti.itm.service.movimentacaofinanceira;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.movimentacaofinanceira.MovimentacaoFinanceiraDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.CentroCusto;
import br.com.trixti.itm.entity.MovimentacaoFinanceira;
import br.com.trixti.itm.entity.TipoCentroCusto;
import br.com.trixti.itm.entity.TipoMovimentacaoFinanceira;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.centrocusto.CentroCustoService;


@Stateless
public class MovimentacaoFinanceiraService extends AbstractService<MovimentacaoFinanceira> {

	private @Inject MovimentacaoFinanceiraDAO movimentacaoFinanceiraDAO;
	private @Inject CentroCustoService centroCustoService;
	
	@Override
	public AbstractDAO<MovimentacaoFinanceira> getDAO() {
		return movimentacaoFinanceiraDAO;
	}

	@Override
	public void incluir(MovimentacaoFinanceira entidade) {
		entidade.setData(new Date());
		super.incluir(entidade);
	}
	
	public void incluir(Boleto boleto){
		MovimentacaoFinanceira movimentacaoFinanceira = new MovimentacaoFinanceira();
		movimentacaoFinanceira.setData(boleto.getDataPagamento());
		movimentacaoFinanceira.setValor(boleto.getValorPago());
		movimentacaoFinanceira.setTipo(TipoMovimentacaoFinanceira.CREDITO);
		movimentacaoFinanceira.setNome("Boleto "+boleto.getNossoNumero() + " - "+boleto.getContrato().getCliente().getNome());
		CentroCusto centroCusto = centroCustoService.recuperarPrincipal(TipoCentroCusto.MENSALIDADE);
		movimentacaoFinanceira.setCentroCusto(centroCusto);
		movimentacaoFinanceiraDAO.incluir(movimentacaoFinanceira);
	}
	
	public List<MovimentacaoFinanceira> listarSemArquivoSici(){
		return movimentacaoFinanceiraDAO.listarSemArquivoSici();
	}
	
	public BigDecimal somar(TipoMovimentacaoFinanceira tipo,Date data,boolean anual,TipoCentroCusto... tipoCentroCusto){
		BigDecimal total = movimentacaoFinanceiraDAO.somar(tipo,data,anual,tipoCentroCusto);
		if(total != null){
			return total;
		}else{
			return BigDecimal.ZERO;
		}
	}

}