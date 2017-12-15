package br.com.trixti.itm.controller.log;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Log;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.service.log.LogService;
import br.com.trixti.itm.to.LogTO;



@Named
@ViewScoped
@Admin
public class LogController extends AbstractController<Log> implements Serializable{
	
	
	private static final long serialVersionUID = -2503972804160042054L;
	private @Inject LogService logService;
	private LogTO logTO;
	
	
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
		getLogTO().setLogs(
				logService.pesquisar(getLogTO().getLogPesquisa()));
	}
	
		
	
	public void gravar(){
		if(getLogTO().getLog().getId() == null){
			logService.incluir(getLogTO().getLog());
		}else{
			logService.alterar(getLogTO().getLog());
		}
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));
	}
	
	private void inicializarAlterar(Integer id){
		getLogTO().setLog(logService.recuperar(id));
	}
	
	private void inicializarIncluir(){
		getLogTO().setLog(new Log());
	}
	
	
	

	public LogTO getLogTO() {
		if (logTO == null) {
			logTO = new LogTO();
		}
		return logTO;
	}

	public void setLogTO(LogTO logTO) {
		this.logTO = logTO;
	}
	
	
	
	public void excluir(Log log){
		logService.excluir(log);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi incluido na base"));
		pesquisar();
	}

	
	
}


