package br.com.trixti.itm.controller.home;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.SMS.SMSBuilder;
import br.com.trixti.itm.entity.StatusBoletoEnum;
import br.com.trixti.itm.infra.security.annotations.CustomIdentity;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.boleto.GeradorBoletoService;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.service.mail.MailService;
import br.com.trixti.itm.service.radacct.RadacctService;
import br.com.trixti.itm.service.sms.SMSService;
import br.com.trixti.itm.service.snmp.SnmpService;
import br.com.trixti.itm.to.HomeTO;
import br.com.trixti.itm.util.UtilArquivo;
import br.com.trixti.itm.util.UtilData;

@ManagedBean
@ViewScoped
public class HomeController extends AbstractController<Object>{

	private HomeTO homeTO;
	private @Inject CustomIdentity customIdentity;
	private @Inject BoletoService boletoService;
	private @Inject RadacctService radacctService;
	private @Inject GeradorBoletoService geradorBoletoService;
	private @Inject SMSService smsService;
	private @Inject SnmpService snmpService;
	private @Inject MailService mailService;
	private @Inject ContratoService contratoService;
	
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
		}else{
			getHomeTO().setOid("1.3.6.1.2.1.2.2.1.2");
//			recarregarLog();
		}	
	}
	
	
	public void downloadBoleto(Boleto boleto) throws Exception {
		File arquivoBoleto = null;
		try {
			System.out.println("HomeController Gera boleto");
			arquivoBoleto = geradorBoletoService.gerarBoleto(boleto);
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
		}finally {
			if(arquivoBoleto != null){
				arquivoBoleto.delete();
			}
		}	
	}
	
	public void downloadSegundaViaBoleto(Boleto boleto) throws Exception {
		File arquivoBoleto = null;
		try {
			boleto.setDataVencimento(new Date());
			System.out.println("HomeControler Segundavia boleto");
			arquivoBoleto = geradorBoletoService.gerarBoleto(boleto);
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
		}finally {
			if(arquivoBoleto != null){
				arquivoBoleto.delete();
			}
		}	
	}
	
	
	public void atribuirContratoSelecionado(Contrato contrato){
		customIdentity.setContratoSelecionado(contrato);
	}
	
	public void enviarSMS(){
		smsService.enviarSMS(new SMSBuilder().dddTelefone("61").numeroTelefone("992988839").mensagem("Seu boleto foi gerado").build());
	}
	
	public void enviarEmail(Boleto boleto){
		mailService.enviarEmail(boleto,null,"Sua fatura está disponível");
		getFacesContext().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Email enviado com sucesso!", "Email enviado com sucesso!"));
	}
	
	public void enviarSMS(Boleto boleto){
		smsService.enviarSMS(boleto);
		getFacesContext().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "SMS enviado com sucesso!", "SMS enviado com sucesso!"));
	}
	
	public void promessaPagamento(Contrato contrato){
		contratoService.promessaPagamento(contrato);
		getFacesContext().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Promessa registrada com sucesso! Em breve sua internet será desbloqueada", "Promessa registrada com sucesso! Em breve sua internet será desbloqueada"));
	}
	
	public void enviarEmailTeste(){
		
		
//		mailService.
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
	
	public void recarregarLog(){
		getHomeTO().setListaSnmp(null);
		getHomeTO().setListaSnmp(snmpService.snmpWalk("",getHomeTO().getOid()));
	}
	
	public Integer calcularQtdBoletoAberto(Contrato contrato){
		Integer cont = 0;
		for(Boleto boleto:contrato.getBoletos()){
			cont = boleto.getStatus().equals(StatusBoletoEnum.ABERTO)?cont+1:cont;
		}
		return cont;
	}
	
	public String formatDate(Date date){
		UtilData utilData = new UtilData();
		return utilData.formatDate(date, "yyyy/MM/dd HH:mm:ss");
	}
}
