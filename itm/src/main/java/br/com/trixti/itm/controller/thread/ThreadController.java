package br.com.trixti.itm.controller.thread;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.infra.security.annotations.Financeiro;
import br.com.trixti.itm.service.thread.ThreadService;
import br.com.trixti.itm.to.ThreadTO;
import br.com.trixti.itm.util.UtilData;



@Named
@ViewScoped
@Financeiro
public class ThreadController implements Serializable{
	
	private static final long serialVersionUID = -3061878692187354148L;
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
