package br.com.trixti.itm.service.remessa;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.com.trixti.itm.dao.AbstractDAO;
import br.com.trixti.itm.dao.remessa.RemessaDAO;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.ContratoNotificacao;
import br.com.trixti.itm.entity.MeioEnvioContratoNotificacao;
import br.com.trixti.itm.entity.Remessa;
import br.com.trixti.itm.entity.TipoContratoNotificacao;
import br.com.trixti.itm.enums.StatusRemessaEnum;
import br.com.trixti.itm.infra.msg.MensagemFactory;
import br.com.trixti.itm.service.AbstractService;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.contratonotificacao.ContratoNotificacaoService;
import br.com.trixti.itm.service.mail.MailService;
import br.com.trixti.itm.service.sms.SMSService;
import br.com.trixti.itm.util.UtilData;

@Stateless
public class RemessaService extends AbstractService<Remessa>{

	private @Inject RemessaDAO remessaDAO;
	private @Inject BoletoService boletoService;
	private @Inject MailService mailService;
	private @Inject SMSService smsService;
	private @Inject MensagemFactory mensagemFactory;
	private @Inject ContratoNotificacaoService contratoNotificacaoService;
	

	@Override
	public AbstractDAO<Remessa> getDAO() {
		return remessaDAO;
	}
	
	public List<Remessa> pesquisar(Remessa remessa){
		return remessaDAO.pesquisar(remessa);
	}

	public Remessa recuperarCompleto(Integer id) {
		return remessaDAO.recuperarCompleto(id);
	}
	
	public void executarManutencao(){
		List<Remessa> listaRemessa = remessaDAO.listarNaoEnviadas();
		for(Remessa remessa:listaRemessa){
			List<Boleto> listaBoleto = boletoService.pesquisarBoletoRemessa(remessa);
			if(listaBoleto != null){
				for(Boleto boleto:listaBoleto){
					boleto.setRemessa(null);
					boletoService.alterar(boleto);
				}
			}
			excluir(remessa);
		}
	}
	
	public void notificarBoletoEmAtraso(Remessa remessa){
		UtilData utilData = new UtilData();
		List<Boleto> listaBoletoAberto = boletoService.pesquisarBoletoEmAberto(remessa);
		for (Boleto boleto : listaBoletoAberto) {
			Integer qtdDiferenca = Long.valueOf(utilData.getDiferencaDias(new Date(), boleto.getDataVencimento())).intValue();
			if(qtdDiferenca > 0){
				String texto = mensagemFactory.getMensagem("label.global.msg.notificacao.mensagemreevioatraso");
				String textoSms = mensagemFactory.getMensagem("label.global.msg.notificacao.mensagemreevioatrasosms");
				mailService.enviarEmail(boleto,"ITRIX - Aviso - Negativação", texto);
				smsService.enviarSMS(boleto,textoSms);
				contratoNotificacaoService.incluir(comporContratoNotificacao(boleto,MeioEnvioContratoNotificacao.EMAIL_E_SMS,
						TipoContratoNotificacao.SEGUNDA_VIDA, texto));
			}
		}	
	}
	
	public void notificarTodosBoleto(Remessa remessa){
		remessa = remessaDAO.recuperarCompleto(remessa.getId());
		UtilData utilData = new UtilData();
		List<Boleto> listaBoletoAberto = remessa.getBoletos();
		for (Boleto boleto : listaBoletoAberto) {
			String texto = String.format("Sua Fatura de %s esta disponivel.", utilData.getMesExtenso(utilData.getMes(boleto.getDataVencimento())));
			mailService.enviarEmail(boleto,"ITRIX - Envio de Fatura", texto);
			smsService.enviarSMS(boleto);
			contratoNotificacaoService.incluir(comporContratoNotificacao(boleto,MeioEnvioContratoNotificacao.EMAIL_E_SMS,TipoContratoNotificacao.SEGUNDA_VIDA, texto));
		}	
	}
	
	private ContratoNotificacao comporContratoNotificacao(Boleto boleto,MeioEnvioContratoNotificacao meio,TipoContratoNotificacao tipo, String texto3) {
		ContratoNotificacao contratoNotificacaoAvisoInicial = new ContratoNotificacao();
		contratoNotificacaoAvisoInicial.setContrato(boleto.getContrato());
		contratoNotificacaoAvisoInicial.setTexto(texto3);
		contratoNotificacaoAvisoInicial.setMeioEnvio(meio);
		contratoNotificacaoAvisoInicial.setTipo(tipo);
		contratoNotificacaoAvisoInicial.setDataEnvio(new Date());
		return contratoNotificacaoAvisoInicial;
	}
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void enviarRemessa(Remessa remessa){
		remessa = remessaDAO.recuperarCompleto(remessa.getId());
		remessa.setDataEnvio(new Date());
		remessa.setStatus(StatusRemessaEnum.ENVIADO);
		remessaDAO.alterar(remessa);
	}
	
	public void removerBoletoRemessa(Boleto boleto){
		Remessa remessa = recuperar(boleto.getRemessa().getId());
		remessa.setValor(remessa.getValor().subtract(boleto.getValor()));
		remessa.setQtdBoletoAberto(remessa.getQtdBoletoAberto() - 1);
		remessaDAO.alterar(remessa);
		boleto.setRemessa(null);
		boletoService.alterar(boleto);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public void excluir(Remessa entidade) {
		try{
			super.excluir(entidade);
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
}
