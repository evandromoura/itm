package br.com.trixti.itm.threads.financeiro;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.jrimum.bopepo.BancosSuportados;

import br.com.trixti.itm.entity.ArquivoSici;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoLancamento;
import br.com.trixti.itm.entity.ContratoNotificacao;
import br.com.trixti.itm.entity.ContratoProduto;
import br.com.trixti.itm.entity.MeioEnvioContratoNotificacao;
import br.com.trixti.itm.entity.Nfe;
import br.com.trixti.itm.entity.Parametro;
import br.com.trixti.itm.entity.Remessa;
import br.com.trixti.itm.entity.Retorno;
import br.com.trixti.itm.entity.StatusBoletoEnum;
import br.com.trixti.itm.entity.StatusContrato;
import br.com.trixti.itm.entity.StatusLancamentoEnum;
import br.com.trixti.itm.entity.StatusRetorno;
import br.com.trixti.itm.entity.TipoContratoNotificacao;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
import br.com.trixti.itm.enums.StatusRemessaEnum;
import br.com.trixti.itm.infra.financeiro.CalculaBase10;
import br.com.trixti.itm.infra.financeiro.IntegracaoFinanceiraItau;
import br.com.trixti.itm.infra.msg.MensagemFactory;
import br.com.trixti.itm.service.arquivosici.ArquivoSiciService;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.service.contratolancamento.ContratoLancamentoService;
import br.com.trixti.itm.service.contratonotificacao.ContratoNotificacaoService;
import br.com.trixti.itm.service.contratoproduto.ContratoProdutoService;
import br.com.trixti.itm.service.freeradius.FreeRadiusService;
import br.com.trixti.itm.service.mail.MailService;
import br.com.trixti.itm.service.nfe.NfeService;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.service.remessa.RemessaService;
import br.com.trixti.itm.service.retorno.RetornoService;
import br.com.trixti.itm.service.sms.SMSService;
import br.com.trixti.itm.service.uploadarquivo.UploadArquivoService;
import br.com.trixti.itm.util.Base64Utils;
import br.com.trixti.itm.util.UtilArquivo;
import br.com.trixti.itm.util.UtilData;
import br.com.trixti.itm.util.UtilString;

