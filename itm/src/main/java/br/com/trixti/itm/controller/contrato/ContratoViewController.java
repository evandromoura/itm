package br.com.trixti.itm.controller.contrato;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.BoletoLancamento;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.entity.ContratoAnexo;
import br.com.trixti.itm.entity.ContratoEquipamento;
import br.com.trixti.itm.entity.ContratoLancamento;
import br.com.trixti.itm.entity.Retorno;
import br.com.trixti.itm.entity.StatusBoletoEnum;
import br.com.trixti.itm.entity.StatusLancamentoEnum;
import br.com.trixti.itm.entity.StatusRetorno;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
import br.com.trixti.itm.infra.financeiro.CalculaBase10;
import br.com.trixti.itm.infra.security.annotations.SuporteNivel1;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.boleto.GeradorBoletoService;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.service.contratoanexo.ContratoAnexoService;
import br.com.trixti.itm.service.contratoequipamento.ContratoEquipamentoService;
import br.com.trixti.itm.service.contratolancamento.ContratoLancamentoService;
import br.com.trixti.itm.service.mail.MailService;
import br.com.trixti.itm.service.parametro.ParametroService;
import br.com.trixti.itm.service.remessa.RemessaService;
import br.com.trixti.itm.service.sms.SMSService;
//import br.com.trixti.itm.service.sms.SMSService;
import br.com.trixti.itm.to.ContratoTO;
import br.com.trixti.itm.util.Base64Utils;
import br.com.trixti.itm.util.UtilArquivo;
import br.com.trixti.itm.util.UtilString;

@ViewScoped
@ManagedBean
@SuporteNivel1
public class ContratoViewController extends AbstractController<Contrato> {

