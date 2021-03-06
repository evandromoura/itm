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

import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationParameter;

import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Cliente;
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
	public void enviar(String titulo, String texto, String...destinatarios){
	}
	
	
	@Asynchronous
	public void enviarEmail(Boleto boleto, String titulo, String texto) {
		System.out.println("Envia Email boleto MailService "+boleto.getNossoNumero());
		File arquivoBoleto = geradorBoletoService.gerarBoleto(boleto);
		titulo = (titulo == null || titulo.equals(""))?comporTitulo(boleto):titulo;
		final Parametro parametro = parametroService.recuperarParametro();
		Properties props = new Properties();
		String from = parametro.getFromEmail();
		props.put("mail.smtp.auth", parametro.isAuthEmail());
		props.put("mail.smtp.starttls.enable", parametro.isUseTls());
		props.put("mail.smtp.host", parametro.getSmtp());
		props.put("mail.smtp.port", parametro.getPortaSmtp().toString());
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(parametro.getLoginEmail(), parametro.getSenhaEmail());
			}
		});
		
		try {
			UtilString utilString = new UtilString();
			if(!utilString.vazio(boleto.getContrato().getCliente().getEmail())){
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(boleto.getContrato().getCliente().getEmail()));
				message.setSubject(titulo);
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(UtilEmail.corpo
						.replace("@@TEXTO", texto)
						.replace("@@SIGLA_EMPRESA", parametro.getSiglaEmpresa())
						.replace("@@TELEFONE", parametro.getNumeroAtendimentoTelefonico()),"text/html");
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
				if(boleto.getDataNotificacao() == null){
					boletoService.atualizarNotificacao(boleto, new Date());
				}	
			}	
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}finally {
			if(arquivoBoleto != null){
				arquivoBoleto.delete();
			}
		}
		
	}

	private String comporTitulo(Boleto boleto) {
		UtilData utildata = new UtilData();
		UtilString utilString = new UtilString();
		final Parametro parametro = parametroService.recuperarParametro();
		String titulo = parametro.getSiglaEmpresa()+" - "+boleto.getContrato().getCliente().getNome()+" sua fatura do mês "+UtilData.obterMesPorMesNumerico(utilString.completaComZerosAEsquerda(String.valueOf(utildata.getMes(boleto.getDataVencimento())), 2))+" chegou!";
		return titulo;
	}
	
	@Asynchronous
	public void esqueciSenha(Cliente cliente){
		String titulo = "ITM - Esqueci a senha";
		final Parametro parametro = parametroService.recuperarParametro();
		Properties props = new Properties();
		String from = parametro.getFromEmail();
		props.put("mail.smtp.auth", parametro.isAuthEmail());
		props.put("mail.smtp.starttls.enable", parametro.isUseTls());
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
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(cliente.getEmail()));
			message.setSubject(titulo);
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(UtilEmail.corpo.replace("@@TEXTO", "Sua senha: "+cliente.getSenha())
					.replace("@@SIGLA_EMPRESA", parametro.getSiglaEmpresa())
					.replace("@@TELEFONE", parametro.getNumeroAtendimentoTelefonico()),"text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
	}

}