package br.com.trixti.itm.controller.thread;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.trixti.itm.infra.security.annotations.Financeiro;
import br.com.trixti.itm.service.thread.ThreadService;
import br.com.trixti.itm.to.ThreadTO;
import br.com.trixti.itm.util.UtilData;



@ViewScoped
@ManagedBean
@Financeiro
public class ThreadController{
	
	private @Inject ThreadService threadService;
	private ThreadTO threadTO;
	
	
	@PostConstruct
	private void init(){
		getThreadTO().setTimers(threadService.listar());
	}


	public ThreadTO getThreadTO() {
		if (threadTO == null) {
			threadTO = new ThreadTO();
		}
		return threadTO;
	}


	public void setThreadTO(ThreadTO threadTO) {
		this.threadTO = threadTO;
	}
	
	public String formatDate(Date date){
		UtilData utilData = new UtilData();
		return utilData.formatDate(date, "yyyy/MM/dd HH:mm:ss");
	}
	
	public void parar(String info){
		threadService.parar(info);
	}
	
}