	private ContratoTO contratoTO;
	private @Inject ContratoService contratoService;
	private @Inject GeradorBoletoService geradorBoletoService;
	private @Inject ContratoLancamentoService contratoLancamentoService;
	private @Inject BoletoService boletoService;
	private @Inject ParametroService parametroService;
	private @Inject MailService mailService;
	private @Inject SMSService smsService;
	private @Inject RemessaService remessaService;
	private @Inject ContratoEquipamentoService contratoEquipamentoService;
	private @Inject ContratoAnexoService contratoAnexoService;
	

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
		mailService.enviarEmail(boleto,null,"Sua fatura está disponível");
		getFacesContext().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Email enviado com sucesso!", "Email enviado com sucesso!"));
	}

	public void enviarSMS(Boleto boleto){
		smsService.enviarSMS(boleto);
		getFacesContext().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "SMS enviado com sucesso!", "SMS enviado com sucesso!"));
	}
	

	public void criarContratoLancamento() {
		getContratoTO().getContratoLancamento().setContrato(getContratoTO().getContrato());
		getContratoTO().getContratoLancamento().setDataLancamento(new Date());
		getContratoTO().getContratoLancamento().setStatus(StatusLancamentoEnum.PENDENTE);
		if (getContratoTO().getContratoLancamento().isGeraBoleto()) {
			Boleto boleto = new Boleto();
			boleto.setContrato(getContratoTO().getContrato());
			boleto.setDataCriacao(new Date());
			boleto.setDataVencimento(getContratoTO().getContratoLancamento().getDataBoleto() != null?getContratoTO().getContratoLancamento().getDataBoleto():new Date());
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
		contratoService.desbloquearContratoTemporariamente(getContratoTO().getContrato());
	}
	
	public void atribuirBoletoSegundaVia(Boleto boleto){
		getContratoTO().setBoletoSegundaVia(boleto);
	}
	
	public void gerarSegundaViaBoleto(){
		try{
			getContratoTO().getBoletoSegundaVia().setDataVencimento(getContratoTO().getDataSegundaViaBoleto());
			downloadBoleto(getContratoTO().getBoletoSegundaVia());
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public void enviarSegundaViaBoleto(){
		getContratoTO().getBoletoSegundaVia().setDataVencimento(getContratoTO().getDataSegundaViaBoleto());
		enviarEmail(getContratoTO().getBoletoSegundaVia());
	}
	
	public void removerBoletoRemessa(Boleto boleto){
		remessaService.removerBoletoRemessa(boleto);
		adicionarMensagem("label.global.alterarsucesso",FacesMessage.SEVERITY_INFO);
	}
	
	public void pagarBoleto(){
		try{
			boletoService.pagar(getContratoTO().getBoleto(),getContratoTO().getChaveMestra());
			adicionarMensagem("label.global.pagarsucesso",FacesMessage.SEVERITY_INFO);
		}catch(Exception e){
			adicionarMensagem("label.global.chavemestrainvalida",FacesMessage.SEVERITY_ERROR);
		}	
	}
	
	public void gerarComodato(){
			UtilString utilString = new UtilString();

			getContratoTO().setListaContratoEquipamento(new ArrayList<ContratoEquipamento>());
			getContratoTO().getContratoEquipamento().setListaContratoEquipamento(new ArrayList<ContratoEquipamento>());
			
			getContratoTO().getListaContratoEquipamento().add(getContratoTO().getContratoEquipamento());
			getContratoTO().getContratoEquipamento().setListaContratoEquipamento(getContratoTO().getListaContratoEquipamento());
		
			try {
				byte[] bytesRelatorio =	gerarRelatorioPDF(getNomeRelatorio(getContratoTO().getContratoEquipamento()), getParametros(getContratoTO().getContratoEquipamento(),getContratoTO().getListaContratoEquipamento()), getContratoTO().getListaContratoEquipamento());
				download(UtilArquivo.converterBytesEmByteArrayOutputStream(bytesRelatorio), "iTRIX_comodato_equipamento_"+getContratoTO().getContratoEquipamento().getNumeroSerie()+".pdf");
			} catch (Exception e) {
			}
			if(!utilString.vazio(getContratoTO().getProtocoloInstalacao())){
				getContratoTO().getContratoEquipamento().setProtocoloInstalacao(getContratoTO().getProtocoloInstalacao());	
				contratoEquipamentoService.alterar(getContratoTO().getContratoEquipamento());
			}
	}
	
	public void gerarRetirada(){
		UtilString utilString = new UtilString();
		List<ContratoEquipamento> listaContratoEquipamento = new ArrayList<ContratoEquipamento>();
		listaContratoEquipamento.add(getContratoTO().getContratoEquipamento());
		try {
			byte[] bytesRelatorio =	gerarRelatorioPDF(getNomeRelatorioRetirada(getContratoTO().getContratoEquipamento()), getParametros(getContratoTO().getContratoEquipamento(),listaContratoEquipamento), listaContratoEquipamento);
			download(UtilArquivo.converterBytesEmByteArrayOutputStream(bytesRelatorio), "iTRIX_retirada_equipamento_"+getContratoTO().getContratoEquipamento().getNumeroSerie()+".pdf");
		} catch (Exception e) {	
		}
		if(!utilString.vazio(getContratoTO().getProtocoloRetirada())){
			getContratoTO().getContratoEquipamento().setProtocoloRetirada(getContratoTO().getProtocoloRetirada());
			contratoEquipamentoService.alterar(getContratoTO().getContratoEquipamento());
		}	
	}
	
	
	private String getNomeRelatorio(ContratoEquipamento contratoEquipamento) {
		return recuperarDiretorio()+"/relatorios/contrato/relatorio_comondato.jasper";
	}
	
	private String getNomeRelatorioRetirada(ContratoEquipamento contratoEquipamento){
		return recuperarDiretorio()+"/relatorios/contrato/relatorio_retirada_equipamento.jasper";
	}

	private HashMap<String, Object> getParametros(ContratoEquipamento contratoEquipamento, List<ContratoEquipamento> listaContratoEquipamento) {
		HashMap<String, Object> parametros = new HashMap<>();
			parametros.put("P_PATH_IMAGEM", recuperarDiretorio()+"/resources/template/img/logo_itrix.png");
			parametros.put("REPORT_PATH",recuperarDiretorio());
			parametros.put("P_RAZAO_SOCIAL", getContratoTO().getParametro().getNomeEmpresa());
			parametros.put("P_CNPJ", getContratoTO().getParametro().getCnpj());
			parametros.put("P_ENDERECO", getContratoTO().getParametro().getLogradouro());
			parametros.put("P_BAIRRO", getContratoTO().getParametro().getBairro());
			parametros.put("P_CIDADE", getContratoTO().getParametro().getCidade());
			parametros.put("P_CEP", getContratoTO().getParametro().getCep());
			parametros.put("P_UF", getContratoTO().getParametro().getUf());
			parametros.put("P_TELEFONE", getContratoTO().getParametro().getTelefone());
			parametros.put("P_EMAIL", getContratoTO().getParametro().getFromEmail());
			parametros.put("P_REPRESENTADO", "");
			parametros.put("P_CARGO", "");
		return parametros;
	}
	
	private String recuperarDiretorio() {
		ServletContext servletContext = getServletContext();
		return servletContext.getRealPath("/");
	}
	
	public void enviarArquivo(){
		try{
			ContratoAnexo contratoAnexo = new ContratoAnexo();
			contratoAnexo.setConteudo(Base64Utils.base64Encode(UtilArquivo.converteInputStreamEmBytes(getContratoTO().getUpload().getInputStream())));
			contratoAnexo.setContrato(getContratoTO().getContrato());
			contratoAnexo.setNome(getContratoTO().getUpload().getSubmittedFileName());
			contratoAnexo.setDataCriacao(new Date());
			contratoAnexoService.incluir(contratoAnexo);
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Anexo enviado com sucesso!!", "O Registro foi incluido na base"));
			setContratoTO(null);
			init();
		}catch(Exception e){
			e.printStackTrace();
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao enviar Remessa", "ERRO"));
		}	
		
	}
	
	public void download(ContratoAnexo contratoAnexo){
		File arquivo =null;
		try{
			ContratoAnexo retornoCompleta = contratoAnexoService.recuperar(contratoAnexo.getId());
			UtilArquivo utilArquivo = new UtilArquivo();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			String nomeArquivo = retornoCompleta.getNome();
			arquivo = utilArquivo.getFileFromBytes(Base64Utils.base64Decode(retornoCompleta.getConteudo()), nomeArquivo);
			utilArquivo.convertFileToByteArrayOutputStream(arquivo,
					byteArrayOutputStream);
			download(byteArrayOutputStream, nomeArquivo);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(arquivo != null){
				arquivo.delete();
			}	
		}	
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
