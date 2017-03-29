package br.com.trixti.itm.controller.contrato;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
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
import br.com.trixti.itm.entity.StatusLancamentoEnum;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.boleto.GeradorBoletoService;
import br.com.trixti.itm.service.contrato.ContratoService;
import br.com.trixti.itm.service.contratolancamento.ContratoLancamentoService;
import br.com.trixti.itm.to.ContratoTO;
import br.com.trixti.itm.util.UtilArquivo;


@ViewScoped
@ManagedBean
public class ContratoViewController  extends AbstractController<Contrato>{
	
	
	private ContratoTO contratoTO;
	private @Inject ContratoService contratoService;
	private @Inject GeradorBoletoService geradorBoletoService;
	private @Inject ContratoLancamentoService contratoLancamentoService;
	private @Inject BoletoService boletoService;
	
	@PostConstruct
	public void init(){
		inicializar();
		getContratoTO().setAbaAtiva("dadosgerais");
	}
	
	private void inicializar(){
		String parametro =getRequest().getParameter("parametro");
		getContratoTO().setContrato(contratoService.recuperarCompleto(new Integer(parametro)));
		getContratoTO().setDataVencimentoBoleto(null);
	}
	
	
	public void downloadBoleto(Boleto boleto) throws Exception{
		File arquivoBoleto = geradorBoletoService.gerarBoleto(boleto);
		if(arquivoBoleto != null){
			UtilArquivo utilArquivo = new UtilArquivo();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			utilArquivo.convertFileToByteArrayOutputStream(arquivoBoleto, byteArrayOutputStream);
			download(byteArrayOutputStream, arquivoBoleto.getName());
		}	
	}
	
	public void criarContratoLancamento(){
		getContratoTO().getContratoLancamento().setContrato(getContratoTO().getContrato());
		getContratoTO().getContratoLancamento().setDataLancamento(new Date());
		getContratoTO().getContratoLancamento().setTipoLancamento(TipoLancamentoEnum.DEBITO);
		getContratoTO().getContratoLancamento().setStatus(StatusLancamentoEnum.PENDENTE);
		contratoLancamentoService.incluir(getContratoTO().getContratoLancamento());
		if(getContratoTO().getContratoLancamento().isGeraBoleto()){
			Boleto boleto = new Boleto();
			boleto.setContrato(getContratoTO().getContrato());
			boleto.setDataCriacao(new Date());
			boleto.setDataVencimento(new Date());
			boleto.setValor(getContratoTO().getContratoLancamento().getValor());
			List<BoletoLancamento> listaBoletoLancamento  = new ArrayList<BoletoLancamento>();
			BoletoLancamento boletoLancamento = new BoletoLancamento();
			boletoLancamento.setBoleto(boleto);
			boletoLancamento.setContratoLancamento(getContratoTO().getContratoLancamento());
			listaBoletoLancamento.add(boletoLancamento);
			boleto.setLancamentos(listaBoletoLancamento);
			boleto.setStatus(StatusBoletoEnum.ABERTO);
			boletoService.incluir(boleto);
		}	
		getContratoTO().setContratoLancamento(null);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incluido com Sucesso", "O Registro foi alterado na base"));
		inicializar();
	}
	
	
	public void gerarBoletoListaContratoLancamento(){
		
		Boleto boleto = new Boleto();
		boleto.setContrato(getContratoTO().getContrato());
		Date dataAtual = new Date();
		boleto.setDataCriacao(dataAtual);
		boleto.setDataVencimento(getContratoTO().getDataVencimentoBoleto());
		boleto.setLancamentos(new ArrayList<BoletoLancamento>());
		BigDecimal totalBoleto = new BigDecimal(0);
		for (ContratoLancamento contratoLancamento:getContratoTO().getContrato().getLancamentos()){
			if(contratoLancamento.isSelecionado()){
				BoletoLancamento boletoLancamento = new BoletoLancamento();
				boletoLancamento.setBoleto(boleto);
				boletoLancamento.setContratoLancamento(contratoLancamento);
				boleto.getLancamentos().add(boletoLancamento);
				totalBoleto = totalBoleto.add(contratoLancamento.getValor());
			}
		}
		if(boleto.getLancamentos().size() > 0){ 
			boleto.setValor(totalBoleto);
			boleto.setStatus(StatusBoletoEnum.ABERTO);
			boletoService.incluir(boleto);
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incluido com Sucesso", "O Registro foi alterado na base"));
		}
		inicializar();
	}
	
	public void excluirBoleto(Boleto boleto){
		boletoService.excluir(boleto);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incluido com Sucesso", "O Registro foi alterado na base"));
		inicializar();
	}
	
	public boolean isExibeBotaoGerarBoleto(){
		if(getContratoTO().getContrato().getLancamentos() != null){
			for(ContratoLancamento lancamento:getContratoTO().getContrato().getLancamentos()){
				if (lancamento.isSelecionado()){
					return true;
				}
			}
		}
		return false;
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
