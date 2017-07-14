package br.com.trixti.itm.infra.sms;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.inject.Inject;

import br.com.trixti.itm.dao.sms.SMSDAO;
import br.com.trixti.itm.entity.SMS;
import br.com.trixti.itm.entity.SituacaoSMSEnum;
import br.com.trixti.itm.util.UtilData;
import br.com.zenvia.client.RestClient;
import br.com.zenvia.client.exception.RestClientException;
import br.com.zenvia.client.request.MessageSmsElement;
import br.com.zenvia.client.request.MultipleMessageSms;
import br.com.zenvia.client.request.SingleMessageSms;
import br.com.zenvia.client.response.SendMultipleSmsResponse;
import br.com.zenvia.client.response.SendSmsResponse;

@Singleton
public class EnvioSMSZenviaService {

	private static final String SENHA = "FFntkDxKW5";
//	private static final String SENHA = "ugWb4Gyr";
	private static final String CONTA = "trix.int";
	private static final String DDI = "55";
	private static final String ITM = "ITRIX";
	private @Inject SMSDAO smsdao;

	private RestClient restClient;

	@PostConstruct
	private void init() {
		restClient = new RestClient();
		restClient.setUsername(CONTA);
		restClient.setPassword(SENHA);
	}

	@Asynchronous
	public void enviar(SMS... listaSMS) throws RestClientException {
		if (listaSMS.length > 1) {
			enviarMensagens(listaSMS);
		} else {
			enviarMensagem(listaSMS[0]);
		}
	}

	private void enviarMensagens(SMS... listaSMS) throws RestClientException {
		MultipleMessageSms multipleMessageSms = converterListaSMS(listaSMS);
		SendMultipleSmsResponse resposta = restClient.sendMultipleSms(multipleMessageSms);
		converterRespostas(resposta, listaSMS);
	}

	private void converterRespostas(SendMultipleSmsResponse resposta, SMS... listaSMS) {
		for (int i = 0; i < listaSMS.length; i++) {
			converterResposta(resposta.getResponses().get(i), listaSMS[i]);
		}
	}

	private MultipleMessageSms converterListaSMS(SMS[] listaSMS) {
		MultipleMessageSms multipleMessageSms = new MultipleMessageSms();
		for (SMS sms : listaSMS) {
			MessageSmsElement messageSmsElement = converterSMSElement(sms);
			multipleMessageSms.addMessageSms(messageSmsElement);
		}
		return multipleMessageSms;
	}

	private void enviarMensagem(SMS sms) throws RestClientException {
		SingleMessageSms singleMessageSms = converterSMS(sms);
		SendSmsResponse resposta = restClient.sendSms(singleMessageSms);
		converterResposta(resposta, sms);
	}

	private void converterResposta(SendSmsResponse resposta, SMS sms) {
		if (resposta.getStatusCode().equals(SMSZenviaStatusCodeEnum.OK.getCodigo())) {
			sms.setSituacao(SituacaoSMSEnum.ENVIADO);
			sms.setDataEnvio(LocalDateTime.now());
		} else if (resposta.getStatusCode().equals(SMSZenviaStatusCodeEnum.SCHEDULED.getCodigo())) {
			sms.setSituacao(SituacaoSMSEnum.AGENDADO);
		} else if (resposta.getStatusCode().equals(SMSZenviaStatusCodeEnum.SENT.getCodigo())) {
			sms.setSituacao(SituacaoSMSEnum.ENVIADO);
			sms.setDataEnvio(LocalDateTime.now());
		} else if (resposta.getStatusCode().equals(SMSZenviaStatusCodeEnum.DELIVERED.getCodigo())) {
			sms.setSituacao(SituacaoSMSEnum.ENVIADO);
			sms.setDataEnvio(LocalDateTime.now());
		} else if (resposta.getStatusCode().equals(SMSZenviaStatusCodeEnum.NOT_RECEIVED.getCodigo())) {
			sms.setSituacao(SituacaoSMSEnum.NAO_RECEBIDO);
		} else if (resposta.getStatusCode().equals(SMSZenviaStatusCodeEnum.BLOCKED_INVALID_NUMBER.getCodigo())) {
			sms.setSituacao(SituacaoSMSEnum.NUMERO_INVALIDO);
		} else if (resposta.getStatusCode().equals(SMSZenviaStatusCodeEnum.ERROR.getCodigo())) {
			sms.setSituacao(SituacaoSMSEnum.ERRO_DESCONHECIDO);
		} else if (resposta.getDetailCode().equals(SMSZenviaStatusCodeEnum.ACCOUNT_LIMITE_REACHED.getCodigo())) {
			sms.setSituacao(SituacaoSMSEnum.LIMITE_EXCEDIDO);
		} else {
			sms.setSituacao(SituacaoSMSEnum.ERRO_DESCONHECIDO);
		}
		sms.setDataEnvio(LocalDateTime.now());
		smsdao.alterar(sms);
	}

	private SingleMessageSms converterSMS(SMS sms) {
		SingleMessageSms singleMessageSms = new SingleMessageSms();
		singleMessageSms.setId(sms.getId().toString());
		singleMessageSms.setTo(DDI+sms.getDddTelefone()+sms.getNumeroTelefone());
		singleMessageSms.setMsg(sms.getMensagem());
		singleMessageSms.setFrom(ITM);
		singleMessageSms.setSchedule(UtilData.converterLocalDateTimeParaDate(sms.getDataEnvio()));
		return singleMessageSms;
	}

	private MessageSmsElement converterSMSElement(SMS sms) {
		MessageSmsElement messageSmsElement = new MessageSmsElement();
		messageSmsElement.setId(sms.getId().toString());
		messageSmsElement.setTo(DDI+sms.getDddTelefone()+sms.getNumeroTelefone());
		messageSmsElement.setMsg(sms.getMensagem());
		messageSmsElement.setFrom(ITM);
		messageSmsElement.setSchedule(UtilData.converterLocalDateTimeParaDate(sms.getDataEnvio()));
		return messageSmsElement;
	}

//	public static void main(String[] args) {
//		EnvioSMSHumanService s = new EnvioSMSHumanService();
//		SMS sms = new SMS();
//		sms.setId(new Random().nextLong());
//		sms.setDddTelefone("61");
//		sms.setNumeroTelefone("996831577");
//		sms.setMensagem("teste rest api");
//		try {
//			s.enviar(sms);
//		} catch (RestClientException e) {
//			e.printStackTrace();
//		}
//	}

}