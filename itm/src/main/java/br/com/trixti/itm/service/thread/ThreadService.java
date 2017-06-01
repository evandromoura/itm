package br.com.trixti.itm.service.thread;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.trixti.itm.threads.financeiro.FinanceiroThread;

@Stateless
public class ThreadService{

	private @Inject FinanceiroThread financeiroThread;
	
	public List<Timer> listar(){
		return financeiroThread.listarThreads();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void parar(String info) {
		try{
			financeiroThread.parar(info);
		}catch(Throwable e){
			
			
		}	
	}
}
