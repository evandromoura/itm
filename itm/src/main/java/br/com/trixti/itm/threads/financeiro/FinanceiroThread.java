package br.com.trixti.itm.threads.financeiro;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.jrimum.bopepo.BancosSuportados;

import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoLancamento;
import br.com.trixti.itm.entity.ContratoNotificacao;
import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.entity.MeioEnvioContratoNotificacao;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.entity.Retorno;
import br.com.trixti.itm.entity.StatusBoletoEnum;
import br.com.trixti.itm.entity.StatusContrato;
import br.com.trixti.itm.entity.StatusLancamentoEnum;
import br.com.trixti.itm.entity.TipoContratoNotificacao;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
import br.com.trixti.itm.infra.financeiro.CalculaBase10;
import br.com.trixti.itm.infra.financeiro.IntegracaoFinanceiraItau;
import br.com.trixti.itm.infra.msg.MensagemFactory;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.boleto.GeradorBoletoService;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.service.contratolancamento.ContratoLancamentoService;
import br.com.trixti.itm.service.contratonotificacao.ContratoNotificacaoService;
import br.com.trixti.itm.service.contratoproduto.ContratoProdutoService;
import br.com.trixti.itm.service.freeradius.FreeRadiusService;
import br.com.trixti.itm.service.mail.MailService;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.service.retorno.RetornoService;
import br.com.trixti.itm.service.sms.SMSService;
import br.com.trixti.itm.util.UtilData;

