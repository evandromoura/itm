package br.com.trixti.itm.threads.boleto;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.trixti.itm.service.contratolancamento.ContratoLancamentoService;

@Named
@Singleton
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class LancamentoThread {

	private @Inject ContratoLancamentoService contratoLancamentoService;
	
	@Schedule(second="*/10", minute="*", hour="*", persistent=false)
	public void criarLancamento() throws Exception {
//		contratoLancamentoService.criarLancamentos();
	}
	
}