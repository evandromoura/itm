package br.com.trixti.itm.controller.contrato;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoLancamento;
import br.com.trixti.itm.entity.StatusBoletoEnum;
import br.com.trixti.itm.entity.StatusContrato;
import br.com.trixti.itm.entity.StatusLancamentoEnum;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
import br.com.trixti.itm.infra.financeiro.CalculaBase10;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.boleto.GeradorBoletoService;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.service.contratolancamento.ContratoLancamentoService;
import br.com.trixti.itm.service.mail.MailService;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.service.sms.SMSService;
import br.com.trixti.itm.to.ContratoTO;
import br.com.trixti.itm.util.UtilArquivo;
import br.com.trixti.itm.util.UtilData;

@ViewScoped
@ManagedBean
public class ContratoViewController extends AbstractController<Contrato> {

	private ContratoTO contratoTO;
	private @Inject ContratoService contratoService;
	private @Inject GeradorBoletoService geradorBoletoService;
	private @Inject ContratoLancamentoService contratoLancamentoService;
	private @Inject BoletoService boletoService;
	private @Inject ParametroService parametroService;
	private @Inject MailService mailService;
	private @Inject SMSService smsService;
	

	@PostConstruct
	public void init() {
		inicializar();
		getContratoTO().setAbaAtiva("dadosgerais");
		getContratoTO().setParametro(parametroService.recuperarParametro());
	}

	private void inicializar() {
		String parametro = getRequest().getParameter("parametro");
		getContratoTO().setContrato(contratoService.recuperarCompleto(new Integer(parametro)));
		getContratoTO().setDataVencimentoBoleto(null);
	}

	public void downloadBoleto(Boleto boleto) throws Exception {
		File arquivoBoleto = null;
		try {
			arquivoBoleto = geradorBoletoService.gerarBoleto(boleto);
			if (arquivoBoleto != null) {
				UtilArquivo utilArquivo = new UtilArquivo();
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				utilArquivo.convertFileToByteArrayOutputStream(arquivoBoleto, byteArrayOutputStream);
				download(byteArrayOutputStream, arquivoBoleto.getName());
			}
		} catch (Exception e) {
			getFacesContext().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}finally {
			if(arquivoBoleto != null){
				arquivoBoleto.delete();
			}		
		}	
	}
	
	public void enviarEmail(Boleto boleto){
		mailService.enviarEmail(boleto);
	}

	public void enviarSMS(Boleto boleto){
		smsService.enviarSMS(boleto);
	}
	

	public void criarContratoLancamento() {
		getContratoTO().getContratoLancamento().setContrato(getContratoTO().getContrato());
		getContratoTO().getContratoLancamento().setDataLancamento(new Date());
		getContratoTO().getContratoLancamento().setStatus(StatusLancamentoEnum.PENDENTE);
		if (getContratoTO().getContratoLancamento().isGeraBoleto()) {
			Boleto boleto = new Boleto();
			boleto.setContrato(getContratoTO().getContrato());
			boleto.setDataCriacao(new Date());
			boleto.setDataVencimento(new Date());
			boleto.setValor(getContratoTO().getContratoLancamento().getValor());
			List<BoletoLancamento> listaBoletoLancamento = new ArrayList<BoletoLancamento>();
			BoletoLancamento boletoLancamento = new BoletoLancamento();
			boletoLancamento.setBoleto(boleto);
			contratoLancamentoService.incluir(getContratoTO().getContratoLancamento());
			boletoLancamento.setContratoLancamento(getContratoTO().getContratoLancamento());
			listaBoletoLancamento.add(boletoLancamento);
			boleto.setLancamentos(listaBoletoLancamento);
			boleto.setStatus(StatusBoletoEnum.ABERTO);
			BigInteger nossoNumero = boletoService.recuperarNossoNumero();
			boleto.setNumeroDocumento(nossoNumero.toString());
			boleto.setNossoNumero(nossoNumero.toString());
			boleto.setDigitoNossoNumero(String.valueOf(new CalculaBase10().getMod10(nossoNumero.toString())));
			boleto.setNossoNumeroCompleto(boleto.getNossoNumero()+boleto.getDigitoNossoNumero());
			boletoService.incluir(boleto);
		}else{
			contratoLancamentoService.incluir(getContratoTO().getContratoLancamento());
		}
		getContratoTO().setContratoLancamento(null);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incluido com Sucesso",
				"O Registro foi alterado na base"));
		inicializar();
	}

	public void gerarBoletoListaContratoLancamento() {
			Boleto boleto = new Boleto();
			boleto.setContrato(getContratoTO().getContrato());
			Date dataAtual = new Date();
			boleto.setDataCriacao(dataAtual);
			boleto.setDataVencimento(getContratoTO().getDataVencimentoBoleto());
			boleto.setLancamentos(new ArrayList<BoletoLancamento>());
			BigInteger nossoNumero = boletoService.recuperarNossoNumero();
			boleto.setNumeroDocumento(nossoNumero.toString());
			boleto.setNossoNumero(nossoNumero.toString());
			boleto.setDigitoNossoNumero(String.valueOf(new CalculaBase10().getMod10(nossoNumero.toString())));
			boleto.setNossoNumeroCompleto(boleto.getNossoNumero()+boleto.getDigitoNossoNumero());
			
			BigDecimal totalBoleto = new BigDecimal(0);
			for (ContratoLancamento contratoLancamento : getContratoTO().getContrato().getLancamentos()) {
				if (contratoLancamento.isSelecionado()) {
					BoletoLancamento boletoLancamento = new BoletoLancamento();
					boletoLancamento.setBoleto(boleto);
					boletoLancamento.setContratoLancamento(contratoLancamento);
					boleto.getLancamentos().add(boletoLancamento);
					totalBoleto = contratoLancamento.getTipoLancamento().equals(TipoLancamentoEnum.DEBITO)
							? totalBoleto.add(contratoLancamento.getValor())
							: totalBoleto.subtract(contratoLancamento.getValor());

				}
			}
			if (boleto.getLancamentos().size() > 0) {
				boleto.setValor(totalBoleto);
				boleto.setStatus(StatusBoletoEnum.ABERTO);
				boletoService.incluir(boleto);
				getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incluido com Sucesso",
						"O Registro foi alterado na base"));
			}
			inicializar();
		

	}

	public void excluirBoleto(Boleto boleto) {
		boletoService.excluir(boleto);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incluido com Sucesso",
				"O Registro foi alterado na base"));
		inicializar();
	}

	public void excluirContratoLancamento(ContratoLancamento contratoLancamento) {
		contratoLancamentoService.excluir(contratoLancamento);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso",
				"O Registro foi alterado na base"));
		inicializar();
	}

	public boolean isExibeBotaoGerarBoleto() {
		if (getContratoTO().getContrato().getLancamentos() != null) {
			for (ContratoLancamento lancamento : getContratoTO().getContrato().getLancamentos()) {
				if (lancamento.isSelecionado()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void desbloquearContratoTemporariamente(){
		UtilData utilData = new UtilData();
		getContratoTO().getContrato().setStatus(StatusContrato.ATIVO);
		getContratoTO().getContrato().setDataParaBloqueio(utilData.adicionaDias(new Date(), getContratoTO().getParametro().getQtdDiasDesbloqueioTemporario()));
		contratoService.alterar(getContratoTO().getContrato());
	}
	

	public ContratoTO getContratoTO() {
		if (contratoTO == null) {
			contratoTO = new ContratoTO();
		}
		return contratoTO;
	}

	public void setContratoTO(ContratoTO contratoTO) {
		this.contratoTO = contratoTO;
	}

}
