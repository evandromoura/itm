package br.com.trixti.itm.service.sms;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.sms.SMSDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.entity.SMS;
import br.com.trixti.itm.entity.SMS.SMSBuilder;
import br.com.trixti.itm.infra.sms.EnvioSMSZenviaService;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.boleto.GeradorBoletoService;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.util.UtilData;
import br.com.trixti.itm.util.UtilString;
import br.com.zenvia.client.exception.RestClientException;


@Stateless
public class SMSService extends AbstractService<SMS> {

	private @Inject SMSDAO smsDAO;
	
	private @Inject EnvioSMSZenviaService envioSMSZenviaService;
	private @Inject GeradorBoletoService geradorBoletoService;
	private @Inject BoletoService boletoService;
	private @Inject ParametroService parametroService;
	
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
	public void enviarSMS(Boleto boleto){
		try {
			Parametro parametro = parametroService.recuperarParametro();
			SMS sms = new SMSBuilder().dddTelefone("").numeroTelefone(boleto.getContrato().getCliente().getTelefoneCelular()).mensagem(comporTextoSMS(boleto,parametro)).build();
			sms.setDataCriacao(LocalDateTime.now());
			smsDAO.incluir(sms);
			envioSMSZenviaService.enviar(sms);
			if(boleto.getDataSms() == null){
				boleto.setDataSms(new Date());
				boletoService.alterar(boleto);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Asynchronous
	public void enviarSMS(Boleto boleto,String msg){
		try {
			Parametro parametro = parametroService.recuperarParametro();
			SMS sms = new SMSBuilder().dddTelefone("").numeroTelefone(boleto.getContrato().getCliente().getTelefoneCelular()).mensagem(comporTextoSMS(boleto,msg,parametro)).build();
			sms.setDataCriacao(LocalDateTime.now());
			smsDAO.incluir(sms);
			envioSMSZenviaService.enviar(sms);
			if(boleto.getDataSms() == null){
				boleto.setDataSms(new Date());
				boletoService.alterar(boleto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Asynchronous
	public void enviarSMSSemData(Boleto boleto,String msg){
		try {
			Parametro parametro = parametroService.recuperarParametro();
			SMS sms = new SMSBuilder().dddTelefone("").numeroTelefone(boleto.getContrato().getCliente().getTelefoneCelular()).mensagem(comporTextoSMS(boleto,msg,parametro)).build();
			sms.setDataCriacao(LocalDateTime.now());
			smsDAO.incluir(sms);
			envioSMSZenviaService.enviar(sms);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String comporTextoSMS(Boleto boleto, Parametro parametro){
		UtilData utilData = new UtilData();
		UtilString utilString = new UtilString();
		StringBuilder sb = new StringBuilder();
		sb.append("Fatura de "+UtilData.obterMesPorMesNumerico(utilString.completaComZerosAEsquerda(String.valueOf(utilData.getMes(boleto.getDataVencimento())), 2)));
		sb.append("\n"); 
		sb.append("Valor: R$"+boleto.getValor());
		sb.append("\n");
		sb.append("Tel: ");
		sb.append(parametro.getNumeroAtendimentoTelefonico());
		sb.append("\n");
		sb.append("Cod Barra: "+geradorBoletoService.recuperarLinhaDigitavel(boleto));
		return sb.toString();
	}
	private String comporTextoSMS(Boleto boleto,String texto, Parametro parametro){
		StringBuilder sb = new StringBuilder();
		sb.append(texto);
		sb.append("\n"); 
		sb.append("Valor: R$"+boleto.getValor());
		sb.append("\n");
		sb.append("Tel: ");
		sb.append(parametro.getNumeroAtendimentoTelefonico());
		sb.append("\n");
		sb.append("Cod Barra: "+geradorBoletoService.recuperarLinhaDigitavel(boleto));
		return sb.toString();
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
