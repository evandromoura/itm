package br.com.trixti.itm.infra.msg;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.message.MessageContext;

@Stateless
public class MensagemFactory {

	private @Inject MessageContext messageContext;

	public String getMensagem(String key) {
		return messageContext.messageSource("resources").message().template("{"+key+"}").toString();
	}

	public String getMensagem(String key, String... params) {
		return messageContext.messageSource("resources").message().template("{"+key+"}").argumentArray(params).toString();
	}

}