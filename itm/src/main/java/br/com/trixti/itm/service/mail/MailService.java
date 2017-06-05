package br.com.trixti.itm.service.mail;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.boleto.GeradorBoletoService;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.util.UtilData;
import br.com.trixti.itm.util.UtilEmail;
import br.com.trixti.itm.util.UtilString;

@Stateless
public class MailService {

	private @Inject ParametroService parametroService;
	private @Inject GeradorBoletoService geradorBoletoService;
	private @Inject BoletoService boletoService;

	@Asynchronous
	public void enviarEmail(Boleto boleto) {
		File arquivoBoleto = geradorBoletoService.gerarBoleto(boleto);
		UtilData utildata = new UtilData();
		UtilString utilString = new UtilString();
		String titulo = "ITRIX Sua fatura do mês "+UtilData.obterMesPorMesNumerico(utilString.completaComZerosAEsquerda(String.valueOf(utildata.getMes(new Date())), 2))+" chegou!";
		final Parametro parametro = parametroService.recuperarParametro();
		Properties props = new Properties();
		String from = "itrixcobranca@gmail.com";
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", parametro.getSmtp());
		props.put("mail.smtp.port", parametro.getPortaSmtp().toString());

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(parametro.getLoginEmail(), parametro.getSenhaEmail());
			}
		});

		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(boleto.getContrato().getCliente().getEmail()));
			message.setSubject(titulo);
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(UtilEmail.corpo,"text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(arquivoBoleto);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(arquivoBoleto.getName());
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			System.out.println(boleto.getContrato().getCliente().getEmail() +" - Sent message successfully....");
			boleto.setDataNotificacao(new Date());
			boletoService.alterar(boleto);
			
			
			
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}

}