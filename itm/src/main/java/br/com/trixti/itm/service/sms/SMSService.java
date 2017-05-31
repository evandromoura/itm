package br.com.trixti.itm.service.sms;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.sms.SMSDAO;
import br.com.trixti.itm.entity.SMS;
import br.com.trixti.itm.infra.sms.EnvioSMSZenviaService;
import br.com.trixti.itm.service.AbstractService;
import br.com.zenvia.client.exception.RestClientException;


@Stateless
public class SMSService extends AbstractService<SMS> {

	private @Inject SMSDAO smsDAO;
	
	private @Inject EnvioSMSZenviaService envioSMSZenviaService;
	
	@Override
	public AbstractDAO<SMS> getDAO() {
		return smsDAO;
	}

	public List<SMS> pesquisar(SMS sms) {
		return smsDAO.pesquisar(sms);
	}
	
	@Asynchronous
	public void enviarSMS(SMS sms){
		try {
			sms.setDataCriacao(LocalDateTime.now());
			smsDAO.incluir(sms);
			envioSMSZenviaService.enviar(sms);
			sms.setDataEnvio(LocalDateTime.now());
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	}
	
	@Asynchronous
	public void enviarSMS(SMS... sms){
		try {
			smsDAO.incluirLista(Arrays.asList(sms));
			envioSMSZenviaService.enviar(sms);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
	}

	
}
