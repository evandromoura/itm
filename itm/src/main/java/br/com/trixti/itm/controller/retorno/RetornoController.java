package br.com.trixti.itm.controller.retorno;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.jrimum.bopepo.BancosSuportados;

import br.com.trixti.itm.controller.AbstractController;
import br.com.trixti.itm.entity.Retorno;
import br.com.trixti.itm.service.retorno.RetornoService;
import br.com.trixti.itm.to.RetornoTO;
import br.com.trixti.itm.util.Base64Utils;
import br.com.trixti.itm.util.UtilArquivo;



@ViewScoped
@ManagedBean
public class RetornoController extends AbstractController<Retorno> {
	
	private @Inject RetornoService retornoService;
	private RetornoTO retornoTO;
	
	@PostConstruct
	private void init(){
		pesquisar();
		getRetornoTO().getRetorno().setBanco(BancosSuportados.BANCO_ITAU.name());
	}
	
	public void pesquisar(){
		getRetornoTO().setRetornos(
				retornoService.pesquisar(getRetornoTO().getRetornoPesquisa()));
	}
	
	public void gravar(){
		if(getRetornoTO().getRetorno().getId() == null){
			retornoService.incluir(getRetornoTO().getRetorno());
		}else{
			retornoService.alterar(getRetornoTO().getRetorno());
		}
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastrado com Sucesso", "O Registro foi incluido na base"));
	}
	
	public void download(Retorno retorno){
		try{
			Retorno retornoCompleta = retornoService.recuperarCompleto(retorno.getId());
			UtilArquivo utilArquivo = new UtilArquivo();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			String nomeArquivo = retornoCompleta.getNomeArquivo();
			utilArquivo.convertFileToByteArrayOutputStream(utilArquivo.getFileFromBytes(Base64Utils.base64Decode(retornoCompleta.getArquivo()), nomeArquivo),
					byteArrayOutputStream);
			download(byteArrayOutputStream, nomeArquivo);
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	public void enviarArquivo(){
		try{
			getRetornoTO().getRetorno().setDataCriacao(new Date());
			getRetornoTO().getRetorno().setArquivo(Base64Utils.base64Encode(UtilArquivo.converteInputStreamEmBytes(getRetornoTO().getUpload().getInputStream())));
			getRetornoTO().getRetorno().setNomeArquivo(getRetornoTO().getUpload().getSubmittedFileName());
			retornoService.incluir(getRetornoTO().getRetorno());
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Remessa enviado com sucesso!!", "O Registro foi incluido na base"));
			init();
		}catch(Exception e){
			e.printStackTrace();
			getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao enviar Remessa", "ERRO"));
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
	
	public void excluir(Retorno retorno){
		retornoService.excluir(retorno);
		getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excluido com Sucesso", "O Registro foi incluido na base"));
		pesquisar();
	}
}
