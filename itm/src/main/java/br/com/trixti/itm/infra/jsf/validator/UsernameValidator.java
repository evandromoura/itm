package br.com.trixti.itm.infra.jsf.validator;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.trixti.itm.service.contratoautenticacao.ContratoAutenticacaoService;
import br.com.trixti.itm.util.EjbUtil;
import br.com.trixti.itm.util.UtilString;

@FacesValidator(value = "UsernameValidator")
public class UsernameValidator implements Validator {

	private  ContratoAutenticacaoService contratoAutenticacaoService;

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		EjbUtil<ContratoAutenticacaoService> ejbUtil = new EjbUtil<ContratoAutenticacaoService>();
		UtilString utilString = new UtilString();
		try {
			contratoAutenticacaoService = ejbUtil.getService("ContratoAutenticacaoService");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!utilString.vazio(value.toString()) && contratoAutenticacaoService.recuperarPorUsername(value.toString()) != null) {
			ResourceBundle rb = ResourceBundle.getBundle("resources", context.getViewRoot().getLocale());
			String messageText = rb.getString("label.componente.username.invalido");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, messageText, messageText));
//			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, messageText, messageText));
		}
	}

}