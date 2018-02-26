package br.com.trixti.itm.controller.retorno;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Retorno;
import br.com.trixti.itm.infra.financeiro.IntegracaoFinanceiraItau;
import br.com.trixti.itm.infra.security.annotations.Admin;
import br.com.trixti.itm.infra.security.annotations.Financeiro;
import br.com.trixti.itm.service.retorno.RetornoService;
import br.com.trixti.itm.to.RetornoTO;
import br.com.trixti.itm.util.Base64Utils;
import br.com.trixti.itm.util.UtilArquivo;

@Model
@ViewScoped
@Financeiro
public class RetornoViewController  extends AbstractController<Retorno> implements Serializable{
	
	private static final long serialVersionUID = 8607007655516127618L;
	private RetornoTO retornoTO;
	private @Inject RetornoService retornoService;
	private @Inject IntegracaoFinanceiraItau integracaoFinanceiraItau;
	
	@PostConstruct
	public void init(){
		inicializar();
	}
	
	private void inicializar(){
		String parametro = getRequest().getParameter("parametro");
		getRetornoTO().setRetorno(retornoService.recuperar(new Integer(parametro)));
		getRetornoTO().setRecords(integracaoFinanceiraItau.comporRetorno(getRetornoTO().getRetorno()));
	}
	
	
	public void download(Retorno retorno){
		File arquivo =null;
		try{
			Retorno retornoCompleta = retornoService.recuperarCompleto(retorno.getId());
			UtilArquivo utilArquivo = new UtilArquivo();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			String nomeArquivo = retornoCompleta.getNomeArquivo();
			arquivo = utilArquivo.getFileFromBytes(Base64Utils.base64Decode(retornoCompleta.getArquivo()), nomeArquivo);
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
	
	

	public RetornoTO getRetornoTO() {
		if (retornoTO == null) {
			retornoTO = new RetornoTO();
		}
		return retornoTO;
	}


	public void setRetornoTO(RetornoTO retornoTO) {
		this.retornoTO = retornoTO;
	}
	

}
