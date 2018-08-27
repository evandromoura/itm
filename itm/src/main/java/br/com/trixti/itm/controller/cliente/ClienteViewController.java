package br.com.trixti.itm.controller.cliente;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Cliente;
import br.com.trixti.itm.infra.security.annotations.SuporteNivel1;
import br.com.trixti.itm.service.boleto.GeradorBoletoService;
import br.com.trixti.itm.service.cliente.ClienteService;
import br.com.trixti.itm.to.ClienteTO;
import br.com.trixti.itm.util.UtilArquivo;

@Model
@ViewScoped
@SuporteNivel1
public class ClienteViewController  extends AbstractController<Cliente>{
	
	
	private ClienteTO clienteTO;
	private @Inject ClienteService clienteService;
	private @Inject GeradorBoletoService geradorBoletoService;
	
	@PostConstruct
	public void init(){
		inicializar();
	}
	
	private void inicializar(){
		String parametro =getRequest().getParameter("parametro");
		getClienteTO().setCliente(clienteService.recuperar(new Integer(parametro)));
	}
	
	
	public void downloadBoleto(Boleto boleto) throws Exception{
		File arquivoBoleto = null;
		try{
			System.out.println("Download boleto ClienteViewController "+ boleto.getNossoNumero());
			arquivoBoleto = geradorBoletoService.gerarBoleto(boleto);
			if(arquivoBoleto != null){
				UtilArquivo utilArquivo = new UtilArquivo();
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				utilArquivo.convertFileToByteArrayOutputStream(arquivoBoleto, byteArrayOutputStream);
				download(byteArrayOutputStream, arquivoBoleto.getName());
			}	
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(arquivoBoleto != null){
				arquivoBoleto.delete();
			}
		}	
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
