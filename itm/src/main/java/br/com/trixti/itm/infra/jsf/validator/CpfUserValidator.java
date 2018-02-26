package br.com.trixti.itm.infra.jsf.validator;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.util.EjbUtil;
import br.com.trixti.itm.util.UtilString;

@FacesValidator(value = "CpfUserValidator")
public class CpfUserValidator implements Validator {

	private  ClienteService clienteService;

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		EjbUtil<ClienteService> ejbUtil = new EjbUtil<ClienteService>();
		try {
			clienteService = ejbUtil.getService("ClienteService");
		} catch (Exception e) {
			e.printStackTrace();
		}
		UtilString utilString = new UtilString();
		String hidden = context.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hidden1");
		if(utilString.vazio(hidden)){
			hidden = "0"; 
		}
		
		String cpf = (String) value;
		Integer idClient = Integer.parseInt(hidden);
		if (cpf != null && !cpf.isEmpty()){
			Cliente client;
			client = clienteService.recuperarPorCpf(cpf);
			if (client != null && client.getId().intValue() != idClient.intValue()){
				ResourceBundle rb = ResourceBundle.getBundle("resources", context.getViewRoot().getLocale());
				String messageText = rb.getString("label.componente.cpf.existente");
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,messageText, messageText));
			}
		}
	}

}