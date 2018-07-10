package br.com.trixti.itm.service.contratoequipamento;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.contratoequipamento.ContratoEquipamentoDAO;
import br.com.trixti.itm.entity.ContratoEquipamento;
import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.service.AbstractService;

@Stateless
public class ContratoEquipamentoService extends AbstractService<ContratoEquipamento> {
	
	private @Inject ContratoEquipamentoDAO contratoEquipamentoDAO;
	
	@Override
	public AbstractDAO<ContratoEquipamento> getDAO() {
		return contratoEquipamentoDAO;
	}
	
	public List<ContratoEquipamento> pesquisarPorEquipamento(Equipamento equipamento){
		return contratoEquipamentoDAO.pesquisarPorEquipamento(equipamento);
	}
	
	public BigDecimal qtdEquipamentoEmUso(Equipamento equipamento) {
		BigDecimal retorno = contratoEquipamentoDAO.qtdEquipamentoEmUso(equipamento);
		return retorno != null?retorno:BigDecimal.ZERO; 
	}

}
