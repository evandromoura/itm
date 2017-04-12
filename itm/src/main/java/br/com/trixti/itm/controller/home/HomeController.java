package br.com.trixti.itm.controller.home;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Boleto;
import br.com.trixti.itm.entity.Contrato;
import br.com.trixti.itm.infra.security.annotations.CustomIdentity;
import br.com.trixti.itm.service.boleto.BoletoService;
import br.com.trixti.itm.service.boleto.GeradorBoletoService;
import br.com.trixti.itm.service.radacct.RadacctService;
import br.com.trixti.itm.to.HomeTO;
import br.com.trixti.itm.util.UtilArquivo;

@Model
public class HomeController extends AbstractController<Object>{

	private HomeTO homeTO;
	private @Inject CustomIdentity customIdentity;
	private @Inject BoletoService boletoService;
	private @Inject RadacctService radacctService;
	private @Inject GeradorBoletoService geradorBoletoService;
	
	@PostConstruct
	private void init(){
		if(customIdentity.getCliente() != null){
			getHomeTO().setCliente(customIdentity.getCliente());
			getHomeTO().setListaUltimosBoletos(boletoService.pesquisarUltimosBoletosCliente(getHomeTO().getCliente()));
			for(Contrato contrato:getHomeTO().getCliente().getContratos()){
				if(contrato.getAutenticacoes() != null && !contrato.getAutenticacoes().isEmpty()){
					contrato.setListaUtilizacao(radacctService.pesquisarUltimosPorUsername(contrato.getAutenticacoes().get(0).getUsername()));
				}	
			}
		}	
	}
	
	
	public void downloadBoleto(Boleto boleto) throws Exception {
		try {
			File arquivoBoleto = geradorBoletoService.gerarBoleto(boleto);
			if (arquivoBoleto != null) {
				UtilArquivo utilArquivo = new UtilArquivo();
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				utilArquivo.convertFileToByteArrayOutputStream(arquivoBoleto, byteArrayOutputStream);
				download(byteArrayOutputStream, arquivoBoleto.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			getFacesContext().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}	
	}

	public HomeTO getHomeTO() {
		if (homeTO == null) {
			homeTO = new HomeTO();
		}
		return homeTO;
	}


	public void setHomeTO(HomeTO homeTO) {
		this.homeTO = homeTO;
	}

	
}
