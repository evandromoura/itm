package br.com.trixti.itm.service.equipamento;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.equipamento.EquipamentoDAO;
import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.contratoequipamento.ContratoEquipamentoService;
import br.com.trixti.itm.service.contratoequipamento.historico.ContratoEquipamentoHistoricoService;
import br.com.trixti.itm.service.equipamento.historico.EquipamentoHistoricoService;


@Stateless
public class EquipamentoService extends AbstractService<Equipamento> {

	private @Inject EquipamentoDAO equipamentoDAO;
	
	private @Inject EquipamentoHistoricoService equipamentoHistoricoService;
	private @Inject ContratoEquipamentoHistoricoService contratoEquipamentoHistoricoService;
	private @Inject ContratoEquipamentoService contratoEquipamentoService;
	
	@Override
	public AbstractDAO<Equipamento> getDAO() {
		return equipamentoDAO;
	}
	
	public Equipamento recuperarCompleto(Integer id){
		Equipamento equipamento = super.recuperar(id);
		equipamento.setHistorico(equipamentoHistoricoService.pesquisarPorEquipamento(equipamento));
		equipamento.setContratos(contratoEquipamentoService.pesquisarPorEquipamento(equipamento));
		equipamento.setContratosHistorico(contratoEquipamentoHistoricoService.pesquisarPorEquipamento(equipamento));
		equipamento.setQuantidadeDisponivel(equipamento.getQuantidade().subtract(contratoEquipamentoService.qtdEquipamentoEmUso(equipamento)));
		return equipamento;
	}

	@Override
	public void alterar(Equipamento entidade) {
		entidade.setDataUltimaAtualizacao(new Date());
		super.alterar(entidade);
	}
	
	
}
