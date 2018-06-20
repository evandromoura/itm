package br.com.trixti.itm.to;

import java.util.ArrayList;
import java.util.List;

import br.com.trixti.itm.entity.Equipamento;

public class EquipamentoTO {

	private Equipamento equipamento;
	private List<Equipamento> equipamentos;
	private Equipamento equipamentoPesquisa;
	
	
	public Equipamento getEquipamento() {
		if (equipamento == null) {
			equipamento = new Equipamento();
		}
		return equipamento;
	}
	
	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}
	
	public List<Equipamento> getEquipamentos() {
		if (equipamentos == null) {
			equipamentos = new ArrayList<Equipamento>();
		}

		return equipamentos;
	}
	
	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}

	public Equipamento getEquipamentoPesquisa() {
		if (equipamentoPesquisa == null) {
			equipamentoPesquisa = new Equipamento();
		}
		return equipamentoPesquisa;
	}

	public void setEquipamentoPesquisa(Equipamento equipamentoPesquisa) {
		this.equipamentoPesquisa = equipamentoPesquisa;
	}

}