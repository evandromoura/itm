package br.com.trixti.itm.service.contratoequipamento.historico;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.contratoequipamento.historico.ContratoEquipamentoHistoricoDAO;
import br.com.trixti.itm.entity.ContratoEquipamentoHistorico;
import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.service.AbstractService;

@Stateless
public class ContratoEquipamentoHistoricoService extends AbstractService<ContratoEquipamentoHistorico> {

	
	private @Inject ContratoEquipamentoHistoricoDAO contratoEquipamentoHistoricoDAO;
	
	@Override
	public AbstractDAO<ContratoEquipamentoHistorico> getDAO() {
		return contratoEquipamentoHistoricoDAO;
	}
	
	public List<ContratoEquipamentoHistorico> pesquisarPorEquipamento(Equipamento equipamento){
		return contratoEquipamentoHistoricoDAO.pesquisarPorEquipamento(equipamento);
	}
	

}
