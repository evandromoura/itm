package br.com.trixti.itm.controller.cliente;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.entity.StatusLancamentoEnum;
import br.com.trixti.itm.entity.TipoLancamentoEnum;
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
	
	@PostConstruct
	public void init(){
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
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Incluido com Sucesso", "O Registro foi alterado na base"));
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
