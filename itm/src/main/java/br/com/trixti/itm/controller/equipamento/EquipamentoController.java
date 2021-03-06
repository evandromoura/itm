package br.com.trixti.itm.controller.equipamento;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Equipamento;
import br.com.trixti.itm.entity.EquipamentoMarca;
import br.com.trixti.itm.entity.EquipamentoMarcaModelo;
import br.com.trixti.itm.entity.EquipamentoTipo;
import br.com.trixti.itm.infra.security.annotations.CustomIdentity;
import br.com.trixti.itm.infra.security.annotations.SuporteNivel2;
import br.com.trixti.itm.service.equipamento.EquipamentoService;
import br.com.trixti.itm.to.EquipamentoTO;



@Named
@ViewScoped
@SuporteNivel2
public class EquipamentoController extends AbstractController<Equipamento> implements Serializable {
	
	
	private static final long serialVersionUID = -3430900005102330317L;
	private @Inject EquipamentoService equipamentoService;
	private EquipamentoTO equipamentoTO;
	private @Inject CustomIdentity customIdentity;
	
	
	@PostConstruct
	private void init(){
		String acao = getRequest().getParameter("acao");
		String parametro = getRequest().getParameter("parametro");
		
		if(acao != null && parametro != null){
			if(acao.equals("editar")){
				inicializarAlterar(Integer.valueOf(parametro));
			}
		}else if(acao != null && parametro == null){
				inicializarIncluir();
		}else{
			pesquisar();
		}
	}
	
	public void pesquisar(){
		getEquipamentoTO().setEquipamentos(equipamentoService.listar());
	}
	
		
	public String gravar(){
		getEquipamentoTO().getEquipamento().setUsuarioUltimaAtualizacao(customIdentity.getUsuario());
		if(getEquipamentoTO().getEquipamento().getId() == null){
			equipamentoService.incluir(getEquipamentoTO().getEquipamento());
		}else{
			equipamentoService.alterar(getEquipamentoTO().getEquipamento());
		}
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));
		return "sucesso";
	}
	
	private void inicializarAlterar(Integer id){
		getEquipamentoTO().setEquipamento(equipamentoService.recuperar(id));
	}
	
	private void inicializarIncluir(){
		getEquipamentoTO().setEquipamento(new Equipamento());
		getEquipamentoTO().getEquipamento().setTipo(new EquipamentoTipo());
		getEquipamentoTO().getEquipamento().setMarca(new EquipamentoMarca());
		getEquipamentoTO().getEquipamento().setModelo(new EquipamentoMarcaModelo());
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
	
	
	
	public void excluir(Equipamento equipamento){
		equipamentoService.excluir(equipamento);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi incluido na base"));
		pesquisar();
	}
	
	public void limparMarcaModelo(){
		getEquipamentoTO().getEquipamento().setMarca(new EquipamentoMarca());
		getEquipamentoTO().getEquipamento().setModelo(new EquipamentoMarcaModelo());
	}

	
	
}


