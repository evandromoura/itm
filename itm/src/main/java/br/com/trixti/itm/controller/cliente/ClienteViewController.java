package br.com.trixti.itm.controller.cliente;

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
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.ClienteLancamento;
import br.com.trixti.itm.entity.StatusBoletoEnum;
import br.com.trixti.itm.entity.StatusLancamentoEnum;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.boleto.GeradorBoletoService;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.service.clientelancamento.ClienteLancamentoService;
import br.com.trixti.itm.to.ClienteTO;
import br.com.trixti.itm.util.UtilArquivo;


@ViewScoped
@ManagedBean
public class ClienteViewController  extends AbstractController<Cliente>{
	
	
	private ClienteTO clienteTO;
	private @Inject ClienteService clienteService;
	private @Inject GeradorBoletoService geradorBoletoService;
	private @Inject ClienteLancamentoService clienteLancamentoService;
	private @Inject BoletoService boletoService;
	
	@PostConstruct
	public void init(){
		inicializar();
		getClienteTO().setAbaAtiva("dadosgerais");
	}
	
	private void inicializar(){
		String parametro =getRequest().getParameter("parametro");
		getClienteTO().setCliente(clienteService.recuperar(new Integer(parametro)));
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
	
	public void criarClienteLancamento(){
		getClienteTO().getClienteLancamento().setCliente(getClienteTO().getCliente());
		getClienteTO().getClienteLancamento().setDataLancamento(new Date());
		getClienteTO().getClienteLancamento().setTipoLancamento(TipoLancamentoEnum.DEBITO);
		getClienteTO().getClienteLancamento().setStatus(StatusLancamentoEnum.PENDENTE);
		clienteLancamentoService.incluir(getClienteTO().getClienteLancamento());
		if(getClienteTO().getClienteLancamento().isGeraBoleto()){
			Boleto boleto = new Boleto();
			boleto.setCliente(getClienteTO().getCliente());
			boleto.setDataCriacao(new Date());
			boleto.setDataVencimento(new Date());
			boleto.setValor(getClienteTO().getClienteLancamento().getValor());
			List<BoletoLancamento> listaBoletoLancamento  = new ArrayList<BoletoLancamento>();
			BoletoLancamento boletoLancamento = new BoletoLancamento();
			boletoLancamento.setBoleto(boleto);
			boletoLancamento.setClienteLancamento(getClienteTO().getClienteLancamento());
			listaBoletoLancamento.add(boletoLancamento);
			boleto.setLancamentos(listaBoletoLancamento);
			boleto.setStatus(StatusBoletoEnum.ABERTO);
			boletoService.incluir(boleto);
		}	
		getClienteTO().setClienteLancamento(null);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incluido com Sucesso", "O Registro foi alterado na base"));
		inicializar();
	}
	
	
	public void gerarBoletoListaClienteLancamento(){
		
		Boleto boleto = new Boleto();
		boleto.setCliente(getClienteTO().getCliente());
		Date dataAtual = new Date();
		boleto.setDataCriacao(dataAtual);
		boleto.setDataVencimento(dataAtual);
		boleto.setLancamentos(new ArrayList<BoletoLancamento>());
		BigDecimal totalBoleto = new BigDecimal(0);
		for (ClienteLancamento clienteLancamento:getClienteTO().getCliente().getLancamentos()){
			if(clienteLancamento.isSelecionado()){
				BoletoLancamento boletoLancamento = new BoletoLancamento();
				boletoLancamento.setBoleto(boleto);
				boletoLancamento.setClienteLancamento(clienteLancamento);
				boleto.getLancamentos().add(boletoLancamento);
				totalBoleto = totalBoleto.add(clienteLancamento.getValor());
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
		if(getClienteTO().getCliente().getLancamentos() != null){
			for(ClienteLancamento lancamento:getClienteTO().getCliente().getLancamentos()){
				if (lancamento.isSelecionado()){
					return true;
				}
			}
		}
		return false;
	}

	public ClienteTO getClienteTO() {
		if (clienteTO == null) {
			clienteTO = new ClienteTO();
		}
		return clienteTO;
	}


	public void setClienteTO(ClienteTO clienteTO) {
		this.clienteTO = clienteTO;
	}
	

}