@Named
@Stateless
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
	private @Inject MensagemFactory mensagemFactory;
	private @Inject ContratoNotificacaoService contratoNotificacaoService;
	private @Inject RemessaService remessaService;
	private @Inject ArquivoSiciService arquivoSiciService;
	private @Inject NfeService nfeService;
	private @Inject UploadArquivoService uploadArquivoService;
	
	private boolean ativo = false;
	private boolean integracao = false;

	@Schedule(info = "Gerar-Boleto", minute = "*", hour = "*", persistent = false)
	public void processarBoleto() {
		if(ativo){
			parametro = parametroService.recuperarParametro();
			List<Cliente> clientes = clienteService.listarAtivo();
			for (Cliente cliente : clientes) {
				BigDecimal valor = BigDecimal.ZERO;
				List<BoletoLancamento> lancamentosBoleto = new ArrayList<BoletoLancamento>();
				for (Contrato contrato : cliente.getContratos()) {
					if(contrato.getStatus().equals(StatusContrato.ATIVO) || contrato.getStatus().equals(StatusContrato.SUSPENSO)){
						gerarBoleto(valor, lancamentosBoleto, contrato);
					}	
				}
			}
		}	
	}

	@Schedule(info = "Bloquear-Contrato", minute = "*", hour = "*", persistent = false)
	public void bloquearContrato() {
		if(ativo){
			parametro = parametroService.recuperarParametro();
			List<Cliente> clientes = clienteService.listarAtivo();
			for (Cliente cliente : clientes) {
				for (Contrato contrato : cliente.getContratos()) {
					verificarBloqueioContrato(contrato);
				}
			}
		}	
	}

	@Schedule(info = "Desbloquear-Contrato", minute = "*", hour = "*", persistent = false)
	public void desbloquearContrato() {
		if(ativo){
			parametro = parametroService.recuperarParametro();
			List<Cliente> clientes = clienteService.listarAtivo();
			for (Cliente cliente : clientes) {
				for (Contrato contrato : cliente.getContratos()) {
					verificarDesbloquearContrato(contrato);
				}
			}
		}	
	}

	@Schedule(info = "Gerar-Remessa", minute = "*/3", hour = "*", persistent = false)
	public void processarRemessa() {
		if(ativo){
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
	}
	
	@Schedule(info = "Enviar-Remessa", minute = "*/1", hour = "*", persistent = false)
	public void processarEnvioRemessa() {
		if(ativo){
			UtilString utilString = new UtilString();
			UtilArquivo utilArquivo = new UtilArquivo();
			try{
				List<Remessa> listaRemessasNaoEnviadas = remessaService.listarNaoEnviadas();
				for(Remessa remessa:listaRemessasNaoEnviadas){
					Remessa remessaCompleta = remessaService.recuperarCompleto(remessa.getId());
					String nomeArquivo = "CB"+utilString.completaComZerosAEsquerda(remessaCompleta.getId().toString(), 6)+".rem";
					File arquivo = utilArquivo.getFileFromBytes(Base64Utils.base64Decode(remessaCompleta.getArquivo()), nomeArquivo);
					String diretorio = System.getProperty("user.home")+"/itau/Edi7WebCli/ITRIXIN-001-P/enviar";
					arquivo.renameTo(new File(diretorio+"/"+nomeArquivo));
					remessaCompleta.setStatus(StatusRemessaEnum.A_ENVIAR);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}	
	}
	
	
	@Schedule(info = "Confirmar-Envio-Remessa", minute = "*/1", hour = "*", persistent = false)
	public void processarConfirmacaoEnvioRemessa() {
		if(ativo){
			UtilString utilString = new UtilString();
			try{
				List<Remessa> listaRemessasNaoEnviadas = remessaService.listarAEnviar();
				for(Remessa remessa:listaRemessasNaoEnviadas){
					String diretorio = System.getProperty("user.home")+"/itau/Edi7WebCli/ITRIXIN-001-P/enviados";
					File file = new File(diretorio);
					File[] arquivos = file.listFiles();
					if(arquivos != null){
						GOTO:for (File fileTmp : arquivos) {
							if(fileTmp.getName().equals("CB"+utilString.completaComZerosAEsquerda(remessa.getId().toString(), 6)+".rem")){
								remessa.setDataEnvio(new Date());
								remessa.setStatus(StatusRemessaEnum.ENVIADO);
								remessaService.alterar(remessa);
								break GOTO;
							}
						}
					}
				}	
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}	
	}
	

	@Schedule(info = "Processar-Retorno", minute = "*", hour = "*", persistent = false)
	public void processarRetorno() {
		if(ativo){
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
	}

	@Schedule(info = "Enviar-Notificacoes", minute = "*/1", hour = "*", persistent = false)
	public void processarNotificacao() {
		if(ativo){
			List<Boleto> listaBoleto = boletoService.pesquisarBoletoNaoNotificado();
			 UtilData utilData = new UtilData();
			for (Boleto boleto : listaBoleto) {
				String texto = String.format("Sua Fatura de %s esta disponivel.", utilData.getMesExtenso(utilData.getMes(boleto.getDataVencimento())));
				if(boleto.getDataNotificacao() == null){
					if(boleto.getContrato().getCliente().getEmail() != null && !boleto.getContrato().getCliente().getEmail().equals("")){
						System.out.println("Financeiro Thread ProcessarNotificacao "+boleto.getNossoNumero());
						mailService.enviarEmail(boleto,null,texto);
						contratoNotificacaoService.incluir(comporContratoNotificacao(boleto, MeioEnvioContratoNotificacao.EMAIL, TipoContratoNotificacao.ENVIO_BOLETO, texto));
					}
				}
				if(boleto.getDataSms() == null){
					smsService.enviarSMS(boleto);
					contratoNotificacaoService.incluir(comporContratoNotificacao(boleto, MeioEnvioContratoNotificacao.SMS, TipoContratoNotificacao.ENVIO_BOLETO, texto));
				}
			}
		}	
	}
	
	

	@Schedule(info = "Enviar-Notificacoes-Inadimplencia", hour = "12", persistent = false)
	public void processarNotificacaoInadimplencia() {
		if(ativo){
			UtilData utilData = new UtilData();
			List<Boleto> listaBoletoAberto = boletoService.pesquisarBoletoEmAberto();
			parametro = parametroService.recuperarParametro();
			for (Boleto boleto : listaBoletoAberto) {
				Integer qtdDiferenca = Long.valueOf(utilData.getDiferencaDias(new Date(), boleto.getDataVencimento())).intValue();
				boleto.setDataVencimento(new Date());
				if(qtdDiferenca >= 3 && qtdDiferenca < 5){
					ContratoNotificacao contratoNotificacaoRetornoInicial = 
						contratoNotificacaoService.recuperarPorContratoDataTipo(boleto.getContrato(), TipoContratoNotificacao.AVISO_ATRASO_INICIAL, new Date());
					if (contratoNotificacaoRetornoInicial == null) {
						String texto = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentoatrasado");
						String textoSms = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentoatrasadosms");
						mailService.enviarEmail(boleto,parametro.getSiglaEmpresa()+" - "+boleto.getContrato().getCliente().getNome()+" Aviso - Atraso de Pagamento", texto);
						smsService.enviarSMS(boleto, textoSms);
						contratoNotificacaoService.incluir(comporContratoNotificacao(boleto,MeioEnvioContratoNotificacao.EMAIL_E_SMS, TipoContratoNotificacao.AVISO_ATRASO_INICIAL, texto));
					}
				}else if(qtdDiferenca >=5 && qtdDiferenca < 10){	
					ContratoNotificacao contratoNotificacaoRetornoAntesBloqueio = 
						contratoNotificacaoService.recuperarPorContratoDataTipo(boleto.getContrato(),TipoContratoNotificacao.AVISO_ANTES_BLOQUEIO_PRIMEIRO, new Date());
					if (contratoNotificacaoRetornoAntesBloqueio == null) {
						String texto = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentoantesbloqueio");
						String textoSms = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentoantesbloqueiosms");
						mailService.enviarEmail(boleto,parametro.getSiglaEmpresa()+" - "+boleto.getContrato().getCliente().getNome()+" - Aviso - Atraso de Pagamento", texto);
						smsService.enviarSMS(boleto,textoSms);
						contratoNotificacaoService.incluir(comporContratoNotificacao(boleto,MeioEnvioContratoNotificacao.EMAIL_E_SMS, TipoContratoNotificacao.AVISO_ANTES_BLOQUEIO_PRIMEIRO, texto));
					}
				}else if(qtdDiferenca >= 10 && qtdDiferenca < 15){
					ContratoNotificacao contratoNotificacaoRetornoAntesDezDias = 
							contratoNotificacaoService.recuperarPorContratoDataTipo(boleto.getContrato(),TipoContratoNotificacao.AVISO_ANTES_BLOQUEIO_SEGUNDO, new Date());
					if (contratoNotificacaoRetornoAntesDezDias == null) {
						String texto = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentoantesbloqueio");
						String textoSms = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentoantesbloqueiosms");
						mailService.enviarEmail(boleto,parametro.getSiglaEmpresa()+" - "+boleto.getContrato().getCliente().getNome()+" - Aviso - Atraso de Pagamento", texto);
						smsService.enviarSMS(boleto,textoSms);
						contratoNotificacaoService.incluir(comporContratoNotificacao(boleto,MeioEnvioContratoNotificacao.EMAIL_E_SMS, TipoContratoNotificacao.AVISO_ANTES_BLOQUEIO_SEGUNDO, texto));
					}
				}else if(qtdDiferenca >= 15 && qtdDiferenca < 45){
					ContratoNotificacao contratoNotificacaoRetornoAntesDezDias = 
							contratoNotificacaoService.recuperarPorContratoDataTipo(boleto.getContrato(),TipoContratoNotificacao.AVISO_ANTES_BLOQUEIO_TERCEIRO, new Date());
					if (contratoNotificacaoRetornoAntesDezDias == null) {
						String texto = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentoantesbloqueio");
						String textoSms = mensagemFactory.getMensagem("label.global.msg.notificacao.pagamentoantesbloqueiosms");
						mailService.enviarEmail(boleto,parametro.getSiglaEmpresa()+" - "+boleto.getContrato().getCliente().getNome()+" - Aviso - Atraso de Pagamento", texto);
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
						mailService.enviarEmail(boleto,parametro.getSiglaEmpresa()+" - "+boleto.getContrato().getCliente().getNome()+" - Aviso - Negativação", texto);
						smsService.enviarSMS(boleto,textoSms);
						contratoNotificacaoService.incluir(comporContratoNotificacao(boleto,MeioEnvioContratoNotificacao.EMAIL_E_SMS,
								TipoContratoNotificacao.AVISO_NEGATIVACAO, texto));
					}
				}
			}
		}	
	}
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Schedule(info = "Processar-Agent-Integracao-Financeira", minute = "*/2", hour = "*", persistent = false)
	public void processarIntegracaoFinanceira() {
		if(integracao){
			new IntegracaoItauThread().start(); 
		}	
	}
	
	
	@Schedule(info = "Processar-Arquivos-Recebidos", minute = "*/1", hour = "*", persistent = false)
	public void processarArquivosRecebidos() {
		if(ativo){
			try{
				String diretorio = System.getProperty("user.home")+"/itau/Edi7WebCli/ITRIXIN-001-P/recepcao";
				UtilArquivo utilArquivo = new UtilArquivo();
				File file = new File(diretorio);
				File[] arquivos = file.listFiles();
				if(arquivos != null){
					for (File fileTmp : arquivos) {
					     Retorno retorno = new Retorno();
					     retorno.setDataCriacao(new Date());
					     retorno.setArquivo(Base64Utils.base64Encode(utilArquivo.getBytesFromFile(fileTmp)));
					     retorno.setNomeArquivo(fileTmp.getName());
					     retorno.setStatus(StatusRetorno.PENDENTE);
					     retorno.setBanco(BancosSuportados.BANCO_ITAU.name());
					     retornoService.incluir(retorno);
					     fileTmp.renameTo(new File(System.getProperty("user.home")+"/itau/Edi7WebCli/ITRIXIN-001-P/recebidos/"+fileTmp.getName()));
					 }
				}	
			}catch(Exception e){
				e.printStackTrace();
			}	
		}		 
	}
	
	@Schedule(info = "Gerar-Arquivo-SICI", minute = "*/10", hour = "*", persistent = false)
	public void gerarArquivoSici() {
		if(ativo){
			Date data= new Date();
			UtilData utilData  = new UtilData();
			ArquivoSici arquivoSiciRetorno = arquivoSiciService.recuperarPorMesAno(utilData.getMesCorrente(data), String.valueOf(utilData.getAno(data)));
			if(arquivoSiciRetorno == null){
				ArquivoSici arquivoSici = new ArquivoSici();
				arquivoSici.setData(data);
				arquivoSici.setNomeArquivo("SICI_"+utilData.getMesCorrente(data)+"_"+utilData.getAno(data)+".xml");
				arquivoSici.setXml(uploadArquivoService.gerarXml(data));
				arquivoSici.setMes(utilData.getMesCorrente(data));
				arquivoSici.setAno(String.valueOf(utilData.getAno(data)));
				arquivoSiciService.incluir(arquivoSici);
			}	
		}		
	}
	
	
	@Schedule(info = "Gerar-Arquivo-NFE", minute = "*/1", hour = "*", persistent = false)
	public void gerarNfe() {
		if(ativo){
			Date data= new Date();
			UtilData utilData  = new UtilData();
			Nfe nfeRetorno = nfeService.recuperarPorMesAno(utilData.getMesCorrente(data), String.valueOf(utilData.getAno(data)));
			if(nfeRetorno == null){
				Nfe nfe = new Nfe();
				nfe.setData(data);
				nfe.setNome("Nfe_"+utilData.getMesCorrente(data)+"_"+utilData.getAno(data)+".xml");
				nfe.setMes(utilData.getMesCorrente(data));
				nfe.setAno(String.valueOf(utilData.getAno(data)));
				List<Boleto> boletos = boletoService.listarBoletoEmAtraso();
 				nfe.setArquivos(nfeService.gerarNfeArquivo(boletos, nfe));
				nfeService.incluir(nfe);
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
			if (utilData.getDiferencaDias(new Date(), boleto.getDataVencimento()) > parametro.getQtdDiasAviso() 
					&& utilData.getDiferencaDias(new Date(), boleto.getDataVencimento()) < parametro.getQtdDiasBloqueio()
					&& (contrato.getDataParaBloqueio() == null || utilData.data1MaiorIgualData2(new Date(), contrato.getDataParaBloqueio()))) {
				
				if (contrato.getStatus().equals(StatusContrato.ATIVO)) {
					contrato.setStatus(StatusContrato.SUSPENSO);
					contratoService.alterar(contrato);
					freeRadiusService.suspenderContrato(contrato);
					boleto.setDataVencimento(new Date());
					String texto = "Sua velocidade foi reduzida, pague seu boleto e evite bloqueio.";
					mailService.enviarEmail(boleto,null,texto);
					smsService.enviarSMSSemData(boleto, texto);
					contratoNotificacaoService.incluir(comporContratoNotificacao(boleto, MeioEnvioContratoNotificacao.EMAIL, TipoContratoNotificacao.ENVIO_BOLETO, texto));
				}
			}
			if (utilData.getDiferencaDias(new Date(), boleto.getDataVencimento()) > parametro.getQtdDiasBloqueio()
					&& (contrato.getDataParaBloqueio() == null
							|| utilData.data1MaiorIgualData2(new Date(), contrato.getDataParaBloqueio()))) {
				if (contrato.getStatus().equals(StatusContrato.ATIVO) || contrato.getStatus().equals(StatusContrato.SUSPENSO)) {
					contrato.setStatus(StatusContrato.BLOQUEADO);
					contratoService.alterar(contrato);
					freeRadiusService.removerSuspensaoContrato(contrato);
					freeRadiusService.bloquearContrato(contrato);
				}
			}
		}
	}

	private void verificarDesbloquearContrato(Contrato contrato) {
		UtilData utilData = new UtilData();
		List<Boleto> boletos = boletoService.pesquisarBoletoEmAbertoContrato(contrato);
		boolean desbloquear = true;
		boolean removersuspensao = true;
		boolean boletoEmAtraso = false;
		
		for (Boleto boleto : boletos) {
			if (utilData.getDiferencaDias(new Date(), boleto.getDataVencimento()) > parametro.getQtdDiasAviso()){
					removersuspensao = false;
			}
			if (utilData.getDiferencaDias(new Date(), boleto.getDataVencimento()) > parametro.getQtdDiasBloqueio()){
				boletoEmAtraso = true;	
				if(contrato.getDataParaBloqueio() == null || utilData.data1MaiorIgualData2(new Date(), contrato.getDataParaBloqueio())) {
					desbloquear = false;
				}	
			}
		}
		if (desbloquear && contrato.getStatus().equals(StatusContrato.BLOQUEADO)) {
			contrato.setStatus(StatusContrato.ATIVO);
			contratoService.alterar(contrato);
			freeRadiusService.desbloquearContrato(contrato);
		}
		
		
		if (removersuspensao && contrato.getStatus().equals(StatusContrato.SUSPENSO)) {
			contrato.setStatus(StatusContrato.ATIVO);
			contratoService.alterar(contrato);
			freeRadiusService.removerSuspensaoContrato(contrato);
		}
		
		
		if(removersuspensao && !boletoEmAtraso && contrato.getDataParaBloqueio() != null){
			contrato.setDataParaBloqueio(null);
			contratoService.alterar(contrato);
		}
		

	}

	private void gerarBoleto(BigDecimal valor, List<BoletoLancamento> lancamentosBoleto, Contrato contrato) {
		try{
			if (contrato.isGeraBoleto()) {
				List<ContratoLancamento> lancamentosEmAberto = contratoLancamentoService.pesquisarLancamentoAberto(contrato);
				List<ContratoProduto> produtos = contratoProdutoService.pesquisarVigentePorContrato(contrato);
				Boleto boleto = new Boleto();
				UtilData utilData = new UtilData();
				Date dataVencimento = utilData.ajustaData(new Date(),contrato.getDiaMesVencimento(), 23, 59, 59);
				
				Boleto boletoJaCriado = boletoService.recuperarBoletoContratoMes(contrato,new Date());
				Boleto boletoJaCriadoProximoMes  = boletoService.recuperarBoletoContratoMes(contrato,utilData.adicionarMeses(new Date(), 1));
				Boleto boletoJaCriadoAnteriorMes = boletoService.recuperarBoletoContratoMes(contrato,utilData.subtrairMeses(new Date(), 1));
				
				if (boletoJaCriado == null && boletoJaCriadoProximoMes == null) {
					
					for (ContratoLancamento lancamentoAberto : lancamentosEmAberto) {
						valor = lancamentoAberto.getTipoLancamento().equals(TipoLancamentoEnum.DEBITO)
								? valor.add(lancamentoAberto.getValor()) : valor.subtract(lancamentoAberto.getValor());
						lancamentosBoleto.add(new BoletoLancamento(boleto, lancamentoAberto));
					}
					
					if(!contrato.getStatus().equals(StatusContrato.CANCELADO) && 
							!contrato.getStatus().equals(StatusContrato.BLOQUEADO) && 
								!contrato.getStatus().equals(StatusContrato.INATIVO)){
						
						for (ContratoProduto produto : produtos) {
							ContratoLancamento contratoLancamento = new ContratoLancamento();
							contratoLancamento.setContrato(contrato);
							contratoLancamento.setDataLancamento(new Date());
							contratoLancamento.setDescricao(produto.getProduto().getNome());
							contratoLancamento.setStatus(StatusLancamentoEnum.PENDENTE);
							contratoLancamento.setTipoLancamento(TipoLancamentoEnum.DEBITO);
							contratoLancamento.setValor(produto.getValor().multiply(new BigDecimal(produto.getQtd())));
							contratoLancamentoService.incluir(contratoLancamento);
		
							BoletoLancamento boletoLancamento = new BoletoLancamento();
							boletoLancamento.setBoleto(boleto);
							boletoLancamento.setContratoLancamento(contratoLancamento);
		
							lancamentosBoleto.add(boletoLancamento);
							valor = valor.add(produto.getValor().multiply(new BigDecimal(produto.getQtd())));
						}
					
						if (valor.intValue() > 0) {
							boleto.setContrato(contrato);
							boleto.setLancamentos(lancamentosBoleto);
							boleto.setDataCriacao(new Date());
							boleto.setStatus(StatusBoletoEnum.ABERTO);
							boleto.setValor(valor);
							if(boletoJaCriadoAnteriorMes == null){
								boleto.setDataVencimento(utilData.adicionarMeses(dataVencimento, 1));
							}else{
								boleto.setDataVencimento(dataVencimento);
							}	
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
		}catch(Exception e){
			e.printStackTrace();
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

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
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
		} catch (Throwable e) {
			System.out.println("PAROU A THREAD");
		}
	}
	
	@Timeout
	@AccessTimeout(value = 5, unit = TimeUnit.MINUTES)
	public void process(Timer timer) {
	   System.out.println("DEU PROBLEMA DE TIMEOUT: "+timer);
	}

	public static void main(String args[]) {
		
		
	}
	
	
	private static final String getIPFromInt(final long ipaslong) {
	    return String.format("%d.%d.%d.%d",
	                (ipaslong >>> 24) & 0xff,
	                (ipaslong >>> 16) & 0xff,
	                (ipaslong >>>  8) & 0xff,
	                (ipaslong       ) & 0xff);
	}
	
	
}