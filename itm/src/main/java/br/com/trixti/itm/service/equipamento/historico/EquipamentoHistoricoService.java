package br.com.trixti.itm.service.equipamento.historico;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.equipamento.historico.EquipamentoHistoricoDAO;
import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.entity.EquipamentoHistorico;
import br.com.trixti.itm.service.AbstractService;


@Stateless
public class EquipamentoHistoricoService extends AbstractService<EquipamentoHistorico> {

	private @Inject EquipamentoHistoricoDAO equipamentoHistoricoDAO;
	
	@Override
	public AbstractDAO<EquipamentoHistorico> getDAO() {
		return equipamentoHistoricoDAO;
	}
	
	public List<EquipamentoHistorico> pesquisarPorEquipamento(Equipamento equipamento){
		return equipamentoHistoricoDAO.pesquisarPorEquipamento(equipamento);
	}
	
}