@Named
@Singleton
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FinanceiroThread {

	private @Inject ClienteService clienteService;
	private @Inject ContratoProdutoService contratoProdutoService;
	private @Inject ContratoLancamentoService contratoLancamentoService;
	private @Inject ContratoService contratoService;
	private @Inject BoletoService boletoService;
	private @Inject FreeRadiusService freeRadiusService;
	private @Inject ParametroService parametroService;
	private @Inject IntegracaoFinanceiraItau integracaoFinanceiraItau;
	private @Inject RetornoService retornoService;
	private Parametro parametro;
	private @Resource TimerService sessionContext;
	private @Inject MailService mailService;
	private @Inject SMSService smsService;
	private @Inject GeradorBoletoService geradorBoletoService;
	private @Inject MensagemFactory mensagemFactory;
	private @Inject ContratoNotificacaoService contratoNotificacaoService;

	@Schedule(info = "Gerar-Boleto", minute = "*", hour = "*", persistent = false)
	public void processarBoleto() {
		parametro = parametroService.recuperarParametro();
		List<Cliente> clientes = clienteService.listarAtivo();
		for (Cliente cliente : clientes) {
			BigDecimal valor = BigDecimal.ZERO;
			List<BoletoLancamento> lancamentosBoleto = new ArrayList<BoletoLancamento>();
			for (Contrato contrato : cliente.getContratos()) {
				gerarBoleto(valor, lancamentosBoleto, contrato);
			}
		}
	}

	// @Schedule(info="Bloquear-Contrato", hour = "12", persistent = false)
	@Schedule(info = "Bloquear-Contrato", minute = "*", hour = "*", persistent = false)
	public void bloquearContrato() {
		parametro = parametroService.recuperarParametro();
		List<Cliente> clientes = clienteService.listarAtivo();
		for (Cliente cliente : clientes) {
			for (Contrato contrato : cliente.getContratos()) {
				verificarBloqueioContrato(contrato);
			}
		}
	}

	@Schedule(info = "Desbloquear-Contrato", minute = "*", hour = "*", persistent = false)
	public void desbloquearContrato() {
		parametro = parametroService.recuperarParametro();
		List<Cliente> clientes = clienteService.listarAtivo();
		for (Cliente cliente : clientes) {
			for (Contrato contrato : cliente.getContratos()) {
				verificarDesbloquearContrato(contrato);
			}
		}
	}

	@Schedule(info = "Processar-Remessa", minute = "*/1", hour = "*", persistent = false)
	public void processarRemessa() {
		List<Boleto> listaBoleto = boletoService.pesquisarBoletoSemRemessa();
		Map<String, List<Boleto>> mapaBoletoBanco = new HashMap<String, List<Boleto>>();
		for (Boleto boleto : listaBoleto) {
			if (boleto.getStatus().equals(StatusBoletoEnum.ABERTO)) {
				if (mapaBoletoBanco.get(boleto.getContrato().getContaCorrente().getBanco()) == null) {
					mapaBoletoBanco.put(boleto.getContrato().getContaCorrente().getBanco(), new ArrayList<Boleto>());
				}
				mapaBoletoBanco.get(boleto.getContrato().getContaCorrente().getBanco()).add(boleto);
			}
		}
		for (String banco : mapaBoletoBanco.keySet()) {
			if (banco.equals(BancosSuportados.BANCO_ITAU.name())) {
				integracaoFinanceiraItau.gerarRemessa(mapaBoletoBanco.get(banco));
			}
		}
	}

	@Schedule(info = "Processar-Retorno", minute = "*", hour = "*", persistent = false)
	public void processarRetorno() {
		List<Retorno> listaRetorno = retornoService.listarPendentes();
		Map<String, List<Retorno>> mapaRetorno = new HashMap<String, List<Retorno>>();
		for (Retorno retorno : listaRetorno) {
			if (retorno.getBanco().equals(BancosSuportados.BANCO_ITAU.name())) {
				if (mapaRetorno.get(retorno.getBanco()) == null) {
					mapaRetorno.put(retorno.getBanco(), new ArrayList<Retorno>());
				}
				mapaRetorno.get(retorno.getBanco()).add(retorno);
			}
		}
		for (String banco : mapaRetorno.keySet()) {
			if (BancosSuportados.BANCO_ITAU.name().equals(banco)) {
				integracaoFinanceiraItau.processarRetorno(mapaRetorno.get(banco));
			}
		}
	}

	@Schedule(info = "Enviar-Notificacoes", minute = "*/1", hour = "*", persistent = false)
	public void processarNotificacao() {
		List<Boleto> listaBoleto = boletoService.pesquisarBoletoNaoNotificado();
		String texto = "Sua fatura está disponível"; 
		for (Boleto boleto : listaBoleto) {
			mailService.enviarEmail(boleto,null,texto);
			smsService.enviarSMS(boleto);
			contratoNotificacaoService.incluir(comporContratoNotificacao(boleto, MeioEnvioContratoNotificacao.EMAIL_E_SMS, TipoContratoNotificacao.ENVIO_BOLETO, texto));
		}
	}

	@Schedule(info = "Enviar-Notificacoes-Inadimplencia", hour = "12", persistent = false)
	public void processarNotificacaoInadimplencia() {
		UtilData utilData = new UtilData();
		List<Boleto> listaBoletoAberto = boletoService.pesquisarBoletoEmAberto();
		for (Boleto boleto : listaBoletoAberto) {
			Integer qtdDiferenca = Long.valueOf(utilData.getDiferencaDias(new Date(), boleto.getDataVencimento())).intValue();
			boleto.setDataVencimento(new Date());
			if(qtdDiferenca >= 3 && qtdDiferenca < 5){
				ContratoNotificacao contratoNotificacaoRetornoInicial = 
					contratoNotificacaoService.recuperarPorContratoDataTipo(boleto.getContrato(), TipoContratoNotificacao.AVISO_ATRASO_INICIAL, new Date());
				if (contratoNotificacaoRetornoInicial == null) {
					String texto = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentoatrasado");
					String textoSms = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentoatrasadosms");
					mailService.enviarEmail(boleto,"ITRIX - Aviso - Atraso de Pagamento", texto);
					smsService.enviarSMS(boleto, textoSms);
					contratoNotificacaoService.incluir(comporContratoNotificacao(boleto,MeioEnvioContratoNotificacao.EMAIL_E_SMS, TipoContratoNotificacao.AVISO_ATRASO_INICIAL, texto));
				}
			}else if(qtdDiferenca >=5 && qtdDiferenca < 10){	
				ContratoNotificacao contratoNotificacaoRetornoAntesBloqueio = 
					contratoNotificacaoService.recuperarPorContratoDataTipo(boleto.getContrato(),TipoContratoNotificacao.AVISO_ANTES_BLOQUEIO_PRIMEIRO, new Date());
				if (contratoNotificacaoRetornoAntesBloqueio == null) {
					String texto = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentoantesbloqueio");
					String textoSms = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentoantesbloqueiosms");
					mailService.enviarEmail(boleto,"ITRIX - Aviso - Atraso de Pagamento", texto);
					smsService.enviarSMS(boleto,textoSms);
					contratoNotificacaoService.incluir(comporContratoNotificacao(boleto,MeioEnvioContratoNotificacao.EMAIL_E_SMS, TipoContratoNotificacao.AVISO_ANTES_BLOQUEIO_PRIMEIRO, texto));
				}
			}else if(qtdDiferenca >= 10 && qtdDiferenca < 15){
				ContratoNotificacao contratoNotificacaoRetornoAntesDezDias = 
						contratoNotificacaoService.recuperarPorContratoDataTipo(boleto.getContrato(),TipoContratoNotificacao.AVISO_ANTES_BLOQUEIO_SEGUNDO, new Date());
				if (contratoNotificacaoRetornoAntesDezDias == null) {
					String texto = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentoantesbloqueio");
					String textoSms = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentoantesbloqueiosms");
					mailService.enviarEmail(boleto,"ITRIX - Aviso - Atraso de Pagamento", texto);
					smsService.enviarSMS(boleto,textoSms);
					contratoNotificacaoService.incluir(comporContratoNotificacao(boleto,MeioEnvioContratoNotificacao.EMAIL_E_SMS, TipoContratoNotificacao.AVISO_ANTES_BLOQUEIO_SEGUNDO, texto));
				}
			}else if(qtdDiferenca >= 15 && qtdDiferenca < 45){
				ContratoNotificacao contratoNotificacaoRetornoAntesDezDias = 
						contratoNotificacaoService.recuperarPorContratoDataTipo(boleto.getContrato(),TipoContratoNotificacao.AVISO_ANTES_BLOQUEIO_TERCEIRO, new Date());
				if (contratoNotificacaoRetornoAntesDezDias == null) {
					String texto = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentoantesbloqueio");
					String textoSms = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentoantesbloqueiosms");
					mailService.enviarEmail(boleto,"ITRIX - Aviso - Atraso de Pagamento", texto);
					smsService.enviarSMS(boleto,textoSms);
					contratoNotificacaoService.incluir(comporContratoNotificacao(boleto,MeioEnvioContratoNotificacao.EMAIL_E_SMS,
							TipoContratoNotificacao.AVISO_ANTES_BLOQUEIO_TERCEIRO, texto));
				}
			}else if(qtdDiferenca > 45){
				ContratoNotificacao contratoNotificacaoRetornoAntesDezDias = 
					contratoNotificacaoService.recuperarPorContratoDataTipo(boleto.getContrato(),TipoContratoNotificacao.AVISO_NEGATIVACAO, new Date());
				if (contratoNotificacaoRetornoAntesDezDias == null) {
					String texto = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentonegativacao");
					String textoSms = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentonegativacaosms");
					mailService.enviarEmail(boleto,"ITRIX - Aviso - Negativação", texto);
					smsService.enviarSMS(boleto,textoSms);
					contratoNotificacaoService.incluir(comporContratoNotificacao(boleto,MeioEnvioContratoNotificacao.EMAIL_E_SMS,
							TipoContratoNotificacao.AVISO_NEGATIVACAO, texto));
				}
			}
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

	private void verificarBloqueioContrato(Contrato contrato) {
		UtilData utilData = new UtilData();
		List<Boleto> boletos = boletoService.pesquisarBoletoEmAbertoContrato(contrato);
		for (Boleto boleto : boletos) {
			if (utilData.getDiferencaDias(new Date(), boleto.getDataVencimento()) > parametro.getQtdDiasBloqueio()
					&& (contrato.getDataParaBloqueio() == null
							|| utilData.data1MaiorIgualData2(new Date(), contrato.getDataParaBloqueio()))) {
				if (contrato.getStatus().equals(StatusContrato.ATIVO)) {
					contrato.setStatus(StatusContrato.BLOQUEADO);
					contratoService.alterar(contrato);
					freeRadiusService.bloquearContrato(contrato);
				}
			}
		}
	}

	private void verificarDesbloquearContrato(Contrato contrato) {
		UtilData utilData = new UtilData();
		List<Boleto> boletos = boletoService.pesquisarBoletoEmAbertoContrato(contrato);
		boolean desbloquear = true;
		for (Boleto boleto : boletos) {
			if (utilData.getDiferencaDias(new Date(), boleto.getDataVencimento()) > parametro.getQtdDiasBloqueio()
					&& (contrato.getDataParaBloqueio() == null
							|| utilData.data1MaiorIgualData2(new Date(), contrato.getDataParaBloqueio()))) {
				desbloquear = false;
			}
		}
		if (desbloquear && contrato.getStatus().equals(StatusContrato.BLOQUEADO)) {
			contrato.setStatus(StatusContrato.ATIVO);
			// contrato.setDataParaBloqueio(null);
			contratoService.alterar(contrato);
			freeRadiusService.desbloquearContrato(contrato);
		}

	}

	private void gerarBoleto(BigDecimal valor, List<BoletoLancamento> lancamentosBoleto, Contrato contrato) {
		if (contrato.isGeraBoleto()) {
			List<ContratoLancamento> lancamentosEmAberto = contratoLancamentoService
					.pesquisarLancamentoAberto(contrato);
			List<ContratoProduto> produtos = contratoProdutoService.pesquisarVigentePorContrato(contrato);
			Boleto boleto = new Boleto();
			UtilData utilData = new UtilData();
			Date dataVencimento = utilData.ajustaData(utilData.adicionarMeses(new Date(), 1),
					contrato.getDiaMesVencimento(), 23, 59, 59);
			Boleto boletoJaCriado = boletoService.recuperarBoletoContrato(contrato, dataVencimento);
			if (boletoJaCriado == null) {
				for (ContratoLancamento lancamentoAberto : lancamentosEmAberto) {
					valor = lancamentoAberto.getTipoLancamento().equals(TipoLancamentoEnum.DEBITO)
							? valor.add(lancamentoAberto.getValor()) : valor.subtract(lancamentoAberto.getValor());
					lancamentosBoleto.add(new BoletoLancamento(boleto, lancamentoAberto));
				}

				for (ContratoProduto produto : produtos) {
					ContratoLancamento contratoLancamento = new ContratoLancamento();
					contratoLancamento.setContrato(contrato);
					contratoLancamento.setDataLancamento(new Date());
					contratoLancamento.setDescricao(produto.getProduto().getNome());
					contratoLancamento.setStatus(StatusLancamentoEnum.PENDENTE);
					contratoLancamento.setTipoLancamento(TipoLancamentoEnum.DEBITO);
					contratoLancamento.setValor(produto.getValor());
					contratoLancamentoService.incluir(contratoLancamento);

					BoletoLancamento boletoLancamento = new BoletoLancamento();
					boletoLancamento.setBoleto(boleto);
					boletoLancamento.setContratoLancamento(contratoLancamento);

					lancamentosBoleto.add(boletoLancamento);
					valor = valor.add(produto.getValor());
				}
				if (valor.intValue() > 0) {
					boleto.setContrato(contrato);
					boleto.setLancamentos(lancamentosBoleto);
					boleto.setDataCriacao(new Date());
					boleto.setStatus(StatusBoletoEnum.ABERTO);
					boleto.setValor(valor);
					boleto.setDataVencimento(dataVencimento);
					comporNossoNumero(boleto);
					boletoService.incluir(boleto);
				} else {
					boleto.setContrato(contrato);
					boleto.setLancamentos(lancamentosBoleto);
					boleto.setDataCriacao(new Date());
					boleto.setStatus(StatusBoletoEnum.PAGO);
					boleto.setValor(BigDecimal.ZERO);
					boleto.setDataVencimento(dataVencimento);
					if (boleto.getLancamentos() != null && !boleto.getLancamentos().isEmpty()) {
						comporNossoNumero(boleto);
						boletoService.incluir(boleto);
					}
					// TODO: getResources
					if (valor.abs().intValue() > 0) {
						contratoLancamentoService.incluir(new ContratoLancamento("Credito em Conta", contrato,
								valor.abs(), TipoLancamentoEnum.CREDITO, new Date(), StatusLancamentoEnum.PENDENTE));
					}

				}
			}
		}
	}

	private void comporNossoNumero(Boleto boleto) {
		BigInteger nossoNumero = boletoService.recuperarNossoNumero();
		System.out.println("Gerou Nosso Numero = "+nossoNumero);
		boleto.setNumeroDocumento(nossoNumero.toString());
		boleto.setNossoNumero(nossoNumero.toString());
		boleto.setDigitoNossoNumero(String.valueOf(new CalculaBase10().getMod10(nossoNumero.toString())));
		boleto.setNossoNumeroCompleto(boleto.getNossoNumero() + boleto.getDigitoNossoNumero());
	}

	public List<Timer> listarThreads() {
		return new ArrayList<Timer>(sessionContext.getTimers());
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	public void parar(String info) throws Throwable {
		try {
			List<Timer> timers = new ArrayList<Timer>(sessionContext.getTimers());
			for (Timer timer : timers) {
				if (timer.getInfo().equals(info)) {
					timer.cancel();
				}
			}
		} catch (RuntimeException e) {

			System.out.println("PAROU A THREAD");
		}
	}

	public static void main(String[] args) {
		String valor = "10213699339000170065400194703    0000N/A                      000007180000000000000109                     I01718       05071700000000019903410065408N010617053900000000000000507170000000000000000000000000000000000000000200098328204134Naldo Gomes                             Scia QD 15                              Setor Indust71250050Brasilia       DF                                  00000000 000002";
		System.out.println(valor.substring(1, 2));
	}
}