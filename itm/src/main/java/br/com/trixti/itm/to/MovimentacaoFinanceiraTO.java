package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.MovimentacaoFinanceira;

public class MovimentacaoFinanceiraTO {

	private MovimentacaoFinanceira movimentacaoFinanceira;
	private List<MovimentacaoFinanceira> movimentacaoFinanceiras;
	private MovimentacaoFinanceira movimentacaoFinanceiraPesquisa;
	
	
	public MovimentacaoFinanceira getMovimentacaoFinanceira() {
		if (movimentacaoFinanceira == null) {
			movimentacaoFinanceira = new MovimentacaoFinanceira();
		}
		return movimentacaoFinanceira;
	}
	
	public void setMovimentacaoFinanceira(MovimentacaoFinanceira movimentacaoFinanceira) {
		this.movimentacaoFinanceira = movimentacaoFinanceira;
	}
	
	public List<MovimentacaoFinanceira> getMovimentacaoFinanceiras() {
		if (movimentacaoFinanceiras == null) {
			movimentacaoFinanceiras = new ArrayList<MovimentacaoFinanceira>();
		}

		return movimentacaoFinanceiras;
	}
	
	public void setMovimentacaoFinanceiras(List<MovimentacaoFinanceira> movimentacaoFinanceiras) {
		this.movimentacaoFinanceiras = movimentacaoFinanceiras;
	}

	public MovimentacaoFinanceira getMovimentacaoFinanceiraPesquisa() {
		if (movimentacaoFinanceiraPesquisa == null) {
			movimentacaoFinanceiraPesquisa = new MovimentacaoFinanceira();
		}
		return movimentacaoFinanceiraPesquisa;
	}

	public void setMovimentacaoFinanceiraPesquisa(MovimentacaoFinanceira movimentacaoFinanceiraPesquisa) {
		this.movimentacaoFinanceiraPesquisa = movimentacaoFinanceiraPesquisa;
	}

}