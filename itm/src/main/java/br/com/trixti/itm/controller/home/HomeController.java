package br.com.trixti.itm.controller.home;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.SMS.SMSBuilder;
import br.com.trixti.itm.infra.security.annotations.CustomIdentity;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.boleto.GeradorBoletoService;
import br.com.trixti.itm.service.mail.MailService;
import br.com.trixti.itm.service.radacct.RadacctService;
import br.com.trixti.itm.service.sms.SMSService;
import br.com.trixti.itm.to.HomeTO;
import br.com.trixti.itm.util.UtilArquivo;
import br.com.trixti.itm.util.UtilData;
import br.com.trixti.itm.util.UtilString;

@Model
public class HomeController extends AbstractController<Object>{

	private HomeTO homeTO;
	private @Inject CustomIdentity customIdentity;
	private @Inject BoletoService boletoService;
	private @Inject RadacctService radacctService;
	private @Inject GeradorBoletoService geradorBoletoService;
	private @Inject MailService mailService;
	
	private @Inject SMSService smsService;
	
	@PostConstruct
	private void init(){
		if(customIdentity.getCliente() != null){
			getHomeTO().setCliente(customIdentity.getCliente());
			getHomeTO().setListaUltimosBoletos(boletoService.pesquisarUltimosBoletosCliente(getHomeTO().getCliente()));
			for(Contrato contrato:getHomeTO().getCliente().getContratos()){
				if(contrato.getAutenticacoes() != null && !contrato.getAutenticacoes().isEmpty()){
					contrato.setListaUtilizacao(radacctService.pesquisarUltimosPorUsername(contrato.getAutenticacoes().get(0).getUsername()));
				}	
			}
		}	
	}
	
	
	public void downloadBoleto(Boleto boleto) throws Exception {
		try {
			File arquivoBoleto = geradorBoletoService.gerarBoleto(boleto);
			if (arquivoBoleto != null) {
				UtilArquivo utilArquivo = new UtilArquivo();
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				utilArquivo.convertFileToByteArrayOutputStream(arquivoBoleto, byteArrayOutputStream);
				download(byteArrayOutputStream, arquivoBoleto.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			getFacesContext().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}	
	}
	
	public void enviarSMS(){
		smsService.enviarSMS(new SMSBuilder().dddTelefone("61").numeroTelefone("982251415").mensagem("Seu boleto foi gerado").build());
	}

	public HomeTO getHomeTO() {
		if (homeTO == null) {
			homeTO = new HomeTO();
		}
		return homeTO;
	}

	public void setHomeTO(HomeTO homeTO) {
		this.homeTO = homeTO;
	}
	
}
