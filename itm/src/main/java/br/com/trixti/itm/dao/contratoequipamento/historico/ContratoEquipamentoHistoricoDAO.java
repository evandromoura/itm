package br.com.trixti.itm.dao.contratoequipamento.historico;

import java.util.List;

import javax.ejb.Stateless;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.entity.ContratoEquipamentoHistorico;
import br.com.trixti.itm.entity.Equipamento;

@Stateless
public class ContratoEquipamentoHistoricoDAO extends AbstractDAO<ContratoEquipamentoHistorico> {

	
	public List<ContratoEquipamentoHistorico> pesquisarPorEquipamento(Equipamento equipamento) {
		return null;
	}

}
