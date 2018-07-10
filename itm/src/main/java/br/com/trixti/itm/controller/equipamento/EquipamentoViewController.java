package br.com.trixti.itm.controller.equipamento;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.entity.EquipamentoHistorico;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.service.equipamento.EquipamentoService;
import br.com.trixti.itm.to.EquipamentoTO;
import br.com.trixti.itm.util.UtilReflection;



@Named
@ViewScoped
@Admin
public class EquipamentoViewController extends AbstractController<Equipamento> implements Serializable {
	
	
	private static final long serialVersionUID = 1905725996865633300L;
	private @Inject EquipamentoService equipamentoService;
	private EquipamentoTO equipamentoTO;
	
	
	@PostConstruct
	private void init(){
		String parametro = getRequest().getParameter("parametro");
		inicializar(Integer.valueOf(parametro));
	}
	
	private void inicializar(Integer id){
		getEquipamentoTO().setEquipamento(equipamentoService.recuperarCompleto(id));
	}

	public EquipamentoTO getEquipamentoTO() {
		if (equipamentoTO == null) {
			equipamentoTO = new EquipamentoTO();
		}
		return equipamentoTO;
	}

	public void setEquipamentoTO(EquipamentoTO equipamentoTO) {
		this.equipamentoTO = equipamentoTO;
	}
	
	
	public String getColorTd(EquipamentoHistorico equipamentoHistorico,String field)throws Exception{
		UtilReflection utilReflection = new UtilReflection();
		EquipamentoHistorico equipamentoHistoricoOld = null;
		for(EquipamentoHistorico equipamentoHistoricoIt:getEquipamentoTO().getEquipamento().getHistorico()){
			if(equipamentoHistoricoOld != null &&
					equipamentoHistorico.getPk().equals(equipamentoHistoricoIt.getPk()) &&
					utilReflection.getMethod(equipamentoHistoricoOld, field).invoke(equipamentoHistoricoOld, null) != null && 
							utilReflection.getMethod(equipamentoHistoricoIt, field).invoke(equipamentoHistoricoIt, null) != null &&
					
						!utilReflection.getMethod(equipamentoHistoricoOld, field).invoke(equipamentoHistoricoOld, null).equals(
								utilReflection.getMethod(equipamentoHistoricoIt, field).invoke(equipamentoHistoricoIt, null))
					){
				return "badge badge-success";
			}
			equipamentoHistoricoOld = equipamentoHistoricoIt;
		}
		return "";
	}
	
}


