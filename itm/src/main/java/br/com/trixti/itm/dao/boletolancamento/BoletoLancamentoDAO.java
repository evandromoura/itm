package br.com.trixti.itm.dao.boletolancamento;

import javax.persistence.Query;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Contrato;

public class BoletoLancamentoDAO extends AbstractDAO<BoletoLancamento> {

	public void excluirPorContrato(Contrato contrato) {
		StringBuilder jql = new StringBuilder("delete from BoletoLancamento b1 where b1.boleto.id in "
				+ "(select id from Boleto b2 where b1.boleto.id = b2.id and b2.contrato = :contrato)");
		Query query = this.getManager().createQuery(jql.toString());
		query.setParameter("contrato", contrato);
		query.executeUpdate();
	}
	
	
}
