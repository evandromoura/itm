package br.com.trixti.itm.controller.relatorio.contrato;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoGrupo;
import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.to.RelatorioContratoTO;



@ViewScoped
@ManagedBean
public class RelatorioContratoController{
	

	private @Inject ContratoService contratoService;
	
	private RelatorioContratoTO relatorioContratoTO;
	
	@PostConstruct
	private void init(){
		getRelatorioContratoTO().setContratos(contratoService.listarRelatorio());
		comporRelatorio();
	}
	
	private void comporRelatorio(){
		for(Contrato contrato:getRelatorioContratoTO().getContratos()){
			for(ContratoGrupo contratoGrupo:contrato.getContratoGrupos()){
				if(getRelatorioContratoTO().getMapaQuantidadePorGrupo().get(contratoGrupo.getGrupo()) == null){
					getRelatorioContratoTO().getMapaQuantidadePorGrupo().put(contratoGrupo.getGrupo(), 1);
				}else{
					getRelatorioContratoTO().getMapaQuantidadePorGrupo().put(contratoGrupo.getGrupo(), 
							getRelatorioContratoTO().getMapaQuantidadePorGrupo().get(contratoGrupo.getGrupo()) + 1);
				}
			}
			for(ContratoProduto contratoProduto:contrato.getContratoProdutos()){
				if(getRelatorioContratoTO().getMapaQuantidadePorProduto().get(contratoProduto.getProduto()) == null){
					getRelatorioContratoTO().getMapaQuantidadePorProduto().put(contratoProduto.getProduto(), 1);
				}else{
					getRelatorioContratoTO().getMapaQuantidadePorProduto().put(contratoProduto.getProduto(),
							getRelatorioContratoTO().getMapaQuantidadePorProduto().get(contratoProduto.getProduto()) + 1);
				}
			}
			if(getRelatorioContratoTO().getMapaQuantidadePorStatusContrato().get(contrato.getStatus()) == null){
				getRelatorioContratoTO().getMapaQuantidadePorStatusContrato().put(contrato.getStatus(), 1);
			}else{
				getRelatorioContratoTO().getMapaQuantidadePorStatusContrato().put(contrato.getStatus(),  
							getRelatorioContratoTO().getMapaQuantidadePorStatusContrato().get(contrato.getStatus()) + 1);
			}
		}
	}
	

	public RelatorioContratoTO getRelatorioContratoTO() {
		if (relatorioContratoTO == null) {
			relatorioContratoTO = new RelatorioContratoTO();
		}
		return relatorioContratoTO;
	}

	public void setRelatorioContratoTO(RelatorioContratoTO relatorioContratoTO) {
		this.relatorioContratoTO = relatorioContratoTO;
	}

	
}


