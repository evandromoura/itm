package br.com.trixti.itm.threads.boleto;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.service.boleto.BoletoService;

@Named
@Singleton
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BoletoThread {

	private @Inject BoletoService boletoService;
	
	@Schedule(minute="*/1", hour="*", persistent=false)
	public void criarBoleto() throws Exception {
		boletoService.criarBoleto();
	}
	
}